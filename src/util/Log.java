package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Curt
 */
public final class Log {
    
    public static void info(String message) {
        StackTraceElement at = Thread.currentThread().getStackTrace()[2];
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(
            String.format("%s : %s:%s : %s" ,
            format.format(new Date()),
            at.getFileName() , at.getLineNumber(),
            message)
        );
    }
}
