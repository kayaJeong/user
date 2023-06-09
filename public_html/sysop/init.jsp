<%@ page import="java.util.*,java.io.*,dao.*,malgnsoft.db.*,malgnsoft.util.*" %><%
//
//변수
String docRoot = Config.getDocRoot();
String tplRoot = docRoot + "/sysop/html";

//객체
Malgn m = new Malgn(request, response, out);

Form f = new Form("form1");
f.setRequest(request);

//기본정보
int userId = 0;
String loginId = "";
String userName = "";
String userKind = "";
String userSessionId = "";

String sysToday = m.time("yyyyMMdd");
String sysNow = m.time("yyyyMMddHHmmss");

SessionDao mSession = new SessionDao(request, response);

Page p = new Page(tplRoot);
p.setRequest(request);
p.setPageContext(pageContext);
p.setWriter(out);


// 로그인 여부 체크
Auth auth = new Auth(request, response);
auth.loginURL = "/sysop/main/login.jsp";
//auth.keyName = "ENTER2022BO";
if(auth.isValid()) {
    userId = m.parseInt(auth.getString("ID"));
    loginId = auth.getString("LOGINID");
    userName = auth.getString("NAME");
//    userType = auth.getString("TYPE");

} /*else {
    if(request.getRequestURI().indexOf("/sysop/main/login.jsp") == -1
            && request.getRequestURI().indexOf("/sysop/user/password_find.jsp") == -1 ) { //로그인 페이지면 제외
        m.jsReplace(auth.loginURL, "top");
        return;
    }
}*/



%>