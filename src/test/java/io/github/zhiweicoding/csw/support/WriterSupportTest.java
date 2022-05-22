package io.github.zhiweicoding.csw.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Created by zhiwei on 2022/3/29.
 */
class WriterSupportTest {
    static String text = "1 $*. 2 $*. 3";
    static String mark = "\\$\\*\\.";

//    public static void main(String[] args) {
//        String[] ss = text.split(mark);
//        StringBuilder sb = new StringBuilder();
//        for (String s : ss) {
//            sb.append(s).append(System.lineSeparator());
//        }
//        String str = sb.toString();
////        System.out.println(str.substring(0, str.length() - 1));
//        System.out.println(str);
//    }

//    public static void main(String[] args) {
//        String text="&1";
//        System.out.println(text.split("&")[1]);
//        System.out.println(text.split("&")[0]);
//    }

    //    public static void main(String[] args) {
//        String text="*1";
//        System.out.println(text.startsWith("\\*"));
//        System.out.println(text.startsWith("*"));
//    }
//    public static void main(String[] args) {
//        String text = "*1*测试阿达地方阿达地方" +
//                "*231ajdf*adfakdf" +
//                "**123**";
//        String reg = "\\*[\\S]+\\*";
//        String replace = text.replaceAll(reg, "2");
//        System.out.println(replace);
//    }

        public static void main(String[] args) {
        String text = "*1*测试阿达地方阿达地方";
        String reg = "\\*[\\S]+\\*";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(text);
        while (m.find()) {
            String s = m.group();
            System.out.println(s);
        }
    }
//    public static void main(String[] args) {
//        String text = "*1*测试阿达地方阿达地方";
//        String reg = "\\*";
//        System.out.println(text.replaceAll(reg,""));
//    }
}