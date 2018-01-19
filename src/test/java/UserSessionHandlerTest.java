import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sbt-filippov-vv on 19.01.2018.
 */
public class UserSessionHandlerTest {

    @Test
    public void dateFormatterTest(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        //dateFormat.format(date);
        System.out.println(dateFormat.format(date));
    }

}
