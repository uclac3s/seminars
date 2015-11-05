package note;

import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import model.Record;
import model.SeminarLink;
import util.Service;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NoteSyncServlet extends HttpServlet {

    /**
     * insert new seminars to db
     * @param appUser
     * @param content
     */
    private void doInserts(com.google.appengine.api.users.User appUser, String content) {
        Gson gson = new Gson();
        Record r = gson.fromJson(content, Record.class);

        System.out.println("Inserted: " + r.toString());

        // examine the existed one with the same title and overwrite it
        List<Record> existed = ObjectifyService.ofy()
                .load()
                .type(Record.class) //
                .filter("title", r.title)
                .list();

        if (existed.size() > 0) {
            r.id = existed.get(0).id;
        }

        ObjectifyService.ofy().save().entities(r).now();
    }

    /**
     * update seminar links in db
     * @param appUser
     * @param linkContent
     */
    private void doSeminarLinks(User appUser, String linkContent) {
        System.out.println("***" + linkContent);
        Gson gson = new Gson();
        SeminarLink[] links = gson.fromJson(linkContent, SeminarLink[].class);

        for (SeminarLink link : links) {
            ObjectifyService.ofy().save().entities(link).now();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("utf-8");
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();


        // Action 1: add weekly seminars
        String insertContent = req.getParameter("insert");
        if (insertContent != null) {
            doInserts(appUser, insertContent);
        }

        // Action 2: update weekly seminar links
        String linkContent = req.getParameter("seminar_links");
        if (linkContent != null) {
            doSeminarLinks(appUser, linkContent);
        }

        /*String deletes = req.getParameter("delete");
        Record[] deletedRecords = new Record[0];
        if (deletes != null) {
            deletedRecords = doDeletes(appUser, deletes);
        }*/

        //String updates = req.getParameter("update");

        resp.setContentType("application/json");
        resp.getWriter().printf("{\"result\": \"success\"}");
    }

    /**
     * deletes only consider id, so content will has no affects
     */
    /*   private Record[] doDeletes(com.google.appengine.api.users.User appUser, String deletes) {
        Gson gson = new Gson();
        Record[] deletedRecords = gson.fromJson(deletes, Record[].class);
        List<String> ids = new ArrayList<String>();
        for (Record r: deletedRecords) {
            ids.add(r.id);
        }
        System.out.println("Deleted: " + Arrays.asList(deletedRecords));
        ObjectifyService.ofy().delete().type(Record.class)
                .parent(new User(appUser.getEmail())).ids(ids).now();
        return deletedRecords;
    }*/
}