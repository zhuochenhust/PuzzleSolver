import java.io.IOException;

public class StartProgram {

	// For test 3
//		1 0 0 0 1 0 0 1 1 0 0  --0
//		0 1 0 0 0 1 1 0 0 1 1  --5
//		0 0 1 1 0 0 0 0 0 0 0  --6
	


		public static final String root = "/Users/haoyuchen/Documents/workspace/APuzzleSolver/Puzzle/";
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Puzzle p = new Puzzle();
			
			
			//String path = root + "checkerboard.txt";
			//String path = root + "test1.txt";
			//String path = root + "test3.txt";
			//String path = root + "test4.txt";
			//String path = root + "test2.txt";
			//String path = root + "trivial.txt";
			String path = root + "lucky13.txt";
			//String path = root + "IQ_creator.txt";
			//String path = root + "test5.txt";
			System.out.println(path);
		    try  {
		    	p.parse(path);
		    } catch (IOException e) {
			e.printStackTrace();
		    }

		}

}
