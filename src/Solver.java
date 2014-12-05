import java.util.ArrayList;

public class Solver {
    Node head;
    ArrayList<Node> solution = new ArrayList<Node>();
    ArrayList<ArrayList<Node>> solutions = new ArrayList<ArrayList<Node>>();
	public Solver(Node head){
		this.head = head;
		this.solvePuzzle();
	}
	
	public void solvePuzzle(){
	    this.search(0);
		
		//test cover
//		Node columnNode = head.getRight();
//		cover(columnNode);
	}
	
	public void search(int k){
		boolean flag = true;
		Node columnNode = new Node(-1);
		if(head.getRight() == null){
			printSolution();
			solutions.add(solution);
			return;
		} else { 
			 if(flag){
				  columnNode = head.getRight();
				  flag = false;
			  } else {
				  columnNode = columnNode.getRight();   
			  } 
			 if (columnNode == null){
					printSolution();
					return;
			 }
			  cover(columnNode);  	 

		     for( Node rowNode = columnNode.getDown() ; rowNode != null ; rowNode = rowNode.getDown() ) { 
		           	solution.add( rowNode ); 

		           	for( Node rightNode = rowNode.getRight() ; rightNode != null ; rightNode = rightNode.getRight() ) 
		                	cover( rightNode.getColumnNode() ); 

		           	this.search( k+1); 

		           solution.remove( rowNode ); 
		           columnNode = rowNode.getColumnNode(); 

		          	for( Node leftNode = rowNode.getLeft() ; leftNode != null ; leftNode = leftNode.getLeft() ) 
		               	uncover( leftNode.getColumnNode() ); 
		      } 

		     uncover( columnNode ); 
		}
	}
	public void cover(Node columnNode){

		if(columnNode.getRight() != null){
			columnNode.getRight().setLeftNode( columnNode.getLeft() ); 
		}
		if(columnNode.getLeft() != null){
		   columnNode.getLeft().setRightNode( columnNode.getRight() ); 
		}
      try {
		for( Node row = columnNode.getDown() ; row != null ; row = row.getDown() ){ 
		     	for( Node rightNode = row.getRight() ; rightNode != null ; rightNode = rightNode.getRight() ) { 
		          	rightNode.getUp().setDownNode( rightNode.getDown() ); 
		          	rightNode.getDown().setUpNode( rightNode.getUp() ); 
		     	} 
		}	
      }  catch ( Throwable error ) {
         System.out.println("I found you!");
      }
	}
	
	public void uncover(Node columnNode){
		
		for( Node row = columnNode.getDown() ; row != null ; row = row.getDown() ) { 
		     	for( Node leftNode = row.getRight() ; leftNode != null ; leftNode = leftNode.getRight() ) { 
		          	if(leftNode.getUp() != null ){
		     		leftNode.getUp().setDownNode( leftNode ); 
		          	}
		        	if(leftNode.getDown() != null ) {
		     		leftNode.getDown().setUpNode( leftNode ); 
		        	}
		     	} 
		if(columnNode.getRight() != null){
		     columnNode.getRight().setLeftNode( columnNode ); 
		}
		if(columnNode.getLeft() != null){
		columnNode.getLeft().setRightNode( columnNode ); 
		}
		}

	}
	public void printSolution(){
		Node node;
		for(int i=0; i < solution.size(); i++){
			  node = solution.get(i);
			  System.out.println("ColumnId: " + node.columnId + "RowId: " + node.rowId);
		}
	}
	

}
