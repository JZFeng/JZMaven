package com.jz.annotation.com.jz.regExp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo {
    public static void main(String[] args) {
        String url = "https://www.m.qa.ebay.com/itm/11001224845";
        String regExp = ".*\\.(m\\.)(.*)\\.com";
        Pattern pattern = Pattern.compile(regExp);
        Matcher m = pattern.matcher(url);

        String newURL = "";
        while (m.find()) {
            newURL = new StringBuilder(url).replace(m.start(1), m.end(1), "uk." + m.group(1) + "stratus.").toString();
        }

        url = "https://www.m.qa.ebay.com/itm/11001224845";
        regExp = "(.*\\.)(m\\.)(.*\\.com)";
        String a = url.replaceAll(regExp, "$1"+"uk." + "$2"+"stratus."+"$3");

        System.out.println(a);
    }

    public static String replaceGroup(String regex, String source, int groupToReplace, String replacement) {
        return replaceGroup(regex, source, groupToReplace, 1, replacement);
    }

    public static String replaceGroup(String regex, String source, int groupToReplace, int groupOccurrence, String replacement) {
        Matcher m = Pattern.compile(regex).matcher(source);
        for (int i = 0; i < groupOccurrence; i++)
            if (!m.find()) return source; // pattern not met, may also throw an exception here
        return new StringBuilder(source).replace(m.start(groupToReplace), m.end(groupToReplace), replacement).toString();
    }


}
