<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%



UserDao user= new UserDao();

f.addElement("login_id", null, "hname:'회원아이디', required:'Y'");
f.addElement("email", null, "hname:'이메일'");

if (m.isPost() && f.validate()) {
    //회원아이디와 이메일이 맞는지 확인



    //이메일에 임시 비번 전송



}

p.setLayout("blank");
p.setBody("main.find_password");
p.setVar("form_script", f.getScript());
p.display();

%>