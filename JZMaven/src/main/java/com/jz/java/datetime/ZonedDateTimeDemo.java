package com.jz.java.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

public class ZonedDateTimeDemo {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        ZonedDateTime znow = now.atZone(ZoneId.systemDefault());
        System.out.println(znow.getZone());

        System.out.println(znow.withZoneSameInstant(ZoneId.of("Asia/Shanghai")));

        System.out.println(ZonedDateTime.now());
        System.out.println(ZonedDateTime.now(ZoneId.of("America/New_York")));

        Instant instant = znow.toInstant();
        System.out.println("Instant epoch seconds: " + instant.getEpochSecond());
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("Asia/Shanghai"));
        System.out.println(zdt);
        zdt = ZonedDateTime.ofInstant(instant, ZoneId.of("America/New_York"));
        System.out.println(zdt);

        /*String[] zones = TimeZone.getAvailableIDs();
        for(String zone : zones) {
            System.out.println(zone);
        }
        System.out.println(ZonedDateTime.now());*/


        LocalDateTime localStartTime = LocalDateTime.of(2016,12,1,7,50);
        System.out.println("\r\nDeparture Time: " + localStartTime);

        LocalTime flightTime = LocalTime.of(12,15);
        String arrivalZone = "America/New_York";

        ZonedDateTime arrivalTime = calculateArrivalTime(localStartTime, flightTime, arrivalZone);

        System.out.println("Arrival Time: " + arrivalTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai")));
        System.out.println("Arrival Local Time : " + arrivalTime);

        System.out.println(epochToString( ZonedDateTime.now().toEpochSecond()* 1000, Locale.CHINA, "Asia/Shanghai" ));
        System.out.println(epochToString(ZonedDateTime.now().toEpochSecond() * 1000, Locale.US, "America/New_York"));
        System.out.println(epochToString(ZonedDateTime.now().toEpochSecond() * 1000, Locale.US, "US/Pacific"));
    }

    public static ZonedDateTime calculateArrivalTime(LocalDateTime localStartTime, LocalTime flightTime, String arrivalZone) {
        ZonedDateTime startTime = localStartTime.atZone(ZoneId.of("Asia/Shanghai"));

        int flightHour = flightTime.getHour();
        int flightMinutes = flightTime.getMinute();

        startTime = startTime.plusHours(flightHour).plusMinutes(flightMinutes);

        return startTime.withZoneSameInstant(ZoneId.of(arrivalZone));

    }

    public static String epochToString(long epoch, Locale locale, String zoneId) {
        Instant instant = Instant.ofEpochMilli(epoch);
        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM).withLocale(locale);

        return dtf.format(ZonedDateTime.ofInstant(instant, ZoneId.of(zoneId)));
    }
}
