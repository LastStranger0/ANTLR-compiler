import AST.Node;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
                ANTLRInputStream fstream = new ANTLRInputStream(new FileInputStream("resources/test.txt"));
                xmlLexer lexer = new xmlLexer(fstream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                xmlParserV1 parser = new xmlParserV1(tokenStream);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ParseTree tree = parser.xml();
                if (!byteArrayOutputStream.toString().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null, "Lexer error: " + byteArrayOutputStream.toString());
                    return;
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                xmlVisitor<Node> listVisitor = new MyXMLVisitor(/*parser, "Result"*/);
                Node output = listVisitor.visit(tree);
                System.out.println(gson.toJson(output));

    }
}