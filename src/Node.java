
public class Node {
	Node leftNode;
	Node rightNode;
	Node upNode;
	Node downNode;


	
//	Node columnNode
	public Node(){
			
	}
	public Node(Node leftNode, Node rightNode, Node upNode, Node downNode){
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.upNode = upNode;
		this.downNode = downNode;
	}

	public void setRightNode(Node rightNode){
		this.rightNode =  rightNode;	
	}
	public void setLeftNode(Node leftNode){
		this.leftNode =  leftNode;	
	}
	public void setUpNode(Node upNode){
		this.upNode =  upNode;	
	}
	public void setDownNode(Node downNode){
		this.downNode =  downNode;	
	}
	
	public Node getRight(){
		return this.rightNode;	
	}
	
	public Node getLeft(){
		return this.leftNode;		
	}
	public Node getUp(){
		return this.upNode;	
	}
	public Node getDown(){
	    return this.downNode;
	}

	
}
