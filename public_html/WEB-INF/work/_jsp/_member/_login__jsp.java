/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._member;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _login__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    javax.servlet.ServletContext application = _jsp_application;
    com.caucho.jsp.PageContextImpl pageContext = _jsp_application.getJspApplicationContext().allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);
    javax.servlet.jsp.PageContext _jsp_parentContext = pageContext;
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    response.setContentType("text/html; charset=utf-8");
    request.setCharacterEncoding("UTF-8");
    try {
      

//\uac1d\uccb4
Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
try { f.setRequest(request); }
catch(Exception ex) { out.print("Overflow file size. - " + ex.getMessage()); return; }

Page p = new Page();
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);

//\ubcc0\uc218
int userId = 0;
String loginId = "";
String userName = "";
String userEmail = "";
String userType = "";
String sysToday = m.time("yyyyMMdd");
String sysNow = m.time("yyyyMMddHHmmss");
boolean loginBlock = false;

//\ub85c\uadf8\uc778 \uc5ec\ubd80 \uccb4\ud06c
Auth auth = new Auth(request, response);
auth.loginURL = "/member/login.jsp";
auth.keyName = "ENTER2022";

if(auth.isValid()) {
    userId = auth.getInt("USERID");
    loginId = auth.getString("LOGINID");
    userName = auth.getString("USERNAME");
    userEmail = auth.getString("EMAIL");
    userType = auth.getString("TYPE");
    loginBlock = true;
}

p.setVar("login_block", loginBlock);
p.setVar("SYS_TITLE", "<ENTER \"_\"> \uc624\ud508 \ub354 \uc774\ub7ec\ub2dd\uc0ac\uc774\ud2b8!");
p.setVar("SYS_LOGINID", loginId);
p.setVar("SYS_USERNAME", userName);
p.setVar("SYS_USEREMAIL", userEmail);
p.setVar("SYS_USERKIND", userType);
p.setVar("SYS_TODAY", sysToday);
p.setVar("SYS_NOW", sysNow);

      out.write('\n');
      

String ch = "member";


      

//\ub85c\uadf8\uc778
if(userId != 0) {
    m.redirect("../mypage/modify_verify.jsp");
    return;
}

//\uac1d\uccb4
UserDao user = new UserDao();
UserLoginDao userLogin = new UserLoginDao();

//\ud3fc\uccb4\ud06c
f.addElement("id", null, "hname:'\uc544\uc774\ub514', required:'Y'");
f.addElement("passwd", null, "hname:'\ube44\ubc00\ubc88\ud638', required:'Y'");

if(m.isPost() && f.validate()) {
    String id = f.get("id");
    String passwd = m.encrypt(f.get("passwd"), "SHA-256");
    DataSet info = user.find("login_id = ? AND status IN (1, -1)", new Object[] {id}, 1);

    //\uc874\uc7ac\ud558\uc9c0 \uc54a\ub294 \uc544\uc774\ub514
    if(!info.next()) {
        m.jsAlert("\uc544\uc774\ub514/\ube44\ubc00\ubc88\ud638\ub97c \ud655\uc778\ud558\uc138\uc694.\\n\ub85c\uadf8\uc778 5\ud68c \uc624\ub958 \uc2dc \ub85c\uadf8\uc778\uc774 \uc81c\ud55c\ub429\ub2c8\ub2e4.");
        return;
    }

    //\uc815\uc9c0\ud68c\uc6d0
    if(0 == info.i("status")) {
        m.jsAlert("\ub85c\uadf8\uc778 \ud560 \uc218 \uc5c6\uc2b5\ub2c8\ub2e4.\\n\uad00\ub9ac\uc790\uc5d0\uac8c \ubb38\uc758\ud574 \uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.\\nadmin@enter.com");
        return;
    }

    //\uc81c\ud55c-\ube44\ubc00\ubc88\ud638\uc624\ub958
    if(!passwd.equals(info.s("passwd"))) {
        int totalFailCnt = info.i("fail_cnt") + 1;
        user.item("fail_cnt", totalFailCnt);
        if(5 == totalFailCnt) {
            user.item("status", 0);
        }
        user.update("id = " + info.i("id"));
        m.jsAlert("\uc544\uc774\ub514/\ube44\ubc00\ubc88\ud638\ub97c \ud655\uc778\ud558\uc138\uc694.\\n\ub85c\uadf8\uc778 5\ud68c \uc624\ub958 \uc2dc \ub85c\uadf8\uc778\uc774 \uc81c\ud55c\ub429\ub2c8\ub2e4.");
        return;
    }

    //\uac1c\uc778\uc815\ubcf4\ud65c\uc6a9\uc5d0 \ub3d9\uc758\ud558\uc9c0 \uc54a\uc740 \uacbd\uc6b0
    if(!"Y".equals(info.s("agreement_yn")) && !"S".equals(info.s("user_type"))) {
        String ek = m.encrypt("PRIVACY_" + info.s("id") + "_AGREE_" + sysToday);
        m.jsAlert("\ub3d9\uc758\ud558\uc9c0 \uc54a\uc740 \uc57d\uad00\uc774 \uc874\uc7ac\ud569\ub2c8\ub2e4.\\n\uc57d\uad00\uc5d0 \ub3d9\uc758\ud574\uc57c \uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4\\n\ud655\uc778 \ubc84\ud2bc\uc744 \ub204\ub974\uba74 \uc774\uc6a9\uc57d\uad00 \ud398\uc774\uc9c0\ub85c \uc774\ub3d9\ud569\ub2c8\ub2e4.");
        m.jsReplace("privacy_agree.jsp?id=" + info.s("id") + "&ek=" + ek , "parent");
        return;
    }

    //\ub85c\uadf8 \ub0a8\uae30\uae30
    userLogin.item("user_id", info.i("id"));
    userLogin.item("user_type", "F"); //F:\uc0ac\uc6a9\uc790\ub2e8 B:\uad00\ub9ac\uc790\ub2e8
    userLogin.item("login_type", "I"); //I:\ub85c\uadf8\uc778 O:\ub85c\uadf8\uc544\uc6c3
    userLogin.item("ip_addr", m.getRemoteAddr());
    userLogin.item("device", userLogin.getDeviceType(request.getHeader("user-agent")));
    userLogin.item("log_date", sysToday);
    userLogin.item("reg_date", sysNow);

    //\ub85c\uadf8
    if(!userLogin.insert()) { m.log("login", "\ub85c\uadf8\uc778 \uae30\ub85d \ub4f1\ub85d \uc624\ub958 - USER_ID = " + info.i("id") + ""); }

    auth.put("USERID", info.i("id"));
    auth.put("LOGINID", info.s("login_id"));
    auth.put("USERNAME", info.s("user_nm"));
    auth.put("EMAIL", info.s("email"));
    auth.put("TYPE", info.s("user_type"));
    auth.save();

    if(-1 == user.execute("UPDATE " + user.table + " SET fail_cnt = 0, conn_date = " + sysNow + " WHERE id = " + info.i("id"))) {
        m.log("login", "\ub85c\uadf8\uc778 \uc131\uacf5 \uc5c5\ub370\uc774\ud2b8 \uc624\ub958 - USER_ID = " + info.i("id") + "");
    }

    //\uc774\ub3d9
    m.jsReplace("../main/index.jsp", "parent");
    return;
}

//\ucd9c\ub825
p.setLayout(ch);
p.setBody("member.login");
p.setVar("form_script", f.getScript());
p.display();


      out.write(_jsp_string0, 0, _jsp_string0.length);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_application.getJspApplicationContext().freePageContext(pageContext);
    }
  }

  private java.util.ArrayList _caucho_depends = new java.util.ArrayList();

  public java.util.ArrayList _caucho_getDependList()
  {
    return _caucho_depends;
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;
    if (com.caucho.server.util.CauchoSystem.getVersionId() != 6749855747778707107L)
      return true;
    for (int i = _caucho_depends.size() - 1; i >= 0; i--) {
      com.caucho.vfs.Dependency depend;
      depend = (com.caucho.vfs.Dependency) _caucho_depends.get(i);
      if (depend.isModified())
        return true;
    }
    return false;
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    com.caucho.server.webapp.WebApp webApp
      = (com.caucho.server.webapp.WebApp) config.getServletContext();
    super.init(config);
    com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
    com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.PageContextImpl(webApp, this);
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
  }

  public void init(com.caucho.vfs.Path appDir)
    throws javax.servlet.ServletException
  {
    com.caucho.vfs.Path resinHome = com.caucho.server.util.CauchoSystem.getResinHome();
    com.caucho.vfs.MergePath mergePath = new com.caucho.vfs.MergePath();
    mergePath.addMergePath(appDir);
    mergePath.addMergePath(resinHome);
    com.caucho.loader.DynamicClassLoader loader;
    loader = (com.caucho.loader.DynamicClassLoader) getClass().getClassLoader();
    String resourcePath = loader.getResourcePathSpecificFirst();
    mergePath.addClassPath(resourcePath);
    com.caucho.vfs.Depend depend;
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/login.jsp"), -1150877219106863769L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("member/init.jsp"), -2398591941373377023L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), 4962166057057453810L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\n\n".toCharArray();
  }
}
