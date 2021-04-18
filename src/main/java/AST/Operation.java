package AST;

public class Operation extends Node {
    Get_Operation get_operation;
    Initialise_Var initialise_var;
    Assignment assignment;
    Add_Assignment add_assignment;
    If_Statement if_statement;
    For_Statement for_statement;
    While_Statement while_statement;
    Type_Cast type_cast;
}
