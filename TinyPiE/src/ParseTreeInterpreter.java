import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import parser.TinyPiELexer;
import parser.TinyPiEParser;
import parser.TinyPiEParser.AddExprContext;
import parser.TinyPiEParser.ExprContext;
import parser.TinyPiEParser.LiteralExprContext;
import parser.TinyPiEParser.MulExprContext;
import parser.TinyPiEParser.ParenExprContext;
import parser.TinyPiEParser.VarExprContext;

public class ParseTreeInterpreter extends InterpreterBase {
	int evalExpr(ParseTree ctxx, Environment env) {
		if (ctxx instanceof ExprContext) {
			ExprContext ctx = (ExprContext) ctxx;
			return evalExpr(ctx.addExpr(), env);
		} else if (ctxx instanceof AddExprContext) {
			AddExprContext ctx = (AddExprContext) ctxx;
			if (ctx.addExpr() == null)
				return evalExpr(ctx.mulExpr(), env);
			int lhsValue = evalExpr(ctx.addExpr(), env);
			int rhsValue = evalExpr(ctx.mulExpr(), env);
			if (ctx.ADDOP().getText().equals("+"))
				return lhsValue + rhsValue;
			else
				return lhsValue - rhsValue;
		} else if (ctxx instanceof MulExprContext) {
			MulExprContext ctx = (MulExprContext) ctxx;
			if (ctx.mulExpr() == null)
				return evalExpr(ctx.unaryExpr(), env);
			int lhsValue = evalExpr(ctx.mulExpr(), env);
			int rhsValue = evalExpr(ctx.unaryExpr(), env);
			if (ctx.MULOP().getText().equals("*"))
				return lhsValue * rhsValue;
			else
				return lhsValue / rhsValue;
		} else if (ctxx instanceof LiteralExprContext) {
			LiteralExprContext ctx = (LiteralExprContext) ctxx;
			int value = Integer.parseInt(ctx.VALUE().getText());
			return value;
		} else if (ctxx instanceof VarExprContext) {
			VarExprContext ctx = (VarExprContext) ctxx;
			String varName = ctx.IDENTIFIER().getText();
			Variable v = env.lookup(varName);
			if (v == null)
				throw new Error("Undefined variable: "+varName);
			return v.get();
		} else if (ctxx instanceof ParenExprContext) {
			ParenExprContext ctx = (ParenExprContext) ctxx;
			return evalExpr(ctx.expr(), env);
		} else
			throw new Error("Unknown parse tree node: "+ctxx.getText());		
	}

	public int eval(ParseTree tree) {
		Environment env = new Environment();
		addGlobalVariable(env, "x", 1);
		addGlobalVariable(env, "y", 10);
		addGlobalVariable(env, "z", -1);		
		return evalExpr(tree, env);
	}

	public static void main(String[] args) throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		TinyPiELexer lexer = new TinyPiELexer(input);
		CommonTokenStream token = new CommonTokenStream(lexer);
		TinyPiEParser parser = new TinyPiEParser(token);
		ParseTree tree = parser.expr();
		ParseTreeInterpreter interpreter = new ParseTreeInterpreter();
		int answer = interpreter.eval(tree);
		System.out.println(answer);
	}
}
