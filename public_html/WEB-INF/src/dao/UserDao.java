package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.*;
import java.util.regex.*;

public class UserDao extends DataObject {

    public String[] statusList = {"1=>정상", "0=>중지", "2=>대기"};
    public String[] kinds = {"U=>회원", "A=>운영자"};

    public UserDao() {
        this.table = "TB_USER";
    }
}

