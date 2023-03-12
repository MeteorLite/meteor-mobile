package meteor;

public class Paramaters {
    static String s6 = "0";
    static String s18 = "";
    static String s13 = ".runescape.com";
    static String s7 = "0";
    static String s19 = "196515767263-1oo20deqm6edn7ujlihl6rpadk9drhva.apps.googleusercontent.com";
    static String s5 = "0";
    static String s9 = "p1cxIGuofoJXb0MtXvoAyiLXO4mOja1-Fqzl-8UybUDnua86o9mSPw";
    static String s28 = "https://account.jagex.com/";
    static String s16 = "true";
    static String s3 = "true";
    static String s8 = "true";
    static String s25 = "212";

    //Visually sets world number on login screen but is functionally useless, set in codebase below
    static String s12 = "441";
    static String s10 = "5";
    static String s15 = "0";
    static String s4 = "1";
    static String s17 = "http://www.runescape.com/g=oldscape/slr.ws?order=LPWM";
    static String s11 = "https://auth.jagex.com/";
    static String s20 = "https://social.auth.jagex.com/";
    static String s14 = "0";
    static String s21 = "0";
    static String s2 = "https://payments.jagex.com/";

    public static String getParameter(String paramater) {
        if (paramater.equals("2")) {
            return s2;
        }
        if (paramater.equals("3")) {
            return s3;
        }
        if (paramater.equals("4")) {
            return s4;
        }
        if (paramater.equals("5")) {
            return s5;
        }
        if (paramater.equals("6")) {
            return s6;
        }
        if (paramater.equals("7")) {
            return s7;
        }
        if (paramater.equals("8")) {
            return s8;
        }
        if (paramater.equals("9")) {
            return s9;
        }
        if (paramater.equals("10")) {
            return s10;
        }
        if (paramater.equals("11")) {
            return s11;
        }
        if (paramater.equals("12")) {
            return s12;
        }
        if (paramater.equals("13")) {
            return s13;
        }
        if (paramater.equals("14")) {
            return s14;
        }
        if (paramater.equals("15")) {
            return s15;
        }
        if (paramater.equals("16")) {
            return s16;
        }
        if (paramater.equals("17")) {
            return s17;
        }
        if (paramater.equals("18")) {
            return s18;
        }
        if (paramater.equals("19")) {
            return s19;
        }
        if (paramater.equals("20")) {
            return s20;
        }
        if (paramater.equals("21")) {
            return s21;
        }
        if (paramater.equals("25")) {
            return s25;
        }
        if (paramater.equals("28")) {
            return s28;
        }
        return "";
    }

    public static String getCodeBase() {
        return "oldschool141.runescape.com";
    }
}
