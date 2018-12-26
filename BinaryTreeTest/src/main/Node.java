package main;

public class Node {
	
	private int amount;
	
	private Node leftNode;
	private Node rightNode;
	
	public Node(){
		amount = 0;
		leftNode = null; 
		rightNode = null;
	}
	public Node(int inp){
		amount = inp;
		leftNode = null; 
		rightNode = null;
	}
	public Node(Node inp){
		amount = inp.getAmount();
		leftNode = new Node(inp.getLeft());
	}
	
	public int getAmount(){
		return amount;
	}
	public void setAmount(int a){
		amount =a ;
	}
	
	public Node getLeft(){
		return leftNode;
	}
	public void setLeft(Node inp){
		
	}
	
	public Node getRight(){
		return leftNode;
	}
	public void setRight(Node inp){
		
	}
	
	

}
