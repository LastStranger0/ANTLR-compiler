package AST;

public class String_Literal extends Node {
    String strWithOne;
    String strWithTwo;

    public String getStrWithOne() {
        return "'" +strWithOne+ "'";
    }

    public String getStrWithTwo() {
        return "\""+strWithTwo+"\"";
    }
}
