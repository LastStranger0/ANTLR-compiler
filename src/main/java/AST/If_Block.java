package AST;

import java.util.List;

public class If_Block extends Node {
    Condition condition;
    List<Operation> operations;
}
