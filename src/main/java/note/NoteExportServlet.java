package note;

import com.googlecode.objectify.ObjectifyService;
import model.Record;
import util.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class NoteExportServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        String category = req.getParameter("category");
        System.out.println("Category: " + category);
        String day = req.getParameter("day");
        System.out.println("Day: " + day);

        List<Record> records;

        /* Category: Biomedicine, Engineering, CS, Chemistry, Business, Career, Social */

        // Get current week's records
        if (category != null) {
            records = ObjectifyService.ofy()
                    .load()
                    .type(Record.class) //
                    .filter("currentWeek", Service.getCurrentWeekInMillis())
                    .filter("category", category)
                    .list();
        } else {
            records = ObjectifyService.ofy()
                    .load()
                    .type(Record.class) //
                    .filter("currentWeek", Service.getCurrentWeekInMillis())
                    .list();
        }

        // sort records by date
        try {
            Collections.sort(records, new Comparator<Record>() {
                public int compare(Record o1, Record o2) {
                    return o1.timestamp.compareTo(o2.timestamp);
                }
            });
        } catch (Exception e) {
            System.err.println("Sort failed due to Exceptions.");
            e.printStackTrace();
        }

        // extra sort by categories
        if (day != null) {
            day = day.toLowerCase();
            Map<String, List<Record>> categoryToRecords = new HashMap<String, List<Record>>();
            for (Record record: records) {
                if (record.date.toLowerCase().startsWith(day)) {
                    if (categoryToRecords.get(record.category) == null) {
                        categoryToRecords.put(record.category, new ArrayList<Record>());
                    }
                    categoryToRecords.get(record.category).add(record);
                }
            }
            resp.getWriter().println(render(categoryToRecords));
        } else {
            resp.getWriter().println(render(records, category));
        }
    }

    private String render(List<Record> records, String category) {
        StringBuilder builder = new StringBuilder();
        if (category == null) {
            category = "All";
        }

        builder.append("<html>");

        builder.append("<head>");
        builder.append("<title>");
        builder.append("Week " + Service.getCurrentWeekInMillis() + " Category: " + category);
        builder.append("</title>");
        builder.append("</head>");


        builder.append("<body>");

        renderRecords(builder, category, records);

        builder.append("</body>");
        builder.append("</html>");

        return builder.toString();
    }

    private String render(Map<String, List<Record>> categoryToRecords) {
        StringBuilder builder = new StringBuilder();

        builder.append("<html>");

        builder.append("<head>");
        builder.append("<title>");
        builder.append("Week " + Service.getCurrentWeekInMillis());
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
            renderP(builder, "讲座题目", r.title, true);
            renderP(builder, "主讲人", r.speaker, false);
            renderP(builder, "时间", r.date, false);
            renderP(builder, "地点", r.room, false);
            renderP(builder, "备注", r.remark, false);
            renderEmptyLine(builder);
            index += 1;
        }
    }

    private void renderTitle(StringBuilder builder, int index) {
        renderP(builder, null, "讲座 [" + index + "]", true);
    }

    private void renderEmptyLine(StringBuilder builder) {
        renderP(builder, null, "", false);
    }

    private void renderP(StringBuilder builder, String prompt, String content, boolean isStrong) {
        builder.append("<p><span style=\"font-size: 16px;\">");
        if (prompt != null) {
            builder.append(prompt + ": ");
        }
        if (isStrong) {
            builder.append("<strong>" + content + "</strong>");
        } else {
            builder.append(content);
        }
        builder.append("<br></span></p>");
    }
}