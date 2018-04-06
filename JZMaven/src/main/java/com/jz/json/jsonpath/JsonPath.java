package com.jz.json.jsonpath;

import com.google.gson.*;
import com.jz.json.jsoncompare.JsonElementWithLevel;

import java.io.File;
import java.util.*;

import static com.jz.json.jsoncompare.Utils.convertFormattedJson2Raw;

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

        Map<String, List<Range>> ranges = generateRanges(path);
        Map<String, List<Range>> matchedRanges = new LinkedHashMap<>();
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
                            if(isMatched(level, ranges, matchedRanges, length)) {
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
                        if (level.matches(regex))  {
                            isDone = true;
                            if(isMatched(level, ranges, matchedRanges, length)) {
                                result.add(tmp);
                            }
                        }
                    }
                }
            }

            // current level is BFS done which means all possible candidates are already captured in the result,
            // end BFS by directly returning result;
            if(isDone){
                return result;
            }
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
            String currentLevel, Map<String, List<Range>> ranges, Map<String, List<Range>> matchedRanges, int length) {
        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = currentLevel.indexOf('[')) != -1) {
            //update matchedRanges
            prefix.append(currentLevel.substring(0, index) + "[]");
            int i = Integer.parseInt(currentLevel.substring(index + 1, currentLevel.indexOf(']')));

            if (!matchedRanges.containsKey(prefix)) {
                for (Map.Entry<String, List<Range>> entry : ranges.entrySet()) {
                    String key = entry.getKey();
                    List<Range> value = entry.getValue();
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
    private static boolean isCurrentFieldMatched(List<Range> rangeList, String prefix, int i, int length) {
        if (rangeList != null && rangeList.size() > 0) {
            for (Range range : rangeList) {
                if (range.start < 0 && range.end < 0) {
                    if ((i - length) >= range.start && (i - length) <= range.end) {
                        return true;
                    }
                } else {
                    if (i >= range.start && i <= range.end) {
                        return true;
                    }
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

    /**
     * @param path minView.actions[  2 ,  3].action[2:5].URL
     * @return minView.actions[] : [(2,2),(3,3)]
     * minView.actions[].action[] : [(2,5)]
     */
    private static Map<String, List<Range>> generateRanges(String path) {
        Map<String, List<Range>> ranges = new LinkedHashMap<>();
        if (path == null || path.trim().length() == 0) {
            return ranges;
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            String r = path.substring(index + 1, path.indexOf(']')).trim();
            if(r.contains("@")) {

            } else {
                List<Range> range = generateRange(r);
                if (range != null && range.size() > 0) {
                    ranges.put(prefix.toString().trim(), range);
                }
            }

            path = path.substring(path.indexOf(']') + 1);
        }

        return ranges;
    }


    /**
     * @param r String in []
     *          [2]
     *          [-2]
     *          [0,1]
     *          [:2]
     *          [1:2]
     *          [-2:]
     *          [2:]
     *          last()
     *          first()
     *          <p>
     *          $..book[?(@.isbn)]	All books with an ISBN number (convert to notempty ), [?@.isbn notempty]
     *          <p>
     *          $.store.book[?(@.price < 10)]	All books in store cheaper than 10
     *          $..book[?(@.price <= $['expensive'])]	All books in store that are not "expensive"
     *          $..book[?(@.author =~ /.*REES/i)]	All books matching regex (ignore case)
     */
    private static List<Range> generateRange(String r) {
        List<Range> result = new ArrayList<>();
        r = r.trim();
        if (r == null || r.length() == 0) {
            return result;
        }

        if (r.equalsIgnoreCase("first()")) {
            result.add(new Range(0, 0));
        } else if (r.equalsIgnoreCase("last()")) {
            result.add(new Range(-1, -1));
        } else if (r.contains("positon()")) {
            //to-do
        } else if (r.contains(",")) {
            String[] strs = r.split("\\s*,\\s*|\\s*:\\s*");
            for (String str : strs) {
                result.add(new Range(Integer.parseInt(str), Integer.parseInt(str)));
            }
        } else if (r.contains(":")) {
            String[] strs = r.split("\\s*,\\s*|\\s*:\\s*");
            if (strs.length == 2) { //[0:3]
                if (strs[0].length() == 0 || strs[0].equalsIgnoreCase("")) {
                    result.add(new Range(0, Integer.parseInt(strs[1]) - 1));
                } else {
                    result.add(new Range(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]) - 1));
                }
            } else if (strs.length == 1) {
                int index = Integer.parseInt(strs[0]);
                if (r.startsWith(":")) { //[ : 2]
                    result.add(new Range(0, index - 1));
                } else {
                    if (index >= 0) {
                        result.add(new Range(index, Integer.MAX_VALUE));
                    } else {
                        result.add(new Range(index, -1)); // [-2],for negative range, i - array.length;
                    }
                }
            }
        } else if (r.equalsIgnoreCase("*")) { //[*]
            result.add(new Range(0, Integer.MAX_VALUE));
        } else if (Integer.parseInt(r) >= 0) {
            result.add(new Range(Integer.parseInt(r), Integer.parseInt(r)));
        } else if (Integer.parseInt(r) < 0) { //[-2]
            result.add(new Range(Integer.parseInt(r), Integer.parseInt(r)));
        }

        return result;
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

    public static void main(String[] args) throws Exception {
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();
        List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(o1, "$.modules.RETURNS.maxView");
        System.out.println("*******************");
        for (JsonElementWithLevel jsonElementWithLevel : res) {
            System.out.println(jsonElementWithLevel);
        }

//        String path = "$.modules.RETURNS.maxView.store[1,3].book[@.category > 'fiction' and @.price < 10 or @.color == \\\"red\\\"].textSpans[0].text";


/*
        if (strs.length != operators.size() + 1) {
            new Exception("Field name has \"&&\" or \"||\".");
        }
*/


    }


}
