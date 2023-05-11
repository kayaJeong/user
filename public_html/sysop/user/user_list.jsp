<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%



//객체
UserDao user = new UserDao();

//폼체크
f.addElement("s_status", null, null);//회원 상태


//목록
ListManager lm = new ListManager();
//lm.d(out);
lm.setRequest(request);
lm.setListNum(100);
lm.setTable(
        user.table + " a "
);
lm.setFields(
        " a.* "
);
lm.addWhere("a.status >= -1");
lm.setOrderBy("a.reg_date DESC"); //최신등록순 내림차순

//포맷팅
DataSet list = lm.getDataSet();
while(list.next()){
    list.put("phone_conv", !"".equals(list.s("phone"))? SimpleAES.decrypt(list.s("phone")) : "-");
    list.put("email_conv",list.s("email"));


}


p.setLayout("sysop");
p.setBody("user.user_list");
p.setVar("form_script", f.getScript());


p.setLoop("list", list);

p.display();

%>