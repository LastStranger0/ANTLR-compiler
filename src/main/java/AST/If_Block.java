package AST;

import java.util.List;

public class If_Block extends Node {
    public Condition condition;
    public List<Operation> operations;
}
