package AST;

public class String_Literal extends Node {
    public String str;

    public String getStrWithOne() {
        return "'" +str+ "'";
    }

    public String getStrWithTwo() {
        return "\""+str+"\"";
    }
}
