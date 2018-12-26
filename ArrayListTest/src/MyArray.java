
public class MyArray {
	protected Node first;
	
	public MyArray(){
		first = null;
	}
	public MyArray(MyArray inp){
		
	}
	
	public void printList(){
		int x = 0;
		int max = getSize();
		while(x<max){
			System.out.println(get(x));
			x++;
		}
	}

	public int getSize(){
		int size = 0;
		
		Node x = first;
		while(x!=null){
			x= x.next;
			size++;
		}
		return size;
	}
	
	public void add(Object o){
		Node n = new Node(o);
		
		if(first == null){
			first = n;
			return;
		}
		
		Node xNode = first;
		while(xNode.next != null){
			
			xNode = xNode.next;
			
		}
		
		xNode.next = n;
		
	}
	
	public void remove(int index){
		if(index ==0){
			first = first.next;
			return;
		}
		if(index == getSize()){
			Node n = getNode(index);
			Node bef = getNode(index-1);
			bef.next =null;
			n.destroy();
			return;
		}
		
		Node beforeI = getNode(index-1);
		Node afterI = getNode(index+1);
		Node i = getNode(index);
		
		beforeI.next = afterI;
		i.destroy();
		
		
	}
	
	public Object get(int index){
		int x = 0;
		Node result = first;
		
		while(x != index){
			result = result.next;
			x++;
		}
		
		
		
		return result.usersObj;
	}
	public Node getNode(int index){
		int x = 0;
		Node result = first;
		
		while(x != index){
			if(result==null) return null;
			result = result.next;
			x++;
		}
		
		
		
		return result;
	}
	
	public boolean isEmpty(){
		return (first==null);
	}
}
