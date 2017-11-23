import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import parser.PiLangParser.AddExprContext;
import parser.PiLangParser.AssignStmtContext;
import parser.PiLangParser.CallExprContext;
import parser.PiLangParser.CompoundStmtContext;
import parser.PiLangParser.ExprContext;
import parser.PiLangParser.FuncDeclContext;
import parser.PiLangParser.IfStmtContext;
import parser.PiLangParser.LiteralExprContext;
import parser.PiLangParser.MulExprContext;
import parser.PiLangParser.ParenExprContext;
import parser.PiLangParser.ProgContext;
import parser.PiLangParser.ReturnStmtContext;
import parser.PiLangParser.StmtContext;
import parser.PiLangParser.VarExprContext;
import parser.PiLangParser.WhileStmtContext;

public class ASTGenerator {	
	ASTFunctionNode translateFuncDecl(FuncDeclContext ctx) {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<String> varDecls = new ArrayList<String>();
		ArrayList<ASTNode> stmts = new ArrayList<ASTNode>();
		String funcName = ctx.IDENTIFIER().getText();
		for (TerminalNode paramCtx: ctx.params().IDENTIFIER()) {
			String paramName = paramCtx.getText();
			params.add(paramName);
		}
		for (TerminalNode varCtx: ctx.varDecls().IDENTIFIER()) {
			String varName = varCtx.getText();
			varDecls.add(varName);
		}
		for (StmtContext stmtCtx: ctx.stmt()) {
			ASTNode stmt = translate(stmtCtx);
			stmts.add(stmt);
		}
		return new ASTFunctionNode(funcName, params, varDecls, stmts);
	}
	
	ASTNode translate(ParseTree ctxx) {
		if (ctxx instanceof ProgContext) {
			ProgContext ctx = (ProgContext) ctxx;
			ArrayList<String> varDecls = new ArrayList<String>();
			ArrayList<ASTFunctionNode> funcDecls = new ArrayList<ASTFunctionNode>();
			for (TerminalNode var: ctx.varDecls().IDENTIFIER()) {
				String varName = var.getText();
				varDecls.add(varName);
			}
			for (FuncDeclContext funcDeclCtx: ctx.funcDecl()) {
				ASTFunctionNode funcDecl = translateFuncDecl(funcDeclCtx);
				funcDecls.add(funcDecl);
			}
			return new ASTProgNode(varDecls, funcDecls);
		} else if (ctxx instanceof CompoundStmtContext) {
			CompoundStmtContext ctx = (CompoundStmtContext) ctxx;
			ArrayList<ASTNode> stmts = new ArrayList<ASTNode>();
			for (StmtContext t: ctx.stmt()) {
				ASTNode n = translate(t);
				stmts.add(n);
			}
			return new ASTCompoundStmtNode(stmts);
		} else if (ctxx instanceof AssignStmtContext) {
			AssignStmtContext ctx = (AssignStmtContext) ctxx;
			String var = ctx.IDENTIFIER().getText();
			ASTNode expr = translate(ctx.expr());
			return new ASTAssignStmtNode(var, expr);
		} else if (ctxx instanceof IfStmtContext) {
			IfStmtContext ctx = (IfStmtContext) ctxx;
			ASTNode cond = translate(ctx.expr());
			ASTNode thenClause = translate(ctx.stmt(0));
			ASTNode elseClause = translate(ctx.stmt(1));
			return new ASTIfStmtNode(cond, thenClause, elseClause);
		} else if (ctxx instanceof WhileStmtContext) {
			WhileStmtContext ctx = (WhileStmtContext) ctxx;
			ASTNode cond = translate(ctx.expr());
			ASTNode stmt = translate(ctx.stmt());
			return new ASTWhileStmtNode(cond, stmt);
	        } else if (ctxx instanceof ReturnStmtContext) {
			throw new Error("ASTGenerator.java を修正して ReturnStmtContext の変換を実装してください");
		} else if (ctxx instanceof ExprContext) {
			ExprContext ctx = (ExprContext) ctxx;
			return translate(ctx.addExpr());
		} else if (ctxx instanceof AddExprContext) {
			AddExprContext ctx = (AddExprContext) ctxx;
			if (ctx.addExpr() == null)
				return translate(ctx.mulExpr());
			ASTNode lhs = translate(ctx.addExpr());
			ASTNode rhs = translate(ctx.mulExpr());
			return new ASTBinaryExprNode(ctx.ADDOP().getText(), lhs, rhs);
		} else if (ctxx instanceof MulExprContext) {
			MulExprContext ctx = (MulExprContext) ctxx;
			if (ctx.mulExpr() == null)
				return translate(ctx.unaryExpr());
			ASTNode lhs = translate(ctx.mulExpr());
			ASTNode rhs = translate(ctx.unaryExpr());
			return new ASTBinaryExprNode(ctx.MULOP().getText(), lhs, rhs);
		} else if (ctxx instanceof LiteralExprContext) {
			LiteralExprContext ctx = (LiteralExprContext) ctxx;
			int value = Integer.parseInt(ctx.VALUE().getText());
			return new ASTNumberNode(value);
		} else if (ctxx instanceof VarExprContext) {
			VarExprContext ctx = (VarExprContext) ctxx;
			String varName = ctx.IDENTIFIER().getText();
			return new ASTVarRefNode(varName);
		} else if (ctxx instanceof ParenExprContext) {
			ParenExprContext ctx = (ParenExprContext) ctxx;
			return translate(ctx.expr());
		} else if (ctxx instanceof CallExprContext) {
			CallExprContext ctx = (CallExprContext) ctxx;
			String funcName = ctx.IDENTIFIER().getText();
			ArrayList<ASTNode> args = new ArrayList<ASTNode>();
			for (ExprContext exprCtx: ctx.args().expr()) {
				ASTNode arg = translate(exprCtx);
				args.add(arg);
			}
			return new ASTCallNode(funcName, args);
		}
		throw new Error("Unknown parse tree node: "+ctxx.getText());		
	}
}
