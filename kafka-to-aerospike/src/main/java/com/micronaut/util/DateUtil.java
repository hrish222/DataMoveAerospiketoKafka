package com.micronaut.util;

import jakarta.inject.Singleton;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Singleton
public class DateUtil {

    public String getFormattedDate(long epochTimestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimestamp), ZoneId.systemDefault());

        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = formatter.format(dateTime);

        return formattedDate;
    }

}
