package note;

import com.googlecode.objectify.ObjectifyService;
import model.Record;
import util.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteExportServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Cache-Control", "max-age=3600");

        String category = req.getParameter("category");
        System.out.println("Category: " + category);

        List<Record> records;

        /*
        <option value="Biomedicine">Biomedicine</option>
        <option value="Engineering">Engineering/Materials Science</option>
        <option value="CS">CS/Math</option>
        <option value="Chemistry">Chemistry/Physics/Earth Science</option>
        <option value="Business">Business/Economics/Law</option>
        <option value="Career">Career Development</option>
        <option value="Social">Social Science/Humanities</option>
        */

        // TODO: support retrieve arbitrary week's records
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

        // order records
        Collections.sort(records, new Comparator<Record>() {
            public int compare(Record o1, Record o2) {
                return o1.timestamp.compareTo(o2.timestamp);
            }
        });

        resp.getWriter().println(render(records, category));
    }

    private String render(List<Record> records, String category) {
        StringBuilder htmlBuilder = new StringBuilder();
        if (category == null) {
            category = "All";
        }

        htmlBuilder.append("<html>");

        htmlBuilder.append("<head>");
        htmlBuilder.append("<title>");
        htmlBuilder.append("Week " + Service.getCurrentWeekInMillis() + " Category: " + category);
        htmlBuilder.append("</title>");
        htmlBuilder.append("</head>");


        htmlBuilder.append("<body>");
        htmlBuilder.append("<h2>" + category + "</h2>");
        int index = 1;
        for (Record r: records) {
            renderTitle(htmlBuilder, index);
            renderP(htmlBuilder, "讲座题目", r.title, true);
            renderP(htmlBuilder, "主讲人", r.speaker, false);
            renderP(htmlBuilder, "时间", r.date, false);
            renderP(htmlBuilder, "地点", r.room, false);
            renderP(htmlBuilder, "备注", r.remark, false);
            renderEmptyLine(htmlBuilder);
            index += 1;
        }
        htmlBuilder.append("</body>");

        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
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