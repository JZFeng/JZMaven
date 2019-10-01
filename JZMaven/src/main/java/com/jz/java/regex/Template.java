package com.jz.java.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//模版引擎
public class Template {
    private final String template;
    private final Pattern pattern = Pattern.compile("\\$\\{(\\w+)}");

    Template(String template) {
        this.template = template;
    }

    private String render(Map<String, String> data) {
        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb, data.get(matcher.group(1)));
        }

        matcher.appendTail(sb);

        return sb.toString().trim();
    }


    public static void main(String[] args) {
        Template template = new Template("Hello, ${name}! You are learning ${lang}!");
        Map<String, String> data = new HashMap<>();
        data.put("name", "Bob");
        data.put("lang", "Java");

        System.out.println(template.render(data));
    }


}
