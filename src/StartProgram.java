import java.io.IOException;

public class StartProgram {


		public static final String root = "/Users/haoyuchen/Documents/workspace/APuzzleSolver/Puzzle/";
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Puzzle p = new Puzzle();
			
			
			//String path = root + "checkerboard.txt";
			String path = root + "test1.txt";
			System.out.println(path);
		    try  {
		    	p.parse(path);
		    } catch (IOException e) {
			e.printStackTrace();
		    }

		}

}
