package com.hotelsystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static Date parseDate(String date) throws ParseException {
        Date new_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        return new_date;
    }
}
