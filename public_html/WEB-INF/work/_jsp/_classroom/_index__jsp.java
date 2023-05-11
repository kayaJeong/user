/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._classroom;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _index__jsp extends com.caucho.jsp.JavaPage
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

      

//\ub85c\uadf8\uc778
if(0 == userId) { auth.loginForm(); return; }

//\uae30\ubcf8\ud0a4
int cuid = m.ri("cuid");
if(cuid == 0) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
UserDao user = new UserDao();
CourseDao course = new CourseDao();
CourseUserDao courseUser = new CourseUserDao();

//\uc815\ubcf4- \uc218\uac15\uc0dd
DataSet cuinfo = courseUser.query(
    "SELECT a.*, c.course_nm, c.course_file, u.id user_id, u.user_nm "
    + " FROM " + courseUser.table + " a "
    + " INNER JOIN " + course.table + " c ON a.course_id = c.id "
    + " INNER JOIN " + user.table + " u ON a.user_id = u.id"
    + " WHERE a.id = " + cuid + " AND a.user_id = '" + userId + "' AND a.status = 1"
);
if(!cuinfo.next()) { m.jsError("\ud574\ub2f9 \uc218\uac15 \uc815\ubcf4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\ud3ec\uba67\ud305
cuinfo.put("start_date_conv", m.time("yyyy.MM.dd", cuinfo.s("start_date")));
cuinfo.put("end_date_conv", m.time("yyyy.MM.dd", cuinfo.s("end_date")));
cuinfo.put("progress_ratio", m.nf(cuinfo.d("progress_ratio"), 1));

//\ubcc0\uc218
String courseId = cuinfo.s("course_id");

//\ucc44\ub110
String ch = "classroom";

      

//\uac1d\uccb4
LessonDao lesson = new LessonDao(); //\uac15\uc758
CourseProgressDao courseProgress = new CourseProgressDao(); //\uc9c4\ub3c4

//\uc815\ubcf4 - \uacfc\uc815 + \uac15\uc758 + \uc9c4\ub3c4
DataSet list = lesson.query(
    " SELECT a.id, a.sort, a.lesson_nm, a.total_time, c.max_time, c.study_time, c.complete_yn "
    + " FROM " + lesson.table + " a "
    + " INNER JOIN " + course.table + " b ON a.course_id = b.id AND b.status = 1 "
    + " LEFT JOIN " + courseProgress.table + " c ON a.id = c.lesson_id " +  " AND c.course_user_id = " + cuid + ""
    + " WHERE a.course_id = " + courseId +" AND a.status = 1 "
    + " ORDER BY a.sort ASC "
);

//\ud3ec\uba67\ud305
while(list.next()) {
    list.put("total_time_conv", list.s("total_time") + "\ubd84");
    list.put("study_time_conv", !"".equals(list.s("study_time")) || list.s("study_time") != null ? (list.i("study_time") / 60) + "\ubd84" : "0\ubd84");
}

//\ucd9c\ub825
p.setLayout(ch);
p.setBody("classroom.index");
p.setVar("cuinfo", cuinfo); // \ud559\uc2b5\uae30\uac04, \uc9c4\ub3c4\uc728, \uacfc\uc815\uc774\ub984
p.setLoop("list", list); //\ucc28\uc2dc, \ud559\uc2b5\uc5ec\ubd80, \uac15\uc758\uba85, \ud559\uc2b5\uc2dc\uac04(\uc9c4\ub3c4-\ub204\uc801\ud559\uc2b5\uc2dc\uac04/\uac15\uc758-\ud559\uc2b5\uc2dc\uac04)
p.display();


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
    depend = new com.caucho.vfs.Depend(appDir.lookup("classroom/index.jsp"), 8724816337832706499L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("classroom/init.jsp"), -6638790553243785233L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), -2671751158375180785L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}