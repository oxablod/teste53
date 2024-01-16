package com.teste.utils.date;


import com.teste.utils.AppConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private DateTimeUtils(){}

    public static LocalDateTime now() {
        return LocalDateTime.now(AppConstants.ZONE_ID_AMERICA_SAO_PAULO);
    }

    public static LocalDateTime toLocalDateTimeISO(String dateTimeISO) {
        if (dateTimeISO.contains(":")) {
            return LocalDateTime.parse(dateTimeISO, DateTimeFormatter.ISO_DATE_TIME);
        } else {
            LocalDate date = LocalDate.parse(dateTimeISO, DateTimeFormatter.ISO_DATE);
            return LocalDateTime.of(date, LocalTime.MIDNIGHT);
        }
    }
}
