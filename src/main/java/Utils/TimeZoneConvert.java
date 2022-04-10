package Utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

/** Makes conversions between times
 * @author Alex Bader
 * @version 1.0
 */

public abstract class TimeZoneConvert {

    /**
     *
     * @param utcTime the utcTime to convert to local time
     * @return the local time conversion from utc
     */
    public static String utcTimeToLocalTime(String utcTime) {
        String[] split = utcTime.split(" ");
        String dateTime = split[0] + "T" + split[1] + "Z";
        Instant instant = Instant.parse(dateTime);
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.toString();
    }

    /**
     *
     * @param localTime the localTime to convert to utc time
     * @return the utc time conversion from local time
     */
    public static String localTimeToUtc(String localTime) {
        String[] split1 = localTime.split(" ")[0].split("-");
        String[] split2 = localTime.split(" ")[1].split(":");

        int year = Integer.parseInt(split1[0]);
        int month = Integer.parseInt(split1[1]);
        int day = Integer.parseInt(split1[2]);

        int hour = Integer.parseInt(split2[0]);
        int min = Integer.parseInt(split2[1]);

        LocalDate userDate = LocalDate.of(year, month, day);
        LocalTime userTime = LocalTime.of(hour, min);
        ZonedDateTime ZDT = ZonedDateTime.of(userDate, userTime, ZoneId.systemDefault());

        return ZDT.withZoneSameInstant(ZoneId.of("UTC")).toString().replace("T", " ").replace("Z", "")
                .replace("[U C]", "") + ":00";
    }

    /**
     *
     * @param localTime the localTime to convert to est time
     * @return the est time conversion from local time
     */
    public static String localTimeToEst(String localTime) {
        String[] split1 = localTime.split(" ")[0].split("-");
        String[] split2 = localTime.split(" ")[1].split(":");

        int year = Integer.parseInt(split1[0]);
        int month = Integer.parseInt(split1[1]);
        int day = Integer.parseInt(split1[2]);

        int hour = Integer.parseInt(split2[0]);
        int min = Integer.parseInt(split2[1]);

        LocalDate userDate = LocalDate.of(year, month, day);
        LocalTime userTime = LocalTime.of(hour, min);
        ZonedDateTime ZDT = ZonedDateTime.of(userDate, userTime, ZoneId.systemDefault());
        ZonedDateTime est = ZDT.withZoneSameInstant(ZoneId.of("America/New_York"));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:00");
        return est.format(format);
    }
}
