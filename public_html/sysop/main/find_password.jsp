<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%

UserDao user= new UserDao();

f.addElement("login_id", null, "hname:'회원아이디', required:'Y'");
f.addElement("email", null, "hname:'이메일', required:'Y'");

if (m.isPost() && f.validate()) {
    m.jsAlert(m.rs("send_auth_no"));
    if ("send_auth_no".equals(m.rs("send_auth_no"))){
        //로그인아이디와 이메일이 맞는지 확인
        DataSet info = user.find("login_id = ? AND email = ? ", new Object[] { f.get("login_id"), f.get("email") });
        if(!info.next()){
            m.jsAlert("정확하지 않은 회원정보입니다. ");
            return;
        }

        //이메일에 임시 비번 전송
    //    int authNo = m.getRandInt(123456, 864198);
        int authNo = (int)(Math.random() * 899999) + 100000; //100000 ~ 999999난수

        p.setVar("auth_no", authNo);

        String mbody = "인증번호가 발송됐습니다.";
        mbody = p.fetchString("인증번호는" + authNo + "입니다.");

        p.setLayout("blank");
        p.setVar("user", info);
        mbody = mbody.replace("<!-- ", "<!--").replace(" -->", "-->").replace("src=\"/data/", "src=\"" + "kaya.malgn.co.kr//data/");
        p.setVar("MBODY", mbody);

        //발송자
        String sender = "Princesa_Kaya";

        //발송
        m.mailFrom = sender;
        m.mail(info.s("email"), mbody, p.fetchAll());

        m.jsAlert("인증번호가 발송됐습니다.");

        //세션
        m.setSession("LOGIN_ID", f.get("login_id"));
        m.setSession("EMAIL", f.get("email"));
        m.setSession("AUTH_NO", authNo);

    } else if ("confirm_auth_no".equals(m.rs("confirm_auth_no"))) {
        m.jsAlert(m.rs("confirm_auth_no"));
        String authNo = f.get("auth_no"); //입력받은 인증번호

        //정보
        DataSet info = user.find("login_id = ? AND email = ? ", new Object[] { f.get("login_id"), f.get("email") });
        if (!info.next()){
            m.jsAlert("정확하지 않은 회원정보입니다.");
            return;
        }

        if (!authNo.equals(m.getSessin("AUTH_NO"))){
            m.jsAlert("인증정보가 일치하지 않습니다. 인증정보를 다시 발급받으세요.");
            return;
        }

        //새 비밀번호 발급
        String newPassword = m.encrypt(m.getUniqId(), "SHA-256");
        user.item("password", newPassword);
        user.item("fail_count", 0);
        if(!user.update("login_id = " + info.s("login_id"))) {
            m.jsAlert("수정하는 중 오류가 발생했습니다."); return;
        }

        //발송
        p.setVar("new_password", newPassword);
//        mailTemplate.sendMail(siteinfo, uinfo, "findpw_newpw", "새로운 비밀번호가 발급되었습니다.", p);

        String mbody = p.fetchString("새로운 비밀번호는" + newPassword + "입니다.");

        p.setLayout("blank");
        p.setVar("user", info);
        mbody = mbody.replace("<!-- ", "<!--").replace(" -->", "-->").replace("src=\"/data/", "src=\"" + "kaya.malgn.co.kr//data/");
        p.setVar("MBODY", mbody);

        //발송자
        String sender = "Princesa_Kaya";

        //발송
        m.mailFrom = sender;
        m.mail(info.s("email"), mbody, p.fetchAll());

        m.jsAlert("인증번호가 발송됐습니다.");


        //세션
        m.setSession("LOGIN_ID", "");
        m.setSession("EMAIL", "");
        m.setSession("AUTH_NO", "");

        m.jsAlert("새로운 비밀번호를 이메일로 발급하여 드렸습니다. 이메일을 확인하세요.");
        m.jsReplace("/member/login.jsp", "parent");
        return;


    }

    //다시 로그인 페이지


}

p.setLayout("blank");
p.setBody("main.find_password");
p.setVar("form_script", f.getScript());
p.display();

%>