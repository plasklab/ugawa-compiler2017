import java.util.Stack;

public class InterpreterBase {
	/* 環境 */
	static class Variable {
		String name;
		int value;
		Variable(String name) {
			this.name = name;
		}
		void set(int value) {
			this.value = value;
		}
		int get() {
			return value;
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

	void addGlobalVariable(Environment env, String name, int value) {
		Variable var = new Variable(name);
		var.set(value);
		env.push(var);
	}
}
