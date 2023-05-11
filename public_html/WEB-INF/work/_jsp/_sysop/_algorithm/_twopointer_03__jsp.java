/*
 * JSP generated by Resin-3.1.15 (built Mon, 13 Oct 2014 06:45:33 PDT)
 */

package _jsp._sysop._algorithm;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.*;
import java.io.*;
import dao.*;
import malgnsoft.db.*;
import malgnsoft.util.*;

public class _twopointer_03__jsp extends com.caucho.jsp.JavaPage
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
      out.write(_jsp_string0, 0, _jsp_string0.length);
      
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

      

    String output ="";
    String output2 ="";

    if(m.isPost()){
        //1. \ubb38\uc790 \uc785\ub825\ubc1b\uae30
        String num1 = f.get("array1"); //\uc22b\uc790
        String num2 = f.get("array2"); //\uba87\uac1c\uc529 \ub354\ud560 \uac83\uc778\uc9c0\uc758 \uc22b\uc790
        m.jsAlert(num1);

        //\uc785\ub825\ubc1b\uc740 \ubb38\uc790\uc5f4\uc744 \uacf5\ubc31\uc744 \uae30\uc900\uc73c\ub85c \ub098\ub220\uc11c \uc2a4\ud2b8\ub9c1 \ubc30\uc5f4\uc5d0 \uc800\uc7a5
        String arr1 [] = num1.split(" ") ;
        int chNum2 = Integer.parseInt(num2);

        //\uc2a4\ud2b8\ub9c1 \ubc30\uc5f4\uc744 -> \uc815\uc218\ud615 \ubc30\uc5f4\ub85c \ubcc0\ud658
        int[] arr1int = new int[arr1.length];
        for(int i = 0; i< arr1.length; i++){
            arr1int[i] = Integer.parseInt(arr1[i]);
        }


        //3. \uba87\uc77c\uac04\uc758 \ucd5c\ub300 \ub9e4\ucd9c\uc561\uc744 \uad6c\ud574\uc57c \ud568
        int answer = 0;
        int sum = 0;
        int k = chNum2;
        for (int i = 0 ; i < k; i++){
            sum += arr1int[i]; // arr[0]+arr[1]+arr[2]

        }
        output = Integer.toString(sum);


        for(int i = k ; i < arr1int.length; i++){
            sum += (arr1int[i] - arr1int[i-k]);
            answer = Math.max(answer, sum);
        } output2 = Integer.toString(answer);

    }

    p.setLayout(ch);
    p.setBody("algorithm.twopointer_3");
    p.setVar("output", output);
    p.setVar("output2", output2);

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
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/algorithm/twopointer_3.jsp"), 1881177440188660784L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/algorithm/init.jsp"), 8504853130663271725L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
    depend = new com.caucho.vfs.Depend(appDir.lookup("sysop/init.jsp"), -2253010914700824916L, false);
    com.caucho.jsp.JavaPage.addDepend(_caucho_depends, depend);
  }

  private final static char []_jsp_string0;
  static {
    _jsp_string0 = "\r\n\r\n\r\n".toCharArray();
  }
}
