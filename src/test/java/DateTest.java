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

        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH:mm:ss");

        String nowDateString = nowDate.format(formatter);
        System.out.println(nowDateString);








    }
}
