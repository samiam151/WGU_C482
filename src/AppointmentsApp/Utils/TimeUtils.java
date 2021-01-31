package AppointmentsApp.Utils;

import java.time.*;
import java.util.TimeZone;

public class TimeUtils {
    static LocalDate date = LocalDate.now();
    static ZoneId estId = ZoneId.of("America/New_York");
    static ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

    /**
     * @return LocalDateTime object representing business open at 8:00AM EST
     */
    public static LocalDateTime getLocalStartTime(){
        LocalTime start = LocalTime.of( 8, 0 );
        ZonedDateTime businessStartEst = ZonedDateTime.of(date, start, estId);
        ZonedDateTime businessStartLocal = businessStartEst.withZoneSameInstant(localZone);

        return businessStartLocal.toLocalDateTime();
    }

    /**
     * @return LocalDateTime object representing business close at 10:00PM EST
     */
    public static LocalDateTime getLocalEndTime(){
        LocalTime end = LocalTime.of(22, 0 );
        ZonedDateTime businessEndEst = ZonedDateTime.of(date, end, estId);
        ZonedDateTime businessEndLocal = businessEndEst.withZoneSameInstant(localZone);

        return businessEndLocal.toLocalDateTime();
    }
}
