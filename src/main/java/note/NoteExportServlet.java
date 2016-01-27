package note;

import com.googlecode.objectify.ObjectifyService;
import model.Record;
import util.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.logging.Logger;

public class NoteExportServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(NoteExportServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        final String category = req.getParameter("category");
        log.info("Category: " + category);

        final String day = req.getParameter("day");
        log.info("Day: " + day);

        final String date = req.getParameter("date");
        log.info("Date: " + date);

        List<Record> records;

        /* Category: Biomedicine, Engineering, CS, Chemistry, Earth, Business, Career, Social */

        // Specify a week
        final String weekInMillis = (date == null) ? Service.getCurrentWeekInMillis() : Service.getWeekInMillisOfDate(date);

        // Get that week's records
        if (category != null) {
            records = ObjectifyService.ofy()
                    .load()
                    .type(Record.class) //
                    .filter("currentWeek", weekInMillis)
                    .filter("category", category)
                    .list();
        } else {
            records = ObjectifyService.ofy()
                    .load()
                    .type(Record.class) //
                    .filter("currentWeek", weekInMillis)
                    .list();
        }

        // sort records by date
        try {
            Collections.sort(records, new Comparator<Record>() {
                public int compare(Record o1, Record o2) {
                    if (o1.timestamp == null ^ o2.timestamp == null) {
                        return (o1.timestamp == null) ? -1 : 1;
                    }

                    if (o1.timestamp == null && o2.timestamp == null) {
                        return 0;
                    }

                    return o1.timestamp.compareTo(o2.timestamp);
                }
            });
        } catch (Exception e) {
            log.warning("Sort failed due to Exceptions.");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            log.warning(sw.toString());
            //e.printStackTrace();
        }

        // log.info(records.toString());
        // put record in its own category
        if (day != null) {
            String dayLower = day.toLowerCase();
            Map<String, List<Record>> categoryToRecords = new HashMap<String, List<Record>>();
            for (Record record: records) {
                if (record.date.toLowerCase().contains(dayLower)) {
                    if (categoryToRecords.get(record.category) == null) {
                        categoryToRecords.put(record.category, new ArrayList<Record>());
                    }
                    categoryToRecords.get(record.category).add(record);
                }
            }
            // log.info(categoryToRecords.toString());
            resp.getWriter().println(render(categoryToRecords, weekInMillis));
        } else {
            resp.getWriter().println(render(records, category, weekInMillis));
        }
    }

    private String render(List<Record> records, String category, String weekInMillis) {
        StringBuilder builder = new StringBuilder();
        if (category == null) {
            category = "All";
        }

        builder.append("<html>");

        builder.append("<head>");
        builder.append("<title>");
        builder.append("Week " + weekInMillis + " Category: " + category);
        builder.append("</title>");
        builder.append("</head>");


        builder.append("<body>");

        renderRecords(builder, category, records);

        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }

    private String render(Map<String, List<Record>> categoryToRecords, String weekInMillis) {
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");

        builder.append("<head>");
        builder.append("<title>");
        builder.append("Week " + weekInMillis);
        builder.append("</title>");
        builder.append("</head>");


        builder.append("<body>");

        // sort map by keys
        SortedSet<String> categories = new TreeSet<String>(categoryToRecords.keySet());
        for (String category : categories) {
            List<Record> records = categoryToRecords.get(category);
            renderRecords(builder, category, records);
        }

        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }

    private void renderRecords(StringBuilder builder, String category, List<Record> records) {
        builder.append("<h2>" + category + "</h2>");

        int index = 1;
        for (Record r: records) {
            renderTitle(builder, index);
            renderSpan(builder, "讲座题目", r.title, true);
            renderSpan(builder, "主讲人", r.speaker, false);
            renderSpan(builder, "时间", hackDate(r.date), false);
            renderSpan(builder, "地点", r.room, false);
            renderSpan(builder, "备注", r.remark, false);
            renderEmptyLine(builder);
            index += 1;
        }
    }

    // hack: not recommended
    private String hackDate(String date) {
        return date.replace("pm", "PM").replace("am", "AM").replace("0PM", "0 PM").replace("0AM", "0 AM");
    }

    private void renderTitle(StringBuilder builder, int index) {
        renderSpan(builder, null, "讲座 [" + index + "]", true);
    }

    private void renderEmptyLine(StringBuilder builder) {
        renderP(builder, null, "", false);
    }

    private void renderP(StringBuilder builder, String prompt, String content, boolean isStrong) {
        builder.append("<p>");
        renderSpan(builder, prompt, content, isStrong);
        builder.append("</p>");
    }

    private void renderSpan(StringBuilder builder, String prompt, String content, boolean isStrong) {
        builder.append("<span style=\"font-size: 16px;\">");
        if (prompt != null) {
            builder.append(prompt + ": ");
        }
        if (isStrong) {
            builder.append("<strong>" + content + "</strong>");
        } else {
            builder.append(content);
        }
        builder.append("<br></span>");
    }
}