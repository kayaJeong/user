/*
 * JSP generated by Resin-4.0.66 (built Mon, 29 Nov 2021 04:22:10 PST)
 */

package _jsp._course;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _course_0view__jsp extends com.caucho.jsp.JavaPage
{
  private static final java.util.HashMap<String,java.lang.reflect.Method> _jsp_functionMap = new java.util.HashMap<String,java.lang.reflect.Method>();
  private boolean _caucho_isDead;
  private boolean _caucho_isNotModified;
  private com.caucho.jsp.PageManager _jsp_pageManager;
  
  public void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response)
    throws java.io.IOException, javax.servlet.ServletException
  {
    javax.servlet.http.HttpSession session = request.getSession(true);
    com.caucho.server.webapp.WebApp _jsp_application = _caucho_getApplication();
    com.caucho.jsp.PageContextImpl pageContext = _jsp_pageManager.allocatePageContext(this, _jsp_application, request, response, null, session, 8192, true, false);

    TagState _jsp_state = null;

    try {
      _jspService(request, response, pageContext, _jsp_application, session, _jsp_state);
    } catch (java.lang.Throwable _jsp_e) {
      pageContext.handlePageException(_jsp_e);
    } finally {
      _jsp_pageManager.freePageContext(pageContext);
    }
  }
  
  private void
  _jspService(javax.servlet.http.HttpServletRequest request,
              javax.servlet.http.HttpServletResponse response,
              com.caucho.jsp.PageContextImpl pageContext,
              javax.servlet.ServletContext application,
              javax.servlet.http.HttpSession session,
              TagState _jsp_state)
    throws Throwable
  {
    javax.servlet.jsp.JspWriter out = pageContext.getOut();
    final javax.el.ELContext _jsp_env = pageContext.getELContext();
    javax.servlet.ServletConfig config = getServletConfig();
    javax.servlet.Servlet page = this;
    javax.servlet.jsp.tagext.JspTag _jsp_parent_tag = null;
    com.caucho.jsp.PageContextImpl _jsp_parentContext = pageContext;
    response.setContentType("text/html; charset=utf-8");

    

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

    

String ch = "course";


    

//\uae30\ubcf8\ud0a4
int id = m.ri("id");
if (id == 0) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c\ud569\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
CourseDao course = new CourseDao(); //\uacfc\uc815
CourseUserDao courseUser = new CourseUserDao(); //\uc218\uac15\uc0dd
LessonDao lesson = new LessonDao(); //\uac15\uc758

//\ubcc0\uc218
int cuStatus = 0; //\uc218\uac15 \uc2e0\uccad \uc774\ub825
int applyStatus = 0; //\uc218\uac15 \uc5ec\ubd80
int applyAgainStatus = 0; //\uc7ac\uc218\uac15 \uc5ec\ubd80
int applyFutureStatus = 0;  //\ubbf8\ub798 \uc218\uac15\uc608\uc815 \uc5ec\ubd80


//\uacfc\uc815 \uc815\ubcf4
DataSet info = course.find(" id = " + id + " AND status = 1 ");

//\uac15\uc758
DataSet lessonList = lesson.find(" status = 1 AND course_id = " + id + " ORDER BY sort ASC ");

if(!info.next()) {
    m.jsError("\ud574\ub2f9 \uc815\ubcf4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.");
    return;
}

//\uacfc\uc815 \ud3ec\uba67\ud305
//\uba54\uc778\uc774\ubbf8\uc9c0
info.put("course_file_url", "/html/images/common/noimage_course.gif");
if(!"".equals(info.s("course_file"))) info.put("course_file_url", m.getUploadUrl(info.s("course_file")));

//\uc218\uac15 \ub0b4\uc5ed- 1.\uc218\uac15\uc5ec\ubd80 \ud655\uc778
cuStatus = courseUser.findCount( " user_id = " + userId + " AND course_id = " + info.s("id") + " AND status = 1 ");

if (cuStatus > 0) { //\uc218\uac15 \uc774\ub825\uc774 \uc788\uc74c

    //\uc218\uac15\uc0dd\uc815\ubcf4
    DataSet courseInsertInfo = courseUser.find( " user_id = " + userId + " AND course_id = " + info.s("id") , "*" , " end_date DESC ", 1);
    if (!courseInsertInfo.next()){};

    
    //2. \uc218\uac15\uc911 or \uc7ac\uc218\uac15
    //\uac12 < 0 : \ud604\uc7ac \uc218\uac15\uc911 -> \uc218\uac15\uc2e0\uccad x
    //\uac12 < 0 : \ubbf8\ub798 \uc218\uac15\uc608\uc815 -> \uc218\uac15\uc2e0\uccad x
    //\uac12 > 0 : \uacfc\uac70\uc5d0 \uc218\uac15 -> \uc7ac\uc218\uac15 \uac00\ub2a5

    applyStatus = m.diffDate("D", courseInsertInfo.s("end_date"), sysToday);//\ud604\uc7ac \uc218\uac15\uc911
    applyFutureStatus = m.diffDate("D", courseInsertInfo.s("start_date"), sysToday);//\ubbf8\ub798 \uc218\uac15\uc608\uc815
    applyAgainStatus = m.diffDate("D", courseInsertInfo.s("end_date"), sysToday); // \uc7ac\uc218\uac15 \uac00\ub2a5

}


//\ucd9c\ub825
p.setLayout(ch);
p.setBody("course.course_view");
p.setVar("form_script", f.getScript());

p.setVar(info);//\uacfc\uc815
p.setLoop("lessonList", lessonList);//\uac15\uc758
p.setVar("lesson_cnt", lessonList.size());//\ucc28\uc2dc

p.setVar("cu_status", cuStatus); //\uc218\uac15\uc2e0\uccad \uc774\ub825
p.setVar("apply_status", applyStatus); //\uccab\uc218\uac15 \uc5ec\ubd80
p.setVar("apply_again_status", applyAgainStatus); //\uc7ac\uc218\uac15 \uc5ec\ubd80
p.setVar("apply_future_status",applyFutureStatus);  //\ubbf8\ub798 \uc218\uac15 \uc5ec\ubd80

p.display();


  }

  private com.caucho.make.DependencyContainer _caucho_depends
    = new com.caucho.make.DependencyContainer();

  public java.util.ArrayList<com.caucho.vfs.Dependency> _caucho_getDependList()
  {
    return _caucho_depends.getDependencies();
  }

  public void _caucho_addDepend(com.caucho.vfs.PersistentDependency depend)
  {
    super._caucho_addDepend(depend);
    _caucho_depends.add(depend);
  }

  protected void _caucho_setNeverModified(boolean isNotModified)
  {
    _caucho_isNotModified = true;
  }

  public boolean _caucho_isModified()
  {
    if (_caucho_isDead)
      return true;

    if (_caucho_isNotModified)
      return false;

    if (com.caucho.server.util.CauchoSystem.getVersionId() != -243257770274170091L)
      return true;

    return _caucho_depends.isModified();
  }

  public long _caucho_lastModified()
  {
    return 0;
  }

  public void destroy()
  {
      _caucho_isDead = true;
      super.destroy();
    TagState tagState;
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("course/course_view.jsp"), -926704023636774331L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("course/init.jsp"), 1758225220959401630L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("init.jsp"), -2671751158375180785L, false);
    _caucho_depends.add(depend);
    loader.addDependency(depend);
  }

  final static class TagState {

    void release()
    {
    }
  }

  public java.util.HashMap<String,java.lang.reflect.Method> _caucho_getFunctionMap()
  {
    return _jsp_functionMap;
  }

  public void caucho_init(ServletConfig config)
  {
    try {
      com.caucho.server.webapp.WebApp webApp
        = (com.caucho.server.webapp.WebApp) config.getServletContext();
      init(config);
      if (com.caucho.jsp.JspManager.getCheckInterval() >= 0)
        _caucho_depends.setCheckInterval(com.caucho.jsp.JspManager.getCheckInterval());
      _jsp_pageManager = webApp.getJspApplicationContext().getPageManager();
      com.caucho.jsp.TaglibManager manager = webApp.getJspApplicationContext().getTaglibManager();
      com.caucho.jsp.PageContextImpl pageContext = new com.caucho.jsp.InitPageContextImpl(webApp, this);
    } catch (Exception e) {
      throw com.caucho.config.ConfigException.create(e);
    }
  }
}
