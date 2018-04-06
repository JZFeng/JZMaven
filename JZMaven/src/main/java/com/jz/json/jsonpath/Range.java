package com.jz.json.jsonpath;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//  x >= start && x <= end
public class Range implements Filter {
    private int start;
    private int end;

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isValid() {
        return (start < end) ? false : true;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }


    /**
     * @param path minView.actions[  2 ,  3].action[2:5].URL
     * @return minView.actions[] : [(2,2),(3,3)]
     * minView.actions[].action[] : [(2,5)]
     */
    public static Map<String, List<Filter>> getRanges(String path) {
        Map<String, List<Filter>> ranges = new LinkedHashMap<>();
        if (path == null || path.trim().length() == 0) {
            return ranges;
        }

        StringBuilder prefix = new StringBuilder();
        int index = 0;
        while ((index = path.indexOf('[')) != -1) {
            prefix.append(path.substring(0, index) + "[]");
            String r = path.substring(index + 1, path.indexOf(']')).trim();
            if (r.contains("@")) {
                List<Filter> conditions = Condition.getConditions(r);
                if (conditions != null && conditions.size() > 0) {
                    ranges.put(prefix.toString().trim(), conditions);
                }
            } else {
                List<Filter> range = getRange(r);
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
    public static List<Filter> getRange(String r) {
        List<Filter> result = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Start : " + start + " , end :" + end;
    }
}
