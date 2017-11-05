import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;

import parser.TinyPiELexer;


public class Tokenizer {
	static public void main(String[] args) throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		TinyPiELexer lexer = new TinyPiELexer(input);
		while (true) {
			Token token = lexer.nextToken();
			if (token.getType() == Token.EOF)
				break;
			System.out.println(token.getType() + ": "+ token.getText());
		}
	}
}
