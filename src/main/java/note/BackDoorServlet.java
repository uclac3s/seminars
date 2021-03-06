package note;

import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import model.Record;
import model.SeminarLink;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BackDoorServlet extends HttpServlet {

    /**
     * insert new seminars to db
     * @param appUser
     * @param content
     */
    private boolean doInserts(User appUser, String content, String isDelete) {
        Gson gson = new Gson();
        System.out.println("###\n" + content);
        Record r = gson.fromJson(content, Record.class);

        // examine the existed one with the same title and overwrite it
        // TODO: not distinguish cases
        List<Record> existed = ObjectifyService.ofy()
                .load()
                .type(Record.class) //
                .filter("title", r.title)
                .list();

        if (existed.size() > 0) {
            if (isDelete.equals("true")) {
                // set r to the existed record
                r = existed.get(0);
                System.out.println("Delete: " + r.toString());
                ObjectifyService.ofy().delete().type(Record.class).id(r.id).now();
            } else {
                System.out.println("Find an existed record.");
                return false;
            }
        } else {
            if (isDelete.equals("true")) {
                System.out.println("Delete a non-exist record : " + r.toString());
                return true;
            } else {
                System.out.println("Insert: " + r.toString());
                ObjectifyService.ofy().save().entities(r).now();
            }
        }

        return true;
    }

    /**
     * update seminar links in db
     * @param appUser
     * @param linkContent
     */
    private boolean doSeminarLinks(User appUser, String linkContent) {
        System.out.println("***" + linkContent);
        Gson gson = new Gson();
        SeminarLink[] links = gson.fromJson(linkContent, SeminarLink[].class);

        for (SeminarLink link : links) {
            ObjectifyService.ofy().save().entities(link).now();
        }

        return true;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("utf-8");

        // Backdoor protection
        String psd = req.getParameter("password");
        if (psd == null || !psd.equals("ithinkthisisabackdoorpassword")) {
            resp.getWriter().printf("{\"result\": \"Failed due to authorization!\"}");
            return;
        }

        boolean result = true;

        // Action 1 & 2: add/delete weekly seminars
        String insertContent = req.getParameter("insert");
        if (insertContent != null) {
            result = doInserts(null, insertContent, req.getParameter("delete"));
        }

        // Action 3: update weekly seminar links
        String linkContent = req.getParameter("seminar_links");
        if (linkContent != null) {
            result = doSeminarLinks(null, linkContent);
        }

        resp.setContentType("application/json");
        if (result) {
            resp.getWriter().printf("{\"result\": \"Success!\"}");
        } else {
            resp.getWriter().printf("{\"result\": \"Failed: same title exists, please delete if you want to overwrite!\"}");
        }
    }
}