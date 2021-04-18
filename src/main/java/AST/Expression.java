package AST;

import java.util.ArrayList;

public class Expression extends Node{
    public Expression expression1;
    public Expression expression2;
    public Action_Operator action_operator;
    public Bool_Operator bool_operator;
    public Get_Operation get_operation;
    public Number_Literal number_literal;
    public String_Literal string_literal;
    public Name name;
}
