package note;

import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import model.Record;
import util.Service;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteSyncServlet extends HttpServlet {
    private Record doInserts(com.google.appengine.api.users.User appUser, String content) {
        Gson gson = new Gson();
        Record r = gson.fromJson(content, Record.class);

        System.out.println("Inserted: " + r.toString());
        ObjectifyService.ofy().save().entities(r).now();
        return r;
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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("utf-8");
        com.google.appengine.api.users.User appUser = Service.getCurrentUser();

        // do inserts
        String insertContent = req.getParameter("insert");
        System.out.println("#" + insertContent);
        Record r = doInserts(appUser, insertContent);
        System.out.println("#" + r);

        // do deletes
        /*
        String deletes = req.getParameter("delete");
        Record[] deletedRecords = new Record[0];
        if (deletes != null) {
            deletedRecords = doDeletes(appUser, deletes);
        }*/

        // TODO: implement do updates
        //String updates = req.getParameter("update");

        resp.setContentType("application/json");
        resp.getWriter().printf("inserted");
    }
}