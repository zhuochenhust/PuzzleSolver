import java.util.ArrayList;

public class Solver {
    Node head;
    ArrayList<ArrayList<Node>> solutions = new ArrayList<ArrayList<Node>>();
	public Solver(Node head){
		this.head = head;
		this.solvePuzzle();
	}
	
	
	public void solvePuzzle(){
	    ArrayList<Node> solution = new ArrayList<Node>();
	    this.search(0, solution);
		
		//test cover
//		Node columnNode = head.getRight();
//		cover(columnNode);
	}
	
	public void search(int k, ArrayList<Node> solution){
		boolean flag = true;
		Node columnNode = new Node(-1);
		if(head.getRight() == null){
			// firstly add solution and then output it
			// Deeply copy solution and add it to solution list.
		    ArrayList<Node> anotherSolution = new ArrayList<Node>();
		    for(int i=0; i< solution.size(); i++){
		    	anotherSolution.add(solution.get(i));
		    }
			solutions.add(anotherSolution);
		    //printSolution();
			return;
		} else { 
			 if(flag){
				  columnNode = head.getRight();
				  flag = false;
			  } else {
				  columnNode = columnNode.getRight();   
			  } 
			 if (columnNode == null){	
				    ArrayList<Node> oneSolution = new ArrayList<Node>();
				    for(int i=0; i< solution.size(); i++){
				    	oneSolution.add(solution.get(i));
				    }
					solutions.add(oneSolution);
					//printSolution();
					return;
			 }
//			  cover(columnNode); 
			 // if there is empty column, then it means in that case, there is no solution. return;
             if (checkAllColumnEmpty(head)){
            	 return;       	 
             }
             
		     for( Node rowNode = columnNode.getDown() ; rowNode != null ; rowNode = rowNode.getDown() ) { 
		           	solution.add( rowNode ); 
		           	//System.out.println("Before Cover: ");
		           	//printNet(head);
		           	cover(rowNode); 
		           	//System.out.println("After Cover: ");
		           	//printNet(head);

//		           	for( Node rightNode = rowNode.getRight() ; rightNode != null ; rightNode = rightNode.getRight() ) 
//		                	cover( rightNode.getColumnNode() ); 
 		           	this.search( k+1, solution); 

		           solution.remove( rowNode );
		           //System.out.println("Before unCover: ");
		           //printNet(head);
//		           columnNode = rowNode.getColumnNode(); 
		           unCover( rowNode ); 
		           //System.out.println("After unCover: ");
		           //printNet(head);
//		          	for( Node leftNode = rowNode.getLeft() ; leftNode != null ; leftNode = leftNode.getLeft() ) 
//		               	uncover( leftNode.getColumnNode() ); 
		      } 

		     
		}
	}
	
	public void unCover(Node rowNode){
		Node columnNode = rowNode.getColumnNode();
		
		if(columnNode.getRight() != null){
			columnNode.getRight().setLeftNode( columnNode ); 
		}
		if(columnNode.getLeft() != null){
		   columnNode.getLeft().setRightNode( columnNode  ); 
		}
//      try {
//		Node otherColumn;
		for(Node column = rowNode.getColumnNode(); rowNode != null; ){
	//	for(Node column = rowNode; column != null; rowNode = rowNode.getRight()){
			column = rowNode.getColumnNode();
			rowNode = rowNode.getRight();
			if(column.getRight() != null){
				column.getRight().setLeftNode( column ); 
			}
			if(column.getLeft() != null){
				column.getLeft().setRightNode( column ); 
			}	
		for( Node row = column.getDown() ; row != null ; row = row.getDown() ){ 
		     	for( Node rightNode = row.getRight() ; rightNode != null ; rightNode = rightNode.getRight() ) { 
		          	if(rightNode.getUp() != null){
		     		    rightNode.getUp().setDownNode( rightNode ); 
		          	}
		          	if(rightNode.getDown() != null){
		          	   rightNode.getDown().setUpNode( rightNode ); 
		          	}
		     	}
		     	for( Node leftNode = row.getLeft() ; leftNode != null ; leftNode = leftNode.getLeft() ) { 
		     		if(leftNode.getUp() != null){
		     			leftNode.getUp().setDownNode( leftNode ); 
		          	}
		          	if(leftNode.getDown() != null){
		          		leftNode.getDown().setUpNode( leftNode ); 
		          	}
		     	}
		  }
		}

	}	
	
	
	public void cover(Node rowNode){
		Node columnNode = rowNode.getColumnNode();
		
		if(columnNode.getRight() != null){
			columnNode.getRight().setLeftNode( columnNode.getLeft() ); 
		}
		if(columnNode.getLeft() != null){
		   columnNode.getLeft().setRightNode( columnNode.getRight() ); 
		}
//      try {
//		Node otherColumn;
		for(Node column = rowNode.getColumnNode(); rowNode != null; ){
			column = rowNode.getColumnNode();
			rowNode = rowNode.getRight();
			if(column.getRight() != null){
				column.getRight().setLeftNode( column.getLeft() ); 
			}
			if(column.getLeft() != null){
				column.getLeft().setRightNode( column.getRight() ); 
			}	
			
		for( Node row = column.getDown() ; row != null ; row = row.getDown() ){ 
		     	for( Node rightNode = row.getRight() ; rightNode != null ; rightNode = rightNode.getRight() ) { 
//		    		otherColumn = rightNode.getColumnNode();
//		    		if(otherColumn.getRight() != null){
//		    			otherColumn.getRight().setLeftNode( otherColumn.getLeft() ); 
//		    		}
//		    		if(otherColumn.getLeft() != null){
//		    			otherColumn.getLeft().setRightNode( otherColumn.getRight() ); 
//		    		}
//		    		coverRow(otherColumn);
		          	if(rightNode.getUp() != null){
		     		    rightNode.getUp().setDownNode( rightNode.getDown() ); 
		          	}
		          	if(rightNode.getDown() != null){
		          	   rightNode.getDown().setUpNode( rightNode.getUp() ); 
		          	}
		     		//coverRow(rightNode);
		     	}
		     	for( Node leftNode = row.getLeft() ; leftNode != null ; leftNode = leftNode.getLeft() ) { 
		     		if(leftNode.getUp() != null){
		     			leftNode.getUp().setDownNode( leftNode.getDown() ); 
		          	}
		          	if(leftNode.getDown() != null){
		          		leftNode.getDown().setUpNode( leftNode.getUp() ); 
		          	}
		     	}
		  }
		}
//      }  catch ( Throwable error ) {
//         System.out.println("I found you!");
//      }
	}
	
//	public void uncover(Node columnNode){
//		if(columnNode.getRight() != null){
//		     columnNode.getRight().setLeftNode( columnNode ); 
//		}
//		if(columnNode.getLeft() != null){
//		columnNode.getLeft().setRightNode( columnNode ); 
//		
//		Node otherColumn;
//		for( Node row = columnNode.getDown() ; row != null ; row = row.getDown() ) { 
//		     	for( Node leftNode = row.getRight() ; leftNode != null ; leftNode = leftNode.getRight() ) { 
//		    		otherColumn = leftNode.getColumnNode();
//		    		if(otherColumn.getRight() != null){
//		    			otherColumn.getRight().setLeftNode( otherColumn ); 
//		    		}
//		    		if(otherColumn.getLeft() != null){
//		    			otherColumn.getLeft().setRightNode( otherColumn ); 
//		    		}
//		    		unCoverRow(otherColumn);
//
//		     	} 
//}
//		}
//
//	}
	
	
	public void coverRow(Node columnNode){
	Node rowNode = columnNode.getDown();
	
	while (rowNode != null ){
		Node leftNode = rowNode.getLeft();
		while(leftNode != null){
          	if(leftNode.getUp() != null ){
          		leftNode.getUp().setDownNode( leftNode.getDown() ); 
          	}
        	if(leftNode.getDown() != null ) {
        		leftNode.getDown().setUpNode( leftNode.getUp() ); 
        	}
        	leftNode = leftNode.getLeft();
		}
		Node rightNode = rowNode.getRight();
		while(rightNode != null){
          	if(rightNode.getUp() != null ){
          		rightNode.getUp().setDownNode( rightNode.getDown() ); 
          	}
        	if(rightNode.getDown() != null ) {
        		rightNode.getDown().setUpNode( rightNode.getUp() ); 
        	}
        	rightNode = rightNode.getRight();
		}
		
//      	if(rowNode.getUp() != null ){
// 		rowNode.getUp().setDownNode( rowNode ); 
//      	}
//    	if(leftNode.getDown() != null ) {
// 		rowNode.getDown().setUpNode( rowNode ); 
//    	}
    	rowNode = rowNode.getDown();
	}    
}
	
public void unCoverRow(Node columnNode){
	Node rowNode = columnNode.getDown();
	
	while (rowNode != null ){
		Node leftNode = rowNode;
		while(leftNode.getLeft() != null){
          	if(leftNode.getUp() != null ){
     		leftNode.getUp().setDownNode( leftNode ); 
          	}
        	if(leftNode.getDown() != null ) {
     		leftNode.getDown().setUpNode( leftNode ); 
        	}
		}
		Node rightNode = rowNode;
		while(rightNode.getRight() != null){
          	if(rightNode.getUp() != null ){
     		rightNode.getUp().setDownNode( rightNode ); 
          	}
        	if(rightNode.getDown() != null ) {
     		rightNode.getDown().setUpNode( rightNode ); 
        	}
		}
		
//      	if(rowNode.getUp() != null ){
// 		rowNode.getUp().setDownNode( rowNode ); 
//      	}
//    	if(leftNode.getDown() != null ) {
// 		rowNode.getDown().setUpNode( rowNode ); 
//    	}
    	rowNode = rowNode.getDown();
	}    
}
	
	
	public void printSolution(){
		Node node;
		System.out.println("Solution size: " + solutions.size());
		for(int i=0; i < solutions.size(); i++){
			System.out.println("Print out Solution " + (i+1) +" :");
			for(int j = 0; j < solutions.get(i).size(); j++ ){
			  node = solutions.get(i).get(j);
			  System.out.println("ColumnId: " + node.columnId + "RowId: " + node.rowId);
			}
		}
	}
	
	//test result to get the right connection among the row
	public void printNet(Node head){
		Node column = head.getRight();;
		Node row;
		while(column != null){
		   row = column.getDown();
		   while(row != null){
			        Node temprow = row;
					if (temprow.getLeft() == null){
						int count =1;
						while(temprow.getRight() != null ){
							temprow = temprow.getRight();
							count++;
						}
						System.out.println("Row Count:" + count);
					}
			      row = row.getDown();
				}
		   column = column.getRight();
		}
	}
	
	public boolean checkAllColumnEmpty(Node head){
		if (head == null){
			System.out.println("head couldn't be null");
			return true;
		}
		Node columnNode = head.getRight();
		
		while(columnNode != null){
			if(columnNode.getDown() == null){
				return true;
			}	
			columnNode = columnNode.getRight();
		}
		return false;
	}
	

}
