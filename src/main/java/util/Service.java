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

    /**
     * dateString should be in format like: 2015-09-12
     *
     * @return "0" if the dateString cannot be parsed
     */
    public static String getWeekInMillisOfDate(String dateString) {
        try {
            String[] parts = dateString.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1; // Jan is 0 in Calendar
            int dayOfMonth = Integer.parseInt(parts[2]);

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("PST"), Locale.US);

            cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);

            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // move to Monday of this week
            cal.add(Calendar.DAY_OF_MONTH, moveToMonday(cal.get(Calendar.DAY_OF_WEEK)));

            return cal.getTimeInMillis() + "";
        } catch (Exception e) {
            return "0";
        }
    }

    public static void main(String[] args) {
        // System.out.println(getCurrentWeekInMillis());
        // System.out.println(getWeekInMillisOfDate("2016-1-13"));
    }

}