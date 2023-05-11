<%@ page contentType="text/html; charset=utf-8" %><%@ include file="init.jsp" %><%


//객체
UserDao user = new UserDao();

//폼체크
f.addElement("login_id", null, "hname:'회원아이디', required:'Y'");
//f.addElement("user_nm", null, "hname:'회원명', required:'Y'");
f.addElement("password", null, "hname:'비밀번호', required:'Y'");
f.addElement("password2", null, "hname:'비밀번호', required:'Y'");
f.addElement("email", null, "hname:'이메일'");
f.addElement("phone", null, "hname:'휴대전화번호'");


f.addElement("zipcode", null, "hname:'우편번호'");
f.addElement("address", null, "hname:'주소'");
f.addElement("address_detail", null, "hname:'상세주소'");

f.addElement("birthday", null, "hname:'생년월일'");
//f.addElement("status", 1, "hname:'상태', required:'Y'");

//제한

//등록
if (m.isPost() && f.validate()) {
    //로그인 아이디 중복체크
    String login_id = f.get("login_id");
    int checkLoginId = user.findCount("login_id = '" + f.get("login_id") + "'");
//    m.jsAlert(checkLoginId+"");
    if (0 < checkLoginId) {
        m.jsAlert("이미 사용 중인 아이디입니다.");
        return;
    }
    //비밀번호 8자 이상 영문,숫자,대문자,특수기호 포함
    String pwPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    if (!f.get("password").matches(pwPattern)){
        m.jsAlert("비밀번호는 8자 이상 영문, 숫자, 대문자, 특수기호를 포함해야합니다.");
        return;
    }
    //이메일 중복체크
    String email = f.get("email");
    int checkEmail = user.findCount("email= '" + email + "'");
    if (0 < checkEmail) {
        m.jsAlert("이미 사용 중인 이메일입니다.");
//        return;
    }
    //비밀번호 암호화 SHA256
    String password = f.get("password");
    password = m.encrypt(password, "SHA-256");

    //휴대전화번호 형식체크
    String phone = f.get("phone");
    String phonePattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    if (!f.get("phone").matches(phonePattern)) {
        m.jsAlert("올바른 휴대전화번호가 아닙니다.");
    }

    //휴대전화번호 암호화 AES128
    String [] phoneArr = phone.split("-");
    phone = m.join("", phoneArr);
    user.item("phone", SimpleAES.encrypt(phone));

    //회원이미지

    //사진파일 첨부 여러개 - pdf, 이미지

    //관심분야


    user.item("login_id", login_id);
    user.item("password", password);
    user.item("email", email);

//    user.item();
    user.item("reg_date", sysNow);
    user.item("status", f.getInt("status"));

    int newId = user.insert(true);
    if(newId == 0) { m.jsAlert("등록하는 중 오류가 발생했습니다."); return; }

    m.jsAlert("등록했습니다.");


}

p.setLayout("sysop");
p.setBody("user.user_insert");
p.setVar("form_script", f.getScript());



p.display();


%>