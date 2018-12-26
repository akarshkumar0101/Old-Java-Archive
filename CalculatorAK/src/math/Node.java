package math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Node {

	private Node parent;

	private Node[] children;

	public Node(Node parent, Node[] children) {
		setParent(parent);
		setChildren(children);
	}

	public static Node parseNode(String rawstr) {
		return NodeParser.parseNode(rawstr);
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node[] getChildren() {
		return children;
	}

	public void setChildren(Node... children) {
		this.children = children;
		if (children != null) {
			for (Node child : children) {
				child.setParent(this);
			}
		}
	}

	public abstract double evaluate();

}

class NodeParser {
	public static char OPEN_PAR = '(', CLOSE_PAR = ')', SEPERATOR = ',', OPEN_VAR = '[', CLOSE_VAR = ']';
	private final String rawStr;

	public NodeParser(String rawStr) {
		this.rawStr = rawStr.replace(" ", "");
	}

	public static Node parseNode(String rawstr) {
		NodeParser nodeParser = new NodeParser(rawstr);
		return nodeParser.parseNode();
	}

	public Node parseNode() {

		String str = getRawStr();
		int openParNum = rawNumOfChar(OPEN_PAR), closeParNum = rawNumOfChar(CLOSE_PAR);

		// check parenthesis
		if (openParNum != closeParNum)
			throw new RuntimeException("Parenthesis don't match: " + str);

		Map<String, Variable> variables = new HashMap<String, Variable>();
		List<Object> parts = new ArrayList<Object>();
		for (int i = 0; i < str.length();) {
			char c = str.charAt(i);
			if (c == OPEN_PAR) {
				int j = matchOpenPar(i);
				String in = str.substring(i + 1, j);
				parts.add(NodeParser.parseNode(in));
				i = j + 1;
			} else if (c == OPEN_VAR) {
				int j = matchOpenVar(i);
				String varName = str.substring(i + 1, j);
				if (!variables.containsKey(varName)) {
					variables.put(varName, new Variable(varName));
				}
				parts.add(new VariableInstance(null, variables.get(varName)));
				i = j + 1;
			} else if (isALetter(c)) {
				int j = i;
				do {
					c = str.charAt(++j);
				} while (isALetter(c) && j < str.length());
				String word = str.substring(i, j);
				if (word.length() > 1) {
					parts.add(word);
				} else {
					if (!variables.containsKey(word)) {
						variables.put(word, new Variable(word));
					}
					parts.add(new VariableInstance(null, variables.get(word)));
				}
				i = j;
			} else if (isPartOfNumber(c)) {
				int j = i;
				do {
					c = str.charAt(j);
				} while (isPartOfNumber(c) && ++j < str.length());
				double num = Double.parseDouble(str.substring(i, j));
				parts.add(new Number(null, num));
				i = j;
			} else if (Function.isBasicFunction(c)) {
				parts.add(c);
				i++;
			}
		}
		// parts is made of nodes, variables, functions (strings), numbers(nodes), basic
		// functions(characters)
		List<Object> finalParts = new ArrayList<Object>();
		// with functions, variables
		for (int i = 0; i < parts.size(); i++) {
			Object o = parts.get(i);
			if (o instanceof String) {
				if (Function.isFunction((String) o)) {
					Node argument = (Node) parts.get(i + 1);
					i++;
					Function func = Function.createFunction((String) o, argument);
					finalParts.add(func);
				}
			} else {
				finalParts.add(o);
			}
		}

		combine(finalParts, Function.EXP);
		combine(finalParts, Function.MUL);
		combine(finalParts, Function.DIV);
		combine(finalParts, Function.ADD);
		combine(finalParts, Function.SUB);
		Node root = (Node) finalParts.get(0);

		return root;
	}

	public static void combine(List<Object> parts, char c) {
		while (parts.contains(c)) {
			int i = parts.indexOf(c) - 1;
			Node a = (Node) parts.remove(i);
			parts.remove(i);
			Node b = (Node) parts.remove(i);
			Function func = Function.createBasicFunction(c, a, b);
			parts.add(i, func);
		}
	}

	public static boolean isPartOfNumber(char c) {
		return (c >= '0' && c <= '9') || c == '.';
	}

	public static boolean isALetter(char c) {
		c = Character.toLowerCase(c);
		return c >= 'a' && c <= 'z';
	}

	public Node parseArgumentNode() {
		boolean argumentNode = nodeNumOfChar(SEPERATOR) != 0;
		if (!argumentNode)
			return parseNode(getRawStr());

		int[] indexes = nodeIndexesOf(SEPERATOR);
		Node[] children = new Node[indexes.length + 1];
		for (int i = 0; i < children.length; i++) {
			if (i == 0) {
				children[i] = NodeParser.parseNode(getRawStr().substring(0, indexes[0]));
			} else if (i == children.length - 1) {
				children[i] = NodeParser
						.parseNode(getRawStr().substring(indexes[indexes.length - 1] + 1, getRawStr().length()));
			} else {
				children[i] = NodeParser.parseNode(getRawStr().substring(indexes[i] + 1, indexes[i + 1]));
			}
		}
		return new Node(null, children) {
			@Override
			public double evaluate() {
				throw new RuntimeException("Temporary argument node is not absorbed by any function");
			}
		};
	}

	public String getRawStr() {
		return rawStr;
	}

	public int rawNumOfChar(char... c) {
		int num = 0;
		for (char cc : rawStr.toCharArray()) {
			for (char x : c) {
				if (cc == x) {
					num++;
					break;
				}
			}
		}
		return num;
	}

	public int nodeNumOfChar(char... c) {
		Iterator<Character> itr = nodeCharacterIterator();
		int num = 0;
		while (itr.hasNext()) {
			char cc = itr.next();
			for (char x : c) {
				if (cc == x) {
					num++;
					break;
				}
			}
		}
		return num;
	}

	public int[] nodeIndexesOf(char... c) {
		int[] indexes = new int[nodeNumOfChar(c)];
		Iterator<Integer> itr = nodeIndexIterator();
		int i = 0;
		while (itr.hasNext()) {
			int index = itr.next();
			for (char x : c) {
				if (rawStr.charAt(index) == x) {
					indexes[i++] = index;
					break;
				}
			}
		}
		return indexes;
	}

	public int matchOpenPar(int index) {
		if (rawStr.charAt(index) == NodeParser.OPEN_PAR) {
			IndexIterator indexItr = new IndexIterator();
			int newindex = indexItr.match(index, NodeParser.OPEN_PAR, NodeParser.CLOSE_PAR);
			return newindex;
		} else
			return -1;
	}

	public int matchOpenVar(int index) {
		if (rawStr.charAt(index) == NodeParser.OPEN_VAR) {
			IndexIterator indexItr = new IndexIterator();
			int newindex = indexItr.match(index, NodeParser.OPEN_VAR, NodeParser.CLOSE_VAR);
			return newindex;
		} else
			return -1;
	}

	public Iterator<Integer> nodeIndexIterator() {
		return new IndexIterator();
	}

	public Iterator<Character> nodeCharacterIterator() {
		return new CharacterIterator(new IndexIterator());
	}

	class IndexIterator implements Iterator<Integer> {
		private int i;

		public IndexIterator() {
			i = 0;
			i = ensureInNode(i);
		}

		public void setIndex(int i) {
			this.i = ensureInNode(i);
		}

		public int ensureInNode(int i) {
			return match(i, NodeParser.OPEN_PAR, NodeParser.CLOSE_PAR) + 1;
		}

		public int match(int i, char open, char close) {
			int numparenth = 0;
			for (int j = i; j < rawStr.length(); j++) {
				if (rawStr.charAt(j) == open) {
					numparenth++;
				} else if (rawStr.charAt(j) == close) {
					numparenth--;
				}
				if (numparenth == 0)
					return j;
			}
			return i;
		}

		@Override
		public boolean hasNext() {
			return i < rawStr.length();
		}

		@Override
		public Integer next() {
			int next = i;
			i++;
			i = ensureInNode(i);
			return next;
		}
	}

	class CharacterIterator implements Iterator<Character> {

		private final IndexIterator indexItr;

		private CharacterIterator(IndexIterator indexItr) {
			this.indexItr = indexItr;
		}

		@Override
		public boolean hasNext() {
			return indexItr.hasNext();
		}

		@Override
		public Character next() {
			return rawStr.charAt(indexItr.next());
		}

	}

}
