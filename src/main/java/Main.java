import AST.Node;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
                ANTLRInputStream fstream = new ANTLRInputStream(new FileInputStream("resources/test.txt"));
                xmlLexer lexer = new xmlLexer((CharStream) fstream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                xmlParserV1 parser = new xmlParserV1(tokenStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ParseTree tree = parser.xml();
                if (!byteArrayOutputStream.toString().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog((Component)null, "Lexer error: " + byteArrayOutputStream.toString());
                    return;
                }
                xmlVisitor<Node> listVisitor = new MyXMLVisitor(parser, "Result");
                Node output = listVisitor.visit(tree);
                System.out.println(output.toString());

    }
}