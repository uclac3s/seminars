package util;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import model.Record;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Service {
    private static final AtomicLong idGenerator = new AtomicLong(Math.abs(new Random().nextLong()));

    public static com.google.appengine.api.users.User getCurrentUser() {
        UserService userService = UserServiceFactory.getUserService();
        com.google.appengine.api.users.User appUser = userService.getCurrentUser();  // Find out who the user is.
        return appUser;
    }

    public static String getAndIncrementNextId() {
        return idGenerator.getAndIncrement() + "";
    }

    public static String getCurrentWeekInMillis() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        // the start of week is Monday
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // System.out.println("Start of this week:       " + cal.getTime());
        // System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
        return cal.getTimeInMillis() + "";

        // start of the next week
        // cal.add(Calendar.WEEK_OF_YEAR, 1);
        // System.out.println("Start of the next week:   " + cal.getTime());
        // System.out.println("... in milliseconds:      " + cal.getTimeInMillis());
    }

    public static void main(String[] args) {
        System.out.println(getCurrentWeekInMillis());
    }

}