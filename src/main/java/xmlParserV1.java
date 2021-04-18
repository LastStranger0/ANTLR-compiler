import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import java.util.ArrayList;
import java.util.List;

public class xmlParserV1 extends xmlParser {
    public xmlParserV1(TokenStream input) {
        super(input);
    }

    public List<Token> getTokens(Token start, Token end) {
        ArrayList<Token> list = new ArrayList<>();

        for(int i = start.getTokenIndex(); i <= end.getTokenIndex(); ++i) {
            list.add(this.getTokenStream().get(i));
        }

        return list;
    }
}
