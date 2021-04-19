package AST;

import java.util.List;

public class Initialise_Func extends Node {
    public Type type;
    public Name name;
    public List<Type> typesOfVar;
    public List<Name> namesOfVar;
    public List<Operation> operations;
}
