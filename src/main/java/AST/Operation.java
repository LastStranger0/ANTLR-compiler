package AST;

public class Operation extends Node {
    public Get_Operation get_operation;
    public Initialise_Var initialise_var;
    public Assignment assignment;
    public Add_Assignment add_assignment;
    public If_Statement if_statement;
    public For_Statement for_statement;
    public While_Statement while_statement;
    public Type_Cast type_cast;
}
