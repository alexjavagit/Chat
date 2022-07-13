package ua.kiev.prog;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UserAuthorizationServlet extends HttpServlet {
    static final Map<String, String> uDetails = new HashMap<String, String>();
    private UserList userList = UserList.getInstance();

    static {
        // hardcode login credentials
        uDetails.put("alex", "alex");
        uDetails.put("boris", "boris");
        uDetails.put("alice", "alice");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);
        String bufStr = new String(buf, StandardCharsets.UTF_8);

        User user = User.fromJSON(bufStr);

        if (uDetails.containsKey(user.getLogin()) && uDetails.get(user.getLogin()).equals(user.getPass())){
            user.setStatus(true);
            userList.add(user);

            resp.setStatus(HttpServletResponse.SC_OK);
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {
        InputStream is = req.getInputStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);
            if (r > 0) bos.write(buf, 0, r);
        } while (r != -1);

        return bos.toByteArray();
    }


}
