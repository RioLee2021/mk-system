package net.system.mk.commons.utils;


import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author USER
 */
public class DateTimeUtils {

    public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd");

    private static ZoneOffset offset = ZoneOffset.ofHours(8);

    public static ZoneOffset getOffset() {
        return offset;
    }

    public static void setOffset(ZoneOffset offset) {
        DateTimeUtils.offset = offset;
    }


    public static LocalDateTime toLocalDateTime(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(offset).toLocalDateTime();
    }

    public static LocalDate toLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(offset).toLocalDate();
    }

    public static Integer todayNumber(){
        return Integer.valueOf(todayStart().format(YYYYMMDD));
    }

    public static long toEpochMilli(LocalDateTime value) {
        return value.toInstant(offset).toEpochMilli();
    }

    public static long toEpochMilli(LocalDate value) {
        return value.atStartOfDay(offset).toInstant().toEpochMilli();
    }

    public static long nowTimestamp() {
        return toEpochMilli(nowDateTime());
    }

    public static String strTimestamp10bit(){
        return String.valueOf(nowTimestamp()/1000);
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now(offset);
    }

    public static LocalDateTime todayStart(){
        return nowDateTime().toLocalDate().atTime(0,0,0);
    }

    public static LocalDateTime todayEnd(){
        return nowDateTime().toLocalDate().atTime(23,59,59);
    }

    public static Integer dateToNumber(LocalDate today) {
        return Integer.valueOf(today.format(YYYYMMDD));
    }
}
