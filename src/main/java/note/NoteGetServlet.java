package note;

import util.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoteGetServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();

        /*
        String out = "[" +
                " {'key': 'k1', 'value': 'v1'}," +
                " {'key': 'k2', 'value': 'v2'}," +
                " {'key': 'key3', 'value': 'val3'}," +
                " {'key': 'key4', 'value': 'val4'}" +
                " ]";*/
        // out = out.replace("'", "\"");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().println("unimplemented");
    }
}