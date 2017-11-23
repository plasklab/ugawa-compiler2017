import java.util.ArrayList;

public class ASTNode {}

class ASTProgNode extends ASTNode {
	ArrayList<String> varDecls;
	ArrayList<ASTFunctionNode> funcDecls;
	ASTProgNode(ArrayList<String> varDecls, ArrayList<ASTFunctionNode> funcDecls) {
		this.varDecls = varDecls;
		this.funcDecls = funcDecls;
	}
	@Override
	public String toString() {
		String s = "(Prog (";
		for (String varDecl: varDecls)
			s += varDecl + " ";
		s += ")\n";
		for (ASTFunctionNode funcDecl: funcDecls)
			s += funcDecl + "\n";
		s += ")";
		return s;
	}
}

class ASTFunctionNode extends ASTNode {
	String name;
	ArrayList<String> params;
	ArrayList<String> varDecls;
	ArrayList<ASTNode> stmts;
	ASTFunctionNode(String name, ArrayList<String> params, ArrayList<String> varDecls, ArrayList<ASTNode> stmts) {
		this.name = name;
		this.params = params;
		this.varDecls = varDecls;
		this.stmts = stmts;
	}
	@Override
	public String toString() {
		String s = "(FuncDecl "+name+"\n";
		s += "(";
		for (String param: params)
			s += param + " ";
		s += ") (";
		for (String varDecl: varDecls)
			s += varDecl + " ";
		s += ")\n";
		for (ASTNode stmt: stmts)
			s += stmt + "\n";
		s += ")";
		return s;
	}
}

class ASTCompoundStmtNode extends ASTNode {
	ArrayList<ASTNode> stmts;
	ASTCompoundStmtNode(ArrayList<ASTNode> stmts) {
		this.stmts = stmts;
	}
	@Override
	public String toString() {
		String s = "(CompoundStmt \n";
		for (ASTNode stmt: stmts)
			s += stmt + "\n";
		s += ")";
		return s;
	}
}

class ASTAssignStmtNode extends ASTNode {
	String var;
	ASTNode expr;
	ASTAssignStmtNode(String var, ASTNode expr) {
		this.var = var;
		this.expr = expr;
	}
	@Override
	public String toString() {
		return "(AssignStmt "+var+" "+expr+")";
	}
}

class ASTIfStmtNode extends ASTNode {
	ASTNode cond;
	ASTNode thenClause;
	ASTNode elseClause;
	ASTIfStmtNode(ASTNode cond, ASTNode thenClause, ASTNode elseClause) {
		this.cond = cond;
		this.thenClause = thenClause;
		this.elseClause = elseClause;
	}
	@Override
	public String toString() {
		return "(IfStmt "+cond+"\n"+thenClause+"\n"+elseClause+")";
	}
}

class ASTWhileStmtNode extends ASTNode {
	ASTNode cond;
	ASTNode stmt;
	ASTWhileStmtNode(ASTNode cond, ASTNode stmt) {
		this.cond = cond;
		this.stmt = stmt;
	}
	@Override
	public String toString() {
		return "(WhileStmt "+cond+"\n"+stmt+")";
	}
}

class ASTBinaryExprNode extends ASTNode {
	String op;
	ASTNode lhs;
	ASTNode rhs;
	ASTBinaryExprNode(String op, ASTNode lhs, ASTNode rhs) {
		this.op = op;
		this.lhs = lhs;
		this.rhs = rhs;
	}
	@Override
	public String toString() {
		return "(BinExpr "+op+" "+lhs+" "+rhs+")";
	}
}

class ASTNumberNode extends ASTNode {
	int value;
	ASTNumberNode(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "(Number "+value+")";
	}
}

class ASTVarRefNode extends ASTNode {
	String varName;
	ASTVarRefNode(String varName) {
		this.varName = varName;
	}
	@Override
	public String toString() {
		return "(VarRef "+varName+")";
	}
}

class ASTCallNode extends ASTNode {
	String name;
	ArrayList<ASTNode> args;
	ASTCallNode(String name, ArrayList<ASTNode> args) {
		this.name = name;
		this.args = args;
	}
	@Override
	public String toString() {
		String s = "(CallExpr \n";
		for (ASTNode arg: args)
			s += arg + "\n";
		s += ")";
		return s;
	}
}
