package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static String formatDate(LocalDate dateTime, String format) {
        return dateTime.format(DateTimeFormatter.ofPattern(format));
    }

}
