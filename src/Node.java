
public class Node {
	Node leftNode;
	Node rightNode;
	Node upNode;
	Node downNode;
	
	Node columnNode;
	int nodeNumber;
	int columnId; 
	int rowId;


	
//	Node columnNode
	public Node(int id){
		this.columnId = id;
			
	}
	public Node(Node leftNode, Node rightNode, Node upNode, Node downNode){
		this.leftNode = leftNode;
		this.rightNode = rightNode;
		this.upNode = upNode;
		this.downNode = downNode;
	}
	public void setColumnNode(Node columnNode){
		this.columnNode =  columnNode;	
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
	public Node getColumnNode(){
		return this.columnNode;
	}
	
	public void setRowId(int rowId){
		this.rowId = rowId;
	}
	public void setColumnId(int columnId){
		this.columnId = columnId;
	}
	public int getRowId(){
		return this.rowId;
	}
	public int getColumnId(){
		return this.columnId;
	}

	public void setNodeNumber(int nodeNumber){
		this.nodeNumber = nodeNumber;
	}
    public int getNodeNumber(){
    	return this.nodeNumber;
    } 

	
}
