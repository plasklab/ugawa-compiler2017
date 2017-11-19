import java.util.Stack;

public class CompilerBase {
	/* レジスタ */
	static final String REG_DST = "r0";  /* return value */
	static final String REG_R1  = "r1";
	static final String REG_FP  = "r11";
	static final String REG_SP  = "r13";
	static final String REG_LR  = "r14";
	
	/* 組込み関数 */
	static final String FUNCTION_PRINT = "__pi_runtime_print";

	void emitI(String op, int imm) {
		System.out.println("\t"+op+" #"+imm);
	}
	void emitRR(String op, String rd, String rs) {
		System.out.println("\t"+op+" "+rd+", "+rs);
	}
	void emitRI(String op, String r, int imm) {
		System.out.println("\t"+op+" "+r+", #"+imm);
	}
	void emitRRR(String op, String rd, String rs1, String rs2) {
		System.out.println("\t"+op+" "+rd+", "+rs1+", "+rs2);
	}
	void emitRRI(String op, String rd, String rs1, int imm) {
		System.out.println("\t"+op+" "+rd+", "+rs1+", #"+imm);
	}
	void emitJMP(String op, String Label) {
		System.out.println("\t"+op+" "+Label);
	}
	
	void emitLDC(String rd, int val) {
		System.out.println("\tldr "+rd+", =#"+val);
	}
	void emitLDC(String rd, String label) {
		System.out.println("\tldr "+rd+", ="+label);
	}
	void emitSTR(String rs, String rd, int offset) {
		System.out.println("\tstr "+rs+", ["+rd+", #"+offset+"]");
	}
	void emitLDR(String rd, String rs, int offset) {
		System.out.println("\tldr "+rd+", ["+rs+", #"+offset+"]");
	}
	void emitPUSH(String rs) {
		System.out.println("\tstr "+rs+", [sp, #-4]!");
	}
	void emitPOP(String rd) {
		System.out.println("\tldr "+rd+", [sp], #4");
	}
	void emitCALL(String name) {
		System.out.println("\tbl "+name);
	}
	void emitRET() {
		System.out.println("\tbx "+REG_LR);
	}

	void emitLabel(String label) {
		System.out.println(label+":");
	}
	
	/* 環境 */
	static class Variable {
		String name;
		Variable(String name) {
			this.name = name;
		}
	}

	static class LocalVariable extends Variable {
		int offset;
		LocalVariable(String name, int offset) {
			super(name);
			this.offset = offset;
		}
	}
	
	static class GlobalVariable extends Variable {
		GlobalVariable(String name) {
			super(name);
		}
		String getLabel() {
			return "_Pi_var_"+name;
		}
	}

	static class Environment {
		Stack<Variable> stack = new Stack<Variable>();
		Variable lookup(String name) {
			for (int i = stack.size() - 1; i >= 0; i--) {
				Variable v = stack.get(i);
				if (v.name.equals(name))
					return v;
			}
			return null;
		}
		void push(Variable var) {
			stack.push(var);
		}
		void pop() {
			stack.pop();
		}
	}

	GlobalVariable addGlobalVariable(Environment env, String name) {
		GlobalVariable var = new GlobalVariable(name);
		env.push(var);
		return var;
	}

	/* ラベル */
	int nextLabel = 0;
	String freshLabel() {
		String label = "L"+nextLabel;
		nextLabel++;
		return label;
	}
}
