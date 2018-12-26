
public class Node {
	
	protected Object usersObj;
	protected Node next;
	
	public Node(Node inp){
		usersObj = inp.usersObj;
		if(inp.next == null){
			next = null;
			return;
		}
		next = new Node(inp.next);
	}
	
	public Node(Object obj){
		usersObj = obj;
		next = null;
	}
	public Node(Object obj, Node n){
		usersObj = obj;
		next = n;
	}
	public void destroy(){
		usersObj = null;
		next = null;
	}

}
