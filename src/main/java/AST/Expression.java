package AST;

import java.util.ArrayList;

public class Expression extends Node{
    Expression expression1;
    Expression expression2;
    Action_Operator action_operator;
    Bool_Operator bool_operator;
    Get_Operation get_operation;
    Number_Literal number_literal;
    String_Literal string_literal;
    Name name;
}
