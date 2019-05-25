package com.jz.java.datetime;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class WorldClock {

    public static void main(String[] args) {
        System.out.println("Current New York time is : " + getCurrentDateTime("America/New_York"));
        System.out.println("Current Beijing time is : " + getCurrentDateTime("Asia/Shanghai"));
        System.out.println("Current Calfirnia time is : " + getCurrentDateTime("US/Pacific"));
    }

    public static String getCurrentDateTime(String zoneId) {
        Instant instant = Instant.ofEpochMilli(System.currentTimeMillis());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(ZonedDateTime.ofInstant(instant, ZoneId.of(zoneId)));
    }

}
