/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._complete;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _complete_0user__jsp extends com.caucho.jsp.JavaPage
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
      
//
//\ubcc0\uc218
String docRoot = Config.getDocRoot();
String tplRoot = docRoot + "/sysop/html";

//\uac1d\uccb4
Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
f.setRequest(request);

Page p = new Page(tplRoot);
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);

// \uae30\ubcf8 \uad00\ub9ac\uc790 \uc815\ubcf4
int userId = 0;
String loginId = "";
String userName = "";
String userType = "";

// \ub85c\uadf8\uc778 \uc5ec\ubd80 \uccb4\ud06c
Auth auth = new Auth(request, response);
auth.loginURL = "/sysop/main/login.jsp";
auth.keyName = "ENTER2022BO";
if(auth.isValid()) {
    userId = m.parseInt(auth.getString("ID"));
    loginId = auth.getString("LOGINID");
    userName = auth.getString("NAME");
    userType = auth.getString("TYPE");

} else {
    if(request.getRequestURI().indexOf("/main/login.jsp") == -1) { //\ub85c\uadf8\uc778 \ud398\uc774\uc9c0\uba74 \uc81c\uc678
        m.jsReplace(auth.loginURL, "top");
        return;
    }
}

p.setVar("SYS_TITLE", "[\uad00\ub9ac\uc790] enter \uc624\ud508 \ub354 \uc774\ub7ec\ub2dd \uc0ac\uc774\ud2b8");


      

String ch = "sysop";

      

//\uae30\ubcf8\ud0a4
int cid = m.ri("cid");
if(cid == 0) { m.jsError("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

//\uac1d\uccb4
UserDao user = new UserDao();
CourseDao course = new CourseDao();
CourseUserDao courseUser = new CourseUserDao();

//\uc815\ubcf4
DataSet cinfo = course.find("id = " + cid + " AND status != -1");
if(!cinfo.next()) { m.jsError("\ud574\ub2f9 \uc815\ubcf4\uac00 \uc5c6\uc2b5\ub2c8\ub2e4."); return; }

//\ucc98\ub9ac - \uc120\ud0dd \uc218\ub8cc \ud310\uc815
if("complete_y".equals(m.rs("mode"))) {
    String idx = m.rs("idx");
    if("".equals(idx)) { m.jsAlert("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

    DataSet culist = courseUser.find("status = 1 AND id IN ( " + idx + " )");
    while(culist.next()) courseUser.completeUser(culist.i("id"));
    
    m.jsAlert(m.nf(culist.size()) + "\uba85\uc774 \uc218\ub8cc\ucc98\ub9ac \ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("complete_user.jsp?cid=" + cid, "parent");
    return;

//\uc804\uccb4 \uc218\ub8cc \ud310\uc815
} else if("all_complete_y".equals(m.rs("mode"))) {
    DataSet culist = courseUser.find("status = 1 AND course_id = " + cid );
    while(culist.next()) courseUser.completeUser(culist.i("id"));

    m.jsAlert(m.nf(culist.size()) + "\uba85\uc774 \uc218\ub8cc\ucc98\ub9ac \ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("complete_user.jsp?cid=" + cid, "parent");
    return;

// \uc120\ud0dd \ud310\uc815 \ucde8\uc18c
} else if("complete_n".equals(m.rs("mode"))) {

    String idx = m.rs("idx");
    if ("".equals(idx)) { m.jsAlert("\uae30\ubcf8\ud0a4\ub294 \ubc18\ub4dc\uc2dc \uc9c0\uc815\ud574\uc57c \ud569\ub2c8\ub2e4."); return; }

    int cuCnt = courseUser.findCount("status = 1 AND id IN ( " + idx + ")");
    courseUser.item("complete_status", "I");
    courseUser.item("complete_date", "");
    courseUser.item("complete_no", "");
    courseUser.item("reg_date", m.time("yyyyMMddHHmmss"));
    if (!courseUser.update("status = 1 AND id IN (" + idx + ")")) { m.jsAlert("\uc218\ub8cc\ub97c \ucde8\uc18c\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }

    //\uc774\ub3d9
    m.jsAlert(m.nf(cuCnt) + "\uba85\uc774 \uc218\ub8cc\ucde8\uc18c \ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("complete_user.jsp?cid=" + cid, "parent");
    return;

// \uc804\uccb4 \ud310\uc815 \ucde8\uc18c
} else if("all_complete_n".equals(m.rs("mode"))) {

    int cuCnt = courseUser.findCount("status = 1 AND course_id = " + cid );
    courseUser.item("complete_status", "I");
    courseUser.item("complete_date", "");
    courseUser.item("complete_no", "");
    courseUser.item("reg_date", m.time("yyyyMMddHHmmss"));
    if (!courseUser.update("status = 1 AND course_id = " + cid)) { m.jsAlert("\uc218\ub8cc\ub97c \ucde8\uc18c\ud558\ub294 \uc911 \uc624\ub958\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4."); return; }

    //\uc774\ub3d9
    m.jsAlert(m.nf(cuCnt) + "\uba85\uc774 \uc218\ub8cc\ucde8\uc18c \ub418\uc5c8\uc2b5\ub2c8\ub2e4.");
    m.jsReplace("complete_user.jsp?cid=" + cid, "parent");
    return;
}

//\ud3fc\uccb4\ud06c
f.addElement("s_complete", null, null);
f.addElement("s_field", null, null);
f.addElement("s_listnum", null, null);
f.addElement("s_keyword", null, null);

//\ubaa9\ub85d
ListManager lm = new ListManager();
lm.setRequest(request);
lm.setListNum("excel".equals(m.rs("mode")) ? 10000 : f.getInt("s_listnum", 20));
lm.setTable(
    courseUser.table + " a "
    + " INNER JOIN " + user.table + " u ON a.user_id = u.id  AND u.status != -1 "
);
lm.setFields("a.*, u.user_nm, u.login_id");
lm.addWhere("a.status != -1");
lm.addWhere("a.course_id = " + cid + "");
lm.addSearch("a.complete_status", f.get("s_complete"));
if(!"".equals(f.get("s_field"))) lm.addSearch(f.get("s_field"), f.get("s_keyword"), "LIKE");
else if("".equals(f.get("s_field")) && !"".equals(f.get("s_keyword"))) {
    lm.addSearch("u.login_id, u.user_nm", f.get("s_keyword"), "LIKE");
}
lm.setOrderBy(!"".equals(m.rs("ord")) ? m.rs("ord") : "a.id DESC");

//\ud3ec\ub9f7\ud305
DataSet list = lm.getDataSet();
while(list.next()) {
	list.put("progress_ratio_conv", m.nf(list.d("progress_ratio"), 0));
	list.put("start_date_conv", m.time("yyyy.MM.dd", list.s("start_date")));
    list.put("end_date_conv", m.time("yyyy.MM.dd", list.s("end_date")));
    list.put("complete_date_conv", m.time("yyyy.MM.dd", list.s("complete_date")));
    list.put("complete_conv", m.getItem(list.s("complete_status"), courseUser.completeStatusList));     
}

//\uc5d1\uc140
if("excel".equals(m.rs("mode2"))) {
    String[] cols = {"__ord=>No","user_nm=>\uc774\ub984", "login_id=>\uc544\uc774\ub514", "start_date_conv=>\uc218\uac15\uc2dc\uc791\uc77c", "end_date_conv=>\uc218\uac15\ub9c8\uac10\uc77c", "progress_ratio_conv=>\uc9c4\ub3c4(100%\uae30\uc900)","complete_conv=>\uc218\ub8cc\uc5ec\ubd80", "complete_date_conv=>\uc218\ub8cc\ud310\uc815\uc77c", "complete_no=>\uc218\ub8cc\ubc88\ud638"};

    ExcelWriter ex = new ExcelWriter(response, "\uacfc\uc815\uc218\ub8cc\uad00\ub9ac (" + m.time("yyyy-MM-dd") + ").xls");
    ex.setData(list, cols);
    ex.write();
    return;
}

//\ucd9c\ub825
p.setLayout(ch);
p.setBody("complete.complete_user");
p.setVar("p_title","\uc218\ub8cc\uad00\ub9ac");
p.setVar("query", m.qs());
p.setVar("form_script", f.getScript());

p.setLoop("list", list);
p.setVar("list_total", lm.getTotalString());
p.setVar("pagebar", lm.getPaging());

p.setVar("tab_complete", "current");
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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/complete/complete_user.jsp"), 5497120636882113978L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/complete/init.jsp"), 5013757657464329565L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -2253010914700824916L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }
}
