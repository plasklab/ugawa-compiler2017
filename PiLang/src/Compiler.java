import java.io.IOException;
import java.util.ArrayList;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import parser.PiLangLexer;
import parser.PiLangParser;

public class Compiler extends CompilerBase {
	Environment globalEnv;
	
	void compileFunction(ASTFunctionNode nd) {
		Environment env = new Environment();
		String epilogueLabel = freshLabel();
		for (int i = 0; i < nd.params.size(); i++) {
			String name = nd.params.get(i);
			int offset = 4 * (i + 1);
			LocalVariable var = new LocalVariable(name, offset);
			env.push(var);
		}

		/* ここにプログラムを追加する */
	}
	
	void compileStmt(ASTNode ndx, String epilogueLabel, Environment env) {
		/* ここを完成させる */
	}

	void compileExpr(ASTNode ndx, Environment env) {
		/* ここを完成させる */
	}
	
	void compile(ASTNode ast) {
		globalEnv = new Environment();
		ASTProgNode program = (ASTProgNode) ast;

		System.out.println("\t.section .data");
		System.out.println("\t@ 大域変数の定義");		
		for (String varName: program.varDecls) {
			if (globalEnv.lookup(varName) != null)
				throw new Error("Variable redefined: "+varName);
			GlobalVariable v = addGlobalVariable(globalEnv, varName);
			emitLabel(v.getLabel());
			System.out.println("\t.word 0");
		}

		System.out.println("\t.section .text");
		System.out.println("\t.global _start");
		System.out.println("_start:");
		System.out.println("\t@ main関数を呼出す．戻り値は r0 に入る");
		emitJMP("bl", "main");
		System.out.println("\t@ EXITシステムコール");
		emitRI("mov", "r7", 1);      // EXIT のシステムコール番号
		emitI("swi", 0);
		
		/* 関数定義 */
		for (ASTFunctionNode func: program.funcDecls)
			compileFunction(func);
	}

	public static void main(String[] args) throws IOException {
		ANTLRInputStream input = new ANTLRInputStream(System.in);
		PiLangLexer lexer = new PiLangLexer(input);
		CommonTokenStream token = new CommonTokenStream(lexer);
		PiLangParser parser = new PiLangParser(token);
		ParseTree tree = parser.prog();
		ASTGenerator astgen = new ASTGenerator();
		ASTNode ast = astgen.translate(tree);
		Compiler compiler = new Compiler();
		compiler.compile(ast);
	}
}
