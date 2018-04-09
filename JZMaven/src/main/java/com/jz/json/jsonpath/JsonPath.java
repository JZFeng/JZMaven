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

        Map<String, List<Filter>> filters = getFilters(path);
        Map<String, List<Filter>> matchedFilters = new LinkedHashMap<>();
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
                JsonElement je = org.getJsonElement();
                System.out.println(currentLevel);

                if (je.isJsonPrimitive()) {
                    //do nothing
                } else if (je.isJsonArray()) {
                    JsonArray ja = je.getAsJsonArray();
                    int length = ja.size();
                    for (int j = 0; j < ja.size(); j++) {
                        String level = currentLevel + "[" + j + "]";
                        JsonElementWithLevel tmp = new JsonElementWithLevel(ja.get(j), level);
                        queue.offer(tmp);
                        updateMatchedFilters(level, filters, matchedFilters);

                        if (level.matches(regex)) {
                            isDone = true;
                            if (isMatchingFilters(tmp.getJsonElement(), level, matchedFilters, length)) {
                                result.add(tmp);
                            }
                        }
                    }
                } else if (je.isJsonObject()) {
                    JsonObject jo = je.getAsJsonObject();
                    int length = jo.entrySet().size();
                    for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
                        String key = entry.getKey();
                        JsonElement value = entry.getValue();
                        String level = currentLevel + "." + key;
                        JsonElementWithLevel tmp = new JsonElementWithLevel(value, level);
                        queue.offer(tmp);
                        updateMatchedFilters(level, filters, matchedFilters);
                        if (level.matches(regex)) {
                            isDone = true;
                            if (isMatchingFilters(tmp.getJsonElement(), level, matchedFilters, length)) {
                                result.add(tmp);
                            }
                        }
                    }
                }
            }

            // current level is BFS done which means all possible candidates are already captured in the result,
            // end BFS by directly returning result;
            if (isDone) {
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
     * @param currentLevel   $.courses[i].grade
     * @param matchedFilters
     * @return true if i in matchedRange();
     */


    private static boolean isMatchingFilters(
            JsonElement je,
            String currentLevel,
            Map<String, List<Filter>> matchedFilters,
            int length) throws Exception {
        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = currentLevel.indexOf('[')) != -1) {
            prefix.append(currentLevel.substring(0, index) + "[]");
            boolean isMatched = false;
            List<Filter> filters = matchedFilters.get(prefix.toString().trim());

            if (filters.size() > 0 && filters.get(0) instanceof Range) {
                int i = Integer.parseInt(currentLevel.substring(index + 1, currentLevel.indexOf(']')));
                isMatched = isMatchingRange(filters, prefix.toString(), i, length);
            } else if (filters.size() > 0 && (filters.get(0) instanceof Condition)) {
                List<Condition> c = new ArrayList<>();
                for (Filter f : filters) {
                    c.add((Condition) f);
                }
                if(je.isJsonObject()) {
                     isMatched = isMatchingConditions(je.getAsJsonObject(), c);
                }
            }

            if (isMatched) {
                currentLevel = currentLevel.substring(currentLevel.indexOf(']') + 1);
            } else {
                return false;
            }

        }

        return true;
    }

    /**
     *
     * @param currentLevel
     * @param filters
     * @param matchedFilters
     */
    private static void updateMatchedFilters(
            String currentLevel, Map<String, List<Filter>> filters, Map<String, List<Filter>> matchedFilters) {

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = currentLevel.indexOf('[')) != -1) {
            //update matchedRanges
            prefix.append(currentLevel.substring(0, index) + "[]");
            if (!matchedFilters.containsKey(prefix)) {
                for (Map.Entry<String, List<Filter>> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    List<Filter> value = entry.getValue();
                    int idx = prefix.indexOf(key);
                    if (idx != -1 && prefix.toString().substring(idx).equals(key)) {
                        matchedFilters.put(prefix.toString(), value);
                    }
                }
            }

            currentLevel = currentLevel.substring(currentLevel.indexOf(']') + 1);

        }

    }


    /**
     * to check whether $.modules.RETURNS.maxView.value[4] is matching the range in matchedRanges;
     *
     * @param prefix the current field name like $.modules.RETURNS.maxView.value[]
     * @param i      index of a JsonArray
     * @return return true if  i in any of the range [(0, 0), (3,3)], otherwise return false;
     */
    private static boolean isMatchingRange(
            List<Filter> filterList, String prefix, int i, int length) throws Exception {
        System.out.println("prefix is " + prefix);
        if (filterList != null && filterList.size() > 0) {
            for (Filter filter : filterList) {
                if (filter instanceof Range) {
                    if (((Range) filter).getStart() < 0 && ((Range) filter).getEnd() < 0) {
                        if ((i - length) >= ((Range) filter).getStart() && (i - length) <= ((Range) filter).getEnd()) {
                            return true;
                        }
                    } else {
                        if (i >= ((Range) filter).getStart() && i <= ((Range) filter).getEnd()) {
                            return true;
                        }
                    }
                }
                /*
                else if (filter instanceof Condition) {
                    return true;

                    //to-do ; deal with conditions like [@.category > 'fiction' and @.price < 10 or @.color == \"red\"]
                    //prefix =  $.modules.RETURNS.maxView.store[1,3].book[]
                    List<JsonElementWithLevel> tmp = getJsonElementWithLevelByPath(source, prefix.substring(0, prefix.lastIndexOf("[")));
                    if (tmp == null || tmp.size() == 0 || tmp.size() > 1) {
                        return false;
                    } else {
                        JsonElementWithLevel je = tmp.get(tmp.size() - 1);
                        if (!je.getJsonElement().isJsonArray()) {
                            return false;
                        } else {
                            //apply the conditions;
                            for (JsonElement jae : je.getJsonElement().getAsJsonArray()) {
                                //jae has to be a JsonObject;
                                //get JsonArray Elements by applying the filter;
                                if(!jae.isJsonObject()) {
                                    return false;
                                } else {

                                }
                            }

                        }
                    }
                    */
                }

            }
        return false;
    }


    //to-do ; deal with conditions like [@.category > 'fiction' and @.price < 10 or @.color == \"red\"]
    //prefix =  $.modules.RETURNS.maxView.store[1,3].book[]
    private static List<JsonElementWithLevel> getJsonElementsByFilters(
            JsonObject source, List<Filter> filterList, String prefix) throws Exception {
        List<JsonElementWithLevel> res = new ArrayList<>();

        List<JsonElementWithLevel> tmp = getJsonElementWithLevelByPath(source, prefix.substring(0, prefix.lastIndexOf("[")));
        if (tmp == null || tmp.size() == 0 || tmp.size() > 1) {
            return res;
        } else {
            JsonElementWithLevel je = tmp.get(tmp.size() - 1);
            if (!je.getJsonElement().isJsonArray()) {
                return res;
            } else {
                //apply the conditions;
                for (JsonElement jae : je.getJsonElement().getAsJsonArray()) {
                    //jae has to be a JsonObject;
                    //get JsonArray Elements by applying the filter;
                    if (jae.isJsonObject()) {

                    }
                }

            }
        }


        return res;
    }


    /**
     * Identify whether a JsonObject matches the conditions
     *
     * @param jo         an JsonArray element as a JsonObject,
     * @param conditions conditions;
     * @return
     */
    public static boolean isMatchingConditions(JsonObject jo, List<Condition> conditions) throws Exception {

        boolean result = isMatchingCondition(jo, conditions.get(0));

        for (int i = 1; i < conditions.size(); i++) {
            String logicalOperator = conditions.get(i - 1).getLogicalOperator();
            if (logicalOperator.equals("&&")) {
                result = result && (isMatchingCondition(jo, conditions.get(i)));
            } else if (logicalOperator.equals("||")) {
                result = result || (isMatchingCondition(jo, conditions.get(i)));
            }
        }

        return result;
    }


    //"<", ">", "<=", ">=", "==", "!=", "=~", "in", "nin", "subsetof", "size", "empty", "notempty"
    public static boolean isMatchingCondition(JsonObject jo, Condition condition) {
        if (!condition.isValid()) {
            return false;
        }

        Set<String> keys = getKeys(jo);
        String left = condition.getLeft().trim();
        String operator = condition.getOperator().trim();
        String right = condition.getRight();
        if (!keys.contains(left)) {
            return false;
        }

        boolean result = false;
        String valueAsString = jo.get(left).getAsString();
        String valueAsJE = jo.get(left).toString();

        //to-do, refactoring, using enum;
        if (operator.equals("<")) {
            result = ((valueAsString.matches("(\\d+)\\.(\\d+)")) ? (Double.parseDouble(valueAsString) < Double.parseDouble(right)) : (Integer.parseInt(valueAsString) < Integer.parseInt(right)));
        } else if (operator.equals(">")) {
            result = ((valueAsString.matches("(\\d+)\\.(\\d+)")) ? (Double.parseDouble(valueAsString) > Double.parseDouble(right)) : (Integer.parseInt(valueAsString) > Integer.parseInt(right)));
        } else if (operator.equals(">=")) {
            result = ((valueAsString.matches("(\\d+)\\.(\\d+)")) ? (Double.parseDouble(valueAsString) >= Double.parseDouble(right)) : (Integer.parseInt(valueAsString) >= Integer.parseInt(right)));
        } else if (operator.equals("<=")) {
            result = ((valueAsString.matches("(\\d+)\\.(\\d+)")) ? (Double.parseDouble(valueAsString) <= Double.parseDouble(right)) : (Integer.parseInt(valueAsString) <= Integer.parseInt(right)));
        } else if (operator.equals("==")) {
            result = valueAsJE.equals(right);
        } else if (operator.equals("!=")) {
            result = !valueAsJE.equals(right);
        } else if (operator.equals("=~")) {
//            to-do
        } else if (operator.equals("in")) {

        } else if (operator.equals("nin")) {

        } else if (operator.equals("subsetof")) {

        } else if (operator.equals("size")) {

        } else if (operator.equals("empty")) {

        } else if (operator.equals("notempty")) {

        }

        return result;
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
     * @param path sample path : $.modules.RETURNS.maxView.store[1,3].book[@.category > 'fiction' and @.price < 10 or @.color == \"red\"].textSpans[0].text
     * @return minView.actions[] : [(2,2),(3,3)]
     * minView.actions[].action[] : [(2,5)]
     */
    public static Map<String, List<Filter>> getFilters(String path) throws Exception {
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
                //conditions
                List<Condition> conditions = Condition.getConditions(r);

                List<Filter> filters = new ArrayList<>();
                for (Condition condition : conditions) {
                    filters.add(condition);
                }
                if (conditions != null && conditions.size() > 0) {
                    res.put(prefix.toString().trim(), filters);
                }
            } else if (r.matches("(.*)([,:])(.*)") || r.contains("last()") || r.contains("first()") || r.contains("*") || r.matches("\\s{0,}(-{0,}\\d+)\\s{0,}")) {
                //ranges, [2:],[-2],[1,3,5] etc
                List<Range> ranges = getRange(r);
                List<Filter> filters = new ArrayList<>();
                for (Range range : ranges) {
                    filters.add(range);
                }
                if (ranges != null && ranges.size() > 0) {
                    res.put(prefix.toString().trim(), filters);
                }
            } else {
                throw new Exception("Invalid JsonPath : " + path);
            }

            path = path.substring(path.indexOf(']') + 1);
        }

        return res;
    }

    public static Set<String> getKeys(JsonObject o) {
        Set<String> keys = new TreeSet<String>();
        for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
            keys.add(entry.getKey().trim());
        }

        return keys;
    }

    public static void main(String[] args) throws Exception {
        /*
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/O.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();
        String path = "store[1:].book[@.category == 'fiction' && @.price < 10 || @.color == \\\"red\\\" || @.name size 10]";
        List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(o1, path);


        String r = "?(@.author==\"Evelyn Waugh\" && @.price > 12 || @.category == \"reference\")";
        List<Condition> conditions = Condition.getConditions(r);

*/
        JsonParser parser = new JsonParser();
        String json = convertFormattedJson2Raw(new File("/Users/jzfeng/Desktop/book.json"));
        JsonObject o1 = parser.parse(json).getAsJsonObject();
//        String path = "store.book[?(@.category == \"fiction\" && @.price < 10 || @.category == \"document\")]";
        String path = "store.book[-2].author";
        List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(o1, path);
        System.out.println("****************");
        for (JsonElementWithLevel je : res) {
            System.out.println(je);
        }

    }

                        /*only add JsonArray Element which matchings the filters.
                        if(list.size() > 0 && (list.get(0) instanceof Condition) ) {
                            List<Condition> c = new ArrayList<>();
                            for (Filter f : list) {
                                c.add((Condition) f);
                            }

                            if (isMatchingConditions(ja.get(j).getAsJsonObject(), c)) {
                                queue.offer(tmp);
                            }
                        }
                        */
}
