<%@ page contentType="text/html; charset=utf-8" %><%@ include file="../init.jsp" %><%

//객체
UserDao user= new UserDao();

//폼체크
f.addElement("login_id", null, "hname:'로그인아이디', required:'Y'");
f.addElement("password", null, "hname:'비밀번호', required:'Y'");

if (m.isPost() && f.validate()) {

    Calendar startCalendar = new GregorianCalendar();
    Date time = startCalendar.getTime();
    String nowTime = Integer.toString(time.getYear())+Integer.toString(time.getHours())+Integer.toString(time.getMinutes())+ Integer.toString(time.getSeconds());
    String limitTime = "0"; //5분 시간 제한d

    if(Integer.parseInt(nowTime) < Integer.parseInt(limitTime)){ //시간이 아직 안 지남
        m.jsAlert("비밀번호 5회 오입력했습니다. 5분 동안 로그인이 제한됩니다.");
        return;
    }

    String password = f.get("password");
    password = m.encrypt(password, "SHA-256");

    DataSet info = user.find("login_id = ? AND user_kind = 'A' AND status = 1", new Object[] { f.get("login_id") });

    if(!info.next()) {
        m.jsAlert("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    //비밀번호 5회 입력인지 확인
    if(5 <= info.i("fail_count")){
        limitTime = Integer.toString(time.getYear())+Integer.toString(time.getHours())+Integer.toString(time.getMinutes() + 5)+ Integer.toString(time.getSeconds());
        m.jsAlert("비밀번호 5회 오입력했습니다. 5분 동안 로그인이 제한됩니다.");
        return;
    }

    //아이디 확인
    if(!f.get("login_id").equals(info.s("login_id"))) {
        m.jsAlert("아이디 또는 비밀번호가 일치하지 않습니다.2");
        return;
    }

    //비밀번호 확인
    if (!password.equals(info.s("password"))){
        m.jsAlert("아이디 또는 비밀번호가 일치하지 않습니다.3");
        user.item("fail_count", info.i("fail_count") + 1);
        user.update("id = " + info.i("id"));
        return;
    }

    user.item("fail_count", 0);
    if(!user.update("id = " + info.i("id"))){
        m.jsAlert("로그인 중 오류가 발생했습니다.");
    };

    //인증
    auth.put("ID", info.i("id"));
    auth.put("LOGINID", info.s("login_id"));
    auth.put("NAME", info.s("user_nm"));
    auth.put("TYPE", info.s("user_type"));
    auth.save();

    //이동
    m.jsAlert("로그인 완료");
//    m.jsReplace("index.jsp", "parent");
    return;
}

p.setLayout("blank");
p.setBody("main.login");
p.setVar("form_script", f.getScript());

p.display();

%>