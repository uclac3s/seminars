package note;

import com.googlecode.objectify.ObjectifyService;
import model.SeminarLink;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SeminarLinksServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        /*String out = "[" +
        " {'key': 'k1', 'value': 'v1'}," +
        " {'key': 'k2', 'value': 'v2'}," +
        " {'key': 'key3', 'value': 'val3'}," +
        " {'key': 'key4', 'value': 'val4'}" +
        " ]";*/

        List<SeminarLink> links = ObjectifyService.ofy()
                .load()
                .type(SeminarLink.class)
                .list();

        String out = "";
        for (SeminarLink l: links) {
            if (out.length() > 0) {
                out += ", ";
            }
            out += format(l);
        }
        out = "{ " + out + " }";
        out = out.replace("'", "\"");

        // enable cross domain
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println(out);
    }

    private String format(SeminarLink l) {
        return "'" + l.category + "': " + "'" + l.url + "'";
    }
}