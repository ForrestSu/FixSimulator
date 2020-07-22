package com.simulator.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String DateTimeFormat(LocalDateTime date) {

        return fmt.format(date);
    }
}
