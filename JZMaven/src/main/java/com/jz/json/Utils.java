package com.jz.json;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<JsonElement> jsonArrayToList(JsonArray expected) {
        List<JsonElement> jsonElements = new ArrayList<JsonElement>(expected.size());
        for (int i = 0; i < expected.size(); ++i) {
            jsonElements.add(expected.get(i));
        }
        return jsonElements;
    }

    /**
     * Creates a cardinality map from {@code coll}.
     *
     * @param coll the collection of items to convert
     * @param <T>  the type of elements in the input collection
     * @return the cardinality map
     */
    public static <T> Map<T, Integer> getCardinalityMap(final Collection<T> coll) {
        Map map = new HashMap<T, Integer>();
        for (T item : coll) {
            Integer c = (Integer) (map.get(item));
            if (c == null) {
                map.put(item, new Integer(1));
            } else {
                map.put(item, new Integer(c.intValue() + 1));
            }
        }
        return map;
    }

    /**
     * Searches for the unique key of the {@code expected} JSON array.
     *
     * @param array the array to find the unique key of
     * @return the unique key if there's any, otherwise null
     */
    public static String findUniqueKey(JsonArray array) {
        // Find a unique key for the object (id, name, whatever)
        if (array.size() > 0 && (array.get(0) instanceof JsonObject)) {
            JsonObject o = ((JsonObject) array.get(0)).getAsJsonObject();
            Set<String> keys = getKeys(o);
            for (String candidate : keys) {
                if (isUsableAsUniqueKey(candidate, array)) {
//                    System.out.println("The unique key of JsonArray is ["  + candidate +"]");
                    return candidate;
                }
            }
        }

        return null;
    }


    /**
     * @param candidate is usable as a unique key if every element in the
     * @param array     is a JSONObject having that key, and no two values are the same.
     * @return true if the candidate can work as a unique id across array
     */

    public static boolean isUsableAsUniqueKey(String candidate, JsonArray array) {
        Set<JsonElement> seenValues = new HashSet<JsonElement>();
        for (int i = 0; i < array.size(); i++) {
            JsonElement item = array.get(i);
            if (item instanceof JsonObject) {
                JsonObject o = (JsonObject) item;
                if (o.has(candidate)) {
                    JsonElement value = o.get(candidate);
                    if (isSimpleValue(value) && !seenValues.contains(value)) {
                        seenValues.add(value);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    //get keys from a JsonObject
    public static Set<String> getKeys(JsonObject o) {
        Set<String> keys = new TreeSet<String>();
        for (Map.Entry<String, JsonElement> entry : o.entrySet()) {
            keys.add(entry.getKey().trim());
        }

        return keys;
    }

    //JsonPrimitive value as unique key
    public static boolean isSimpleValue(Object o) {
        return !(o instanceof JsonObject) && !(o instanceof JsonArray) && (o instanceof JsonPrimitive);
    }

    // build hashmap, KEY is UniqueKey's Value, VALUE is JsonObject;
    public static Map<JsonPrimitive, JsonObject> arrayOfJsonObjectToMap(JsonArray array, String uniqueKey) {
        Map<JsonPrimitive, JsonObject> valueMap = new HashMap<JsonPrimitive, JsonObject>();
        if (uniqueKey != null) {
            for (int i = 0; i < array.size(); ++i) {
                JsonObject jsonObject = (JsonObject) array.get(i);
                JsonPrimitive id = jsonObject.get(uniqueKey).getAsJsonPrimitive();
                valueMap.put(id, jsonObject);
            }
        }

        return valueMap;
    }

    //    refactor, using try with resource statement
    public static String convertFormattedJson2Raw(File f) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String json = br.readLine();
            StringBuilder sb = new StringBuilder();
            while (json != null && json.length() > 0) {
                json = json.trim();
                while (json.startsWith("\t")) {
                    json = json.replaceFirst("\t", "");
                }
                sb.append(json);
                json = br.readLine();
            }
            br.close();

            return sb.toString().trim();
        }

    }

    public static CompareResult applyFilters(CompareResult result, Set<String> filters) {

        if (filters == null || filters.size() == 0) {
            return result;
        }

        List<Failure> failures = result.getFailures();
        Iterator<Failure> itr = failures.iterator();
        while (itr.hasNext()) {
            Failure f = itr.next();
            String field = f.getField();
            if (filters.contains(field)) {
                itr.remove();
            }
        }

        return new CompareResult(failures);
    }

    public static boolean allSimpleValues(JsonArray array) {
        for (int i = 0; i < array.size(); ++i) {
            if (!isSimpleValue(array.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean allJsonObjects(JsonArray array) {
        for (int i = 0; i < array.size(); ++i) {
            if (!(array.get(i) instanceof JsonObject)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param source the source of JsonObject
     *               Sample JsonObject:{"store": {"book": [{"category": "reference","author": "Nigel Rees","title": "Sayings of the Century","price": 8.95},{"category": "fiction","author": "Evelyn Waugh","title": "Sword of Honour","price": 12.99},{"category": "fiction","author": "Herman Melville","title": "Moby Dick","isbn": "0-553-21311-3","price": 8.99},{"category": "fiction","author": "J. R. R. Tolkien","title": "The Lord of the Rings","isbn": "0-395-19395-8","price": 22.99}],"bicycle": {"color": "red","price": 19.95}},"expensive": 10}
     * @param path   now supporting standard JsonPath. Partial JsonPath is also supported.
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

    public static List<JsonElement> getJsonElementByPath(JsonObject source, String path) {
        List<JsonElement> result = new ArrayList<>();
        List<JsonElementWithLevel> res = getJsonElementWithLevelByPath(source, path);
        for(JsonElementWithLevel e : res) {
            result.add(e.getJsonElement());
        }

        return result;
    }

    private static List<JsonElementWithLevel> getJsonElementWithLevelByPath(JsonObject source, String path) {
        List<JsonElementWithLevel> result = new ArrayList<>();
        if (path == null || path.length() == 0 || source == null || source.isJsonNull()) {
            return result;
        }

        Map<String, List<Range>> ranges = generateRanges(path);
        Map<String, List<Range>> matchedRanges = new LinkedHashMap<>();
        String regex = generateRegex(path);

        Queue<JsonElementWithLevel> queue = new LinkedList<JsonElementWithLevel>();
        queue.offer(new JsonElementWithLevel(source, "$"));
        while (!queue.isEmpty()) {
            int size = queue.size();
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
                        if (level.matches(regex) && isMatched(level, ranges, matchedRanges, length)) {
                            result.add(tmp);
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
                        if (level.matches(regex) && isMatched(level, ranges, matchedRanges, length)) {
                            result.add(tmp);
                        }
                    }
                }
            }
        }


        return result;
    }


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
        if (path == null || path.length() == 0) {
            return ranges;
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            String r = path.substring(index + 1, path.indexOf(']'));
            List<Range> range = generateRange(r);
            if (range != null && range.size() > 0) {
                ranges.put(prefix.toString().trim(), range);
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
     *          to-do : supporting filter; Owner: JZ
     *          $..book[?(@.isbn)]	All books with an ISBN number
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
        } else if (Integer.parseInt(r) < 0) { //[-2] ?????
            result.add(new Range(Integer.parseInt(r), Integer.parseInt(r)));
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        //get filters
        String filters = "@.category == 'fiction' && @.price < 10 || @.color == \"red\"";
        String[] strs = filters.split("\\s{0,}&&\\s{0,}|\\s{0,}\\|\\|\\s{0,}");
        List<String> operators = new ArrayList<>();
        for(int i = 0 ; i < strs.length - 1; i++) {
            String operator = filters.substring( filters.indexOf(strs[i]) + strs[i].length(), filters.indexOf(strs[i + 1])).trim();
            operators.add(operator);
            System.out.println(operator);
        }

        if(strs.length != operators.size() + 1) {
            new Exception("Field name has \"&&\" or \"||\".");
        }


        System.out.println("*******************");
        for(String str : strs) {
            String regExp = "(\\s{0,}[><=!]{1}[=~]{0,1}\\s{0,})";
            Pattern pattern = Pattern.compile(regExp);
            Matcher m = pattern.matcher(str);
            while(m.find()) {
                String[] items = str.split(regExp);
                if(items.length > 0) {
                    System.out.println(items[0]);
                }
                if(items.length > 1) {
                    System.out.println(items[1]);
                }

                int count = m.groupCount();
                for(int i = 1 ; i <= count; i++ ) {
                    String s = m.group(i);
                    System.out.println(s);
                }
            }

        }

        System.out.println("*******************");

        //get elements of a filter
        String filter  = "category     size 'fiction'";
        String[] fields = filter.split(" {1,}");
        for(String str : strs) {
            System.out.println(str);
        }



    }

    public class Condition {
        private String left;
        private String right;
        private String operator;
        private final Set<String> operators = new HashSet<>();//how to intianlize it? It should be a global variable.

        public boolean isValid(){
            if(left == null || left.length() == 0) {
                return false;
            } else if (right == null || right.length() == 0) {
                return false;
            } else if (operator == null || operator.length() == 0) {
                return false;
            } else if (!operators.contains(operator)) {
                return false;
            }

            return true;
        }

        Condition(String left, String operator, String right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }

}

