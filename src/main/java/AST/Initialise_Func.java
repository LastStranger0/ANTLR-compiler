package AST;

import java.util.List;

public class Initialise_Func extends Node {
    Type type;
    Name name;
    List<Type> typesOfVar;
    List<Name> namesOfVar;
    List<Operation> operations;
}
