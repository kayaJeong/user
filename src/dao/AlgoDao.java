package dao;

import malgnsoft.db.*;
import malgnsoft.util.*;
import java.util.*;

public class AlgoDao extends DataObject {
    public AlgoDao() {
    }

    public static int countChar(String var0, String var1) {
        return var0.length() - var0.replace(String.valueOf(var1), "").length();
    }
}