package com.teste.utils.date;


import com.teste.utils.AppConstants;

import java.time.LocalDate;

public class DateUtils {

    private DateUtils(){}

    public static LocalDate now() {
        return LocalDate.now(AppConstants.ZONE_ID_AMERICA_SAO_PAULO);
    }
}
