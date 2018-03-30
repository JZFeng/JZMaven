package com.jz.annotation.com.jz.regExp;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpDemo {
    public static void main(String[] args) {
        String baseUrl = "https://www.m.qa.ebay.com/itm/11001224845";
        System.out.println(getURLbySite("DE , AU, UK ", baseUrl));

        String url = "http://viexpsvc.qa.ebay.com/experience/listing_details/v1/view_item?item_id=230007897316&inputoption=image.seller.avatar.200X200-jpg-l%2Creviewstats%2Cdescription%2Cfitmentsvc%2Cfitmentsvcv2%2Csignals%2Crichsnpt&fieldgroups=compact&fieldgroups=compatibility&fieldgroups=review&fieldgroups=warranty&fieldgroups=vehicleHistoryReport&fieldgroups=statusmessage&fieldgroups=productqna&modules=VLS";
        String a = converttoOldendpoint(url);
        System.out.println(url);
        System.out.println(a);

    }

    public static List<String> getURLbySite(String site, String baseUrl) {
        List<String> urls = new ArrayList<>();
        if (site == null || baseUrl == null || site.length() == 0 || baseUrl.length() == 0) {
            return urls;
        }

        String[] sites = site.split(",");
        if (sites.length > 0) {
            for (String s : sites) {
                String regExp = ".*\\.(m\\.)(.*)\\.com";
                Pattern pattern = Pattern.compile(regExp);
                Matcher m = pattern.matcher(baseUrl);

                while (m.find()) {
                    String newURL = new StringBuilder(baseUrl).replace(m.start(1), m.end(1), s.trim().toLowerCase() + "." + m.group(1) + "stratus.").toString();
                    urls.add(newURL);
                }
            }
        }

        return urls;
    }

    public static String converttoOldendpoint(String newEndpoint) {
        if (newEndpoint == null || newEndpoint.length() == 0) {
            return "";
        }

        String regExp = "(.*)\\?item_id=(\\d{10,})&(.*)";
        String oldEndpoint = newEndpoint.replaceAll(regExp, "$1" + "/" + "$2" + "?" + "$3");

        return oldEndpoint;
    }


}
