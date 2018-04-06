package com.jz.json.jsonpath;

import com.google.gson.*;
import com.jz.json.jsoncompare.JsonElementWithLevel;

import java.io.File;
import java.util.*;

import static com.jz.json.jsoncompare.Utils.convertFormattedJson2Raw;
import static com.jz.json.jsonpath.Range.getRange;

public class JsonPath {

    /**
     * @param source the source of JsonObject
     *               Sample JsonObject:{"store": {"book": [{"category": "reference","author": "Nigel Rees","title": "Sayings of the Century","price": 8.95},{"category": "fiction","author": "Evelyn Waugh","title": "Sword of Honour","price": 12.99},{"category": "fiction","author": "Herman Melville","title": "Moby Dick","isbn": "0-553-21311-3","price": 8.99},{"category": "fiction","author": "J. R. R. Tolkien","title": "The Lord of the Rings","isbn": "0-395-19395-8","price": 22.99}],"bicycle": {"color": "red","price": 19.95}},"expensive": 10}
     * @param path   Sample JsonPath
     *               <p>
     *               now supporting standard JsonPath. Partial JsonPath is also supported.
     *               $.store.book[*].author	The authors of all books
     *               $.store 	All things, both books and bicycles
     *               book[2]	    The third book
     *               book[-2]	The second to last book
     *               book[0,1]	The first two books
     *               book[:2]	All books from index 0 (inclusive) until index 2 (exclusive)
     *               book[1:2]	All books from index 1 (inclusive) until index 2 (exclusive)
     *               book[-2:]	Last two books
     *               book[2:]	Book number two from tail
     *               book[first()]
     *               book[last()]
     * @return returns a a list of {@link JsonElementWithLevel}
     */


    private static List<JsonElementWithLevel> getJsonElementWithLevelByPath(
            JsonObject source, String path) throws Exception {
        List<JsonElementWithLevel> result = new ArrayList<>();
        if (path == null || path.length() == 0 || source == null || source.isJsonNull()) {
            return result;
        }

        Map<String, List<Filter>> ranges = getFilters(path);
        Map<String, List<Filter>> matchedRanges = new LinkedHashMap<>();
        String regex = generateRegex(path);

        // to support length() function;
        if (path.matches("(.*)(\\.length\\(\\)$)")) { // case: [path == $.listing.termsAndPolicies.length()]
            path = path.replaceAll("(.*)(\\.length\\(\\)$)", "$1");
            int length = length(source, path);
            result.add(new JsonElementWithLevel(new JsonPrimitive(length), path));
            return result;
        }


        Queue<JsonElementWithLevel> queue = new LinkedList<JsonElementWithLevel>();
        queue.offer(new JsonElementWithLevel(source, "$"));
        boolean isDone = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            //Traverse by level
            for (int i = 0; i < size; i++) {
                JsonElementWithLevel org = queue.poll();
                String currentLevel = org.getLevel();
                JsonElement je1 = org.getJsonElement();
                System.out.println(currentLevel);

                if (je1.isJsonPrimitive()) {
                    //do nothing
                } else if (je1.isJsonArray()) {
                    JsonArray ja1 = je1.getAsJsonArray();
                    int length = ja1.size();
                    for (int j = 0; j < ja1.size(); j++) {
                        String level = currentLevel + "[" + j + "]";
                        JsonElementWithLevel tmp = new JsonElementWithLevel(ja1.get(j), level);
                        queue.offer(tmp);
                        if (level.matches(regex)) {
                            isDone = true;
                            if (isMatched(level, ranges, matchedRanges, length)) {
                                result.add(tmp);
                            }
                        }
                    }
                } else if (je1.isJsonObject()) {
                    JsonObject jo1 = je1.getAsJsonObject();
                    int length = jo1.entrySet().size();
                    for (Map.Entry<String, JsonElement> entry : jo1.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        String level = currentLevel + "." + key;
                        JsonElementWithLevel tmp = new JsonElementWithLevel(value, level);
                        queue.offer(tmp);
                        if (level.matches(regex)) {
                            isDone = true;
                            if (isMatched(level, ranges, matchedRanges, length)) {
                                result.add(tmp);
                            }
                        }
                    }
                }
            }

            // current level is BFS done which means all possible candidates are already captured in the result,
            // end BFS by directly returning result;
         /*   if(isDone){
                return result;
            }*/
        }


        return result;
    }

/*   public static List<JsonElement> getJsonElementByPath(JsonObject source, String path) throws Exception {
    List<JsonElement> result = new ArrayList<>();
    List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(source, path);
    for (JsonElementWithLevel e : res) {
    result.add(e.getJsonElement());
    }

    return result;
    }*/

    /**
     * @param currentLevel  $.courses[i].grade
     * @param ranges
     * @param matchedRanges
     * @return true if i in matchedRange();
     */


    private static boolean isMatched(
            String currentLevel, Map<String, List<Filter>> ranges, Map<String, List<Filter>> matchedRanges,
            int length) {
        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = currentLevel.indexOf('[')) != -1) {
            //update matchedRanges
            prefix.append(currentLevel.substring(0, index) + "[]");
            int i = Integer.parseInt(currentLevel.substring(index + 1, currentLevel.indexOf(']')));

            if (!matchedRanges.containsKey(prefix)) {
                for (Map.Entry<String, List<Filter>> entry : ranges.entrySet()) {
                    String key = entry.getKey();
                    List<Filter> value = entry.getValue();
                    int idx = prefix.indexOf(key);
                    if (idx != -1 && prefix.toString().substring(idx).equals(key)) {
                        matchedRanges.put(prefix.toString(), value);
                    }
                }
            }

            boolean tmp = isCurrentFieldMatched(matchedRanges.get(prefix.toString().trim()), prefix.toString(), i, length);
            if (tmp) {
                currentLevel = currentLevel.substring(currentLevel.indexOf(']') + 1);
            } else {
                return false;
            }
        }

        return true;
    }


    /**
     * to check whether $.modules.RETURNS.maxView.value[4] is matching the range in matchedRanges;
     *
     * @param prefix the current field name like $.modules.RETURNS.maxView.value[]
     * @param i      index of a JsonArray
     * @return return true if  i in any of the range [(0, 0), (3,3)], otherwise return false;
     */
    private static boolean isCurrentFieldMatched(List<Filter> rangeList, String prefix, int i, int length) {
        if (rangeList != null && rangeList.size() > 0) {
            for (Filter range : rangeList) {
                if (range instanceof Range) {
                    if (((Range) range).getStart() < 0 && ((Range) range).getEnd() < 0) {
                        if ((i - length) >= ((Range) range).getStart() && (i - length) <= ((Range) range).getEnd()) {
                            return true;
                        }
                    } else {
                        if (i >= ((Range) range).getStart() && i <= ((Range) range).getEnd()) {
                            return true;
                        }
                    }
                } else if (range instanceof Condition) {
                    //to-do ; deal with conditions
                }

            }
        }

        return false;
    }

    private static String generateRegex(String path) {
        if (path.startsWith("$")) {
            path = "\\$" + path.substring(1);
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            path = path.substring(path.indexOf(']') + 1);
        }
        prefix.append(path);
        String regex = "(.*)(" + prefix.toString().trim().replaceAll("\\[\\]", "\\\\[.*\\\\]") + ")";

        return regex;
    }


    private static int length(JsonObject jsonObject, String path) throws Exception {
        if (path == null || path.length() == 0) {
            return 0;
        }
        if (jsonObject == null || jsonObject.isJsonNull() || !jsonObject.isJsonObject()) {
            return 0;
        }

        int length = 0;
        List<JsonElementWithLevel> result = getJsonElementWithLevelByPath(jsonObject, path);

        if (result == null || result.size() == 0) {
            length = 0;
        } else if (result.size() > 1) {
            throw new Exception("Please correct your json path to match a single JsonElement.");
        } else {
            JsonElement jsonElement = result.get(0).getJsonElement();
            if (jsonElement.isJsonObject()) {
                length = jsonElement.getAsJsonObject().entrySet().size();
            } else if (jsonElement.isJsonArray()) {
                length = jsonElement.getAsJsonArray().size();
            } else if (jsonElement.isJsonPrimitive()) {
                length = jsonElement.getAsJsonPrimitive().getAsString().length();
            }

        }

        return length;
    }

    /**
     * @param path minView.actions[  2 ,  3].action[2:5].URL
     * @return minView.actions[] : [(2,2),(3,3)]
     * minView.actions[].action[] : [(2,5)]
     */
    public static Map<String, List<Filter>> getFilters(String path) {
        Map<String, List<Filter>> res = new LinkedHashMap<>();

        if (path == null || path.trim().length() == 0) {
            return res;
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            String r = path.substring(index + 1, path.indexOf(']')).trim();
            if (r.contains("@")) {
                List<Condition> conditions = Condition.getConditions(r);
                List<Filter> filters = new ArrayList<>();
                for (Condition condition : conditions) {
                    filters.add(condition);
                }
                if (conditions != null && conditions.size() > 0) {
                    res.put(prefix.toString().trim(), filters);
                }
            } else {
                List<Range> ranges = getRange(r);
                List<Filter> filters = new ArrayList<>();
                for (Range range : ranges) {
                    filters.add(range);
                }
                if (ranges != null && ranges.size() > 0) {
                    res.put(prefix.toString().trim(), filters);
                }
            }

            path = path.substring(path.indexOf(']') + 1);
        }

        return res;
    }


    public static void main(String[] args) throws Exception {
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();
        List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(o1, "RETURNS.maxView.value[-2:]");
        System.out.println("*******************");
        for (JsonElementWithLevel jsonElementWithLevel : res) {
            System.out.println(jsonElementWithLevel);
        }

        String path = "$.modules.RETURNS.maxView.store[1,3].book[@.category > 'fiction' and @.price < 10 or @.color == \\\"red\\\"].textSpans[0].text";



/*
        if (strs.length != operators.size() + 1) {
            new Exception("Field name has \"&&\" or \"||\".");
        }
*/


    }


}
