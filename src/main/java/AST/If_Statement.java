package AST;

import java.util.List;

public class If_Statement extends Node{
    public If_Block if_block;
    public List<Else_If_Block> else_if_blocks;
    public Else_Block else_block;
}
