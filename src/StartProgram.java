import java.io.IOException;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


public class StartProgram {

		public static final String root = "/Users/haoyuchen/Documents/workspace/APuzzleSolver/Puzzle/";
		public static void main(String[] args) {
			final Display display = new Display();
			final Shell shell = new Shell(display);
			shell.setMaximized(true);
			shell.setLayout(new FillLayout());

			new DemoDialog(shell);
			shell.setText("Tiling Puzzle Solver");
			shell.addShellListener(new ShellAdapter() {
				public void shellClosed(ShellEvent e) {
					Shell[] shells = display.getShells();
					for (int i = 0; i < shells.length; i++) {
						if (shells[i] != shell)
							shells[i].close();
					}
				}
			});
			shell.setSize(550, 700);
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			
//			Puzzle p = new Puzzle();
//			
//			
//			//String path = root + "checkerboard.txt";
//			//String path = root + "test1.txt";
//			//String path = root + "test3.txt";
//			//String path = root + "test4.txt";
//			//String path = root + "test2.txt";
//			//String path = root + "trivial.txt";
//			//String path = root + "lucky13.txt";
//			//String path = root + "IQ_creator.txt";
//			String path = root + "thirteen_holes.txt";
//			//String path = root + "test5.txt";
//			System.out.println(path);
//		    try  {
//		    	p.parse(path);
//		    } catch (IOException e) {
//			e.printStackTrace();
//		    }

		}

}
