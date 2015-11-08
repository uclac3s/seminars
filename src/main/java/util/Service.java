package util;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.*;
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

    private static int moveToMonday(int today) {
        switch (today) {
            case Calendar.MONDAY: return 0;
            case Calendar.TUESDAY: return -1;
            case Calendar.WEDNESDAY: return -2;
            case Calendar.THURSDAY: return -3;
            case Calendar.FRIDAY: return -4;
            case Calendar.SATURDAY: return -5;
            case Calendar.SUNDAY: return -6;
        }
        return 0;
    }

    public static String getCurrentWeekInMillis() {
        // get today and clear time of day
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"), Locale.US);

        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // move to Monday of this week
        cal.add(Calendar.DAY_OF_MONTH, moveToMonday(cal.get(Calendar.DAY_OF_WEEK)));

        return cal.getTimeInMillis() + "";
    }

    public static void main(String[] args) {
        System.out.println(getCurrentWeekInMillis());
    }

}