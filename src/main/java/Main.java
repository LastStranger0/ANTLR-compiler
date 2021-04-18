import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
                ANTLRInputStream fstream = new ANTLRInputStream(new FileInputStream("resources/test.txt"));
                xmlLexer lexer = new xmlLexer((CharStream) fstream);
                CommonTokenStream tokenStream = new CommonTokenStream(lexer);
                xmlParser parser = new xmlParser(tokenStream);
                ParseTree tree = parser.xml();
                ParseTreeWalker walker = new ParseTreeWalker();
                walker.walk(new MyXMLListener(), tree);

    }
}