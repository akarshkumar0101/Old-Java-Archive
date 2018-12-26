package data;

import java.io.Serializable;


public class StudentList implements Serializable{

	private static final long serialVersionUID = 6161552885041667646L;

	private class Node implements Serializable{

		private static final long serialVersionUID = -4196927635940962198L;
		
		private Student student;
		private Node next;
		
		//no need for copy or blank const

		public Node(Student stu) {
			student = stu;
			next = null;
		}

	}
	
	public static StudentList mainList;
	
	private Node first;

	public StudentList() { // locked constructor
		first = null;
	}

	public int getSize() {
		int size = 0;

		Node x = first;
		while (x != null) {
			x = x.next;
			size++;
		}
		return size;
	}
        public void clearList(){
            first = null;
            System.gc();
        }
	public void add(Student stu) {
		Node n = new Node(stu);

		if (first == null) {
			first = n;
			return;
		}

		Node xNode = first;
		while (xNode.next != null) {

			xNode = xNode.next;

		}

		xNode.next = n;

	}

	public void remove(int index) {
		if (index == 0) {
			first = first.next;
			return;
		}
		
		Node beforeI = getNode(index - 1);
		Node afterI = getNode(index + 1);
		beforeI.next = afterI;

	}

	public void remove(String ID) {
		int index = 0;
		Node result = first;

		while (result != null) {
			if (result.student.getID() == ID) {
				remove(index);
				return;
			}
			index++;
			result = result.next;
		}

	}

	public Student get(int index) {
		if(index<0) return null;
		int x = 0;
		Node result = first;

		while (x != index) {
			result = result.next;
			x++;
		}
		return result.student;
	}

	public Student get(String ID) {
		Node result = first;

		while (result != null) {
			if (result.student.getID().equals(ID))return result.student;
			result = result.next;
		}
		return null;
	}

	private Node getNode(int index) {
		int x = 0;
		Node result = first;

		while (x != index) {
			if (result == null)
				return null;
			result = result.next;
			x++;
		}

		return result;
	}
	
	public boolean isEmpty() {
		return (first == null);
	}

	@Override
	public String toString() {
		String result = "List:\n";
		Node x = first;
		while (x != null) {
			result = result + "\t" + x.student.toSimpleString() + "\n";
			x = x.next;
		}
		return result;
	}
	
}
