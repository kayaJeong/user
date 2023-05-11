package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.Hashtable;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionDao extends DataObject {
    private HttpServletRequest request;
    private String sessionId = null;
    private int userId = 0;
    private DataSet sessionData = null;
    private String keyName = "_sessid";
    
    public SessionDao() {
        this.table = "TB_SESSION";
        this.PK = "id";
    }

    public SessionDao(HttpServletRequest request, HttpServletResponse response) {
        this.table = "TB_SESSION";
        this.PK = "id";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(int i=0; i<cookies.length; i++) {
                if(cookies[i].getName().equals(keyName)) {
                    sessionId = cookies[i].getValue();
                }
            }
        }
        if(sessionId == null) {
            sessionId = Malgn.sha1("" + System.currentTimeMillis() + Malgn.getUniqId());
            Cookie cookie = new Cookie(keyName, sessionId);
            cookie.setPath("/");
            if(request.isSecure()) cookie.setSecure(true);
            response.addCookie(cookie);
        }
    }



}
