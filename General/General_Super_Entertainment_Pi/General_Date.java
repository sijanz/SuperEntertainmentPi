package General.General_Super_Entertainment_Pi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Dustin
 */
public class General_Date {


    public static String get_Date_and_Time() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return (dateFormat.format(date)); // 2016/11/16 12:08:43

    }


    public static String get_Date() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        return (dateFormat.format(date)); // 2016/11/16 12:08:43

    }
}
