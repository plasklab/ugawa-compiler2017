import java.util.ArrayList;

public class ASTNode {}

class ASTProgNode extends ASTNode {
	ArrayList<String> varDecls;
	ASTNode stmt;
	ASTProgNode(ArrayList<String> varDecls, ASTNode stmt) {
		this.varDecls = varDecls;
		this.stmt = stmt;
	}
	@Override
	public String toString() {
		String s = "(Prog (";
		for (String varDecl: varDecls)
			s += varDecl + " ";
		s += ") " + stmt + ")";
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
