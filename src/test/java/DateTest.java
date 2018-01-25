import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by sbt-filippov-vv on 24.01.2018.
 */
public class DateTest {

    @Test
    public void testDateValidator(){

        LocalDateTime startTimeFromDb = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");

        String nowDateString = startTimeFromDb.format(formatter);
        System.out.println(nowDateString);

        System.out.println(startTimeFromDb.format(formatter));

        LocalDateTime currentTime = startTimeFromDb.plusSeconds(60);

        System.out.println(currentTime.format(formatter));



        if (currentTime.isBefore(startTimeFromDb.plusSeconds(10))) {
            System.out.println("currentTime.isBefore(startTimeFromDb.plusSeconds(30)) : true");
        } else{
            System.out.println("currentTime.isBefore(startTimeFromDb.plusSeconds(30)) : false");
        }










    }
}
