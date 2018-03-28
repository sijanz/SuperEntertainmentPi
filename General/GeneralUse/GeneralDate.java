package General.GeneralUse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Simon, Dustin
 */
public class GeneralDate {


    /**
     * Returns the date and time in the following format: 2016/11/16 12:08:43.
     *
     * @return the date and time.
     */
    public static String getDateAndTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return (dateFormat.format(date));
    }


    /**
     * Returns the date in the following format: 2016/11/16.
     *
     * @return the date
     */
    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return (dateFormat.format(date));
    }
}
