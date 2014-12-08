import java.io.IOException;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class DemoDialog extends Dialog implements IMonitor{
   private static final int MAX_TILE = 20;

   public static final Color[] COLORS = new Color[MAX_TILE + 1];

   static {
			Device device = Display.getCurrent();
			COLORS[0] = new Color(device, 255, 255, 255);
			COLORS[1] = new Color(device, 255, 255, 0);
			COLORS[2] = new Color(device, 153, 76, 0);
			COLORS[3] = new Color(device, 127, 0, 204);
			COLORS[4] = new Color(device, 255, 0, 127);
			COLORS[5] = new Color(device, 0, 127, 0);
			COLORS[6] = new Color(device, 255, 127, 0);
			COLORS[7] = new Color(device, 204, 204, 230);
			COLORS[8] = new Color(device, 204, 204, 0);
			COLORS[9] = new Color(device, 255, 25, 25);
			COLORS[10] = new Color(device, 102, 0, 0);
			COLORS[11] = new Color(device, 0, 255, 0);
			COLORS[12] = new Color(device, 102, 127, 230);
			COLORS[13] = new Color(device, 255, 89, 127);
			COLORS[14] = new Color(device, 76, 76, 255);
			COLORS[15] = new Color(device, 25, 0, 127);
			COLORS[16] = new Color(device, 255, 166, 179);
			COLORS[17] = new Color(device, 76, 255, 76);
			COLORS[18] = new Color(device, 76, 76, 255);
			COLORS[19] = new Color(device, 255, 127, 51);
			COLORS[20] = new Color(device, 0, 0, 0);
		}

		private Puzzle puzzle = new Puzzle();
		private Shell shell;
		private Display display;
		
        IMonitor monitor ;

		public DemoDialog(Shell parent) {
			super(parent);
			this.shell = parent;
			this.display = parent.getDisplay();
			createDialogArea(parent);
			//Add monitor means will run the solution program
            monitor = this;
		}

		protected void createDialogArea(Composite parent) {
			Composite diagContainer = new Composite(parent, SWT.NULL);

			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			diagContainer.setLayout(layout);

			createNewPuzzleArea(diagContainer);
			createConfigArea(diagContainer);
			
			createPieceBar(diagContainer);
		}

		TileComposite tileComposite;
		
		int solCount = 0;
		int curSol = -1;

		Button prevButton;
		Label solLabel;
		Button nextButton;
		Composite solContainer;
		Composite pieceContainer;
		
		private ExpandBar pieceBar;
		private ExpandItem[] pieceItem = new ExpandItem[3];
		private Composite[] pieceItemContainer = new Composite[3];
		
		private void createNewPuzzleArea(Composite container) {
			final Group newPuzzleGroup = new Group(container, SWT.NONE);
			newPuzzleGroup.setText("Puzzle Input");
			newPuzzleGroup.setLayoutData(new GridData(GridData.FILL,
					GridData.CENTER, true, false));

			GridLayout layout = new GridLayout();
			layout.numColumns = 3;
			newPuzzleGroup.setLayout(layout);

			new Label(newPuzzleGroup, SWT.NULL).setText("Puzzle file:");
			final Text pathText = new Text(newPuzzleGroup, SWT.BORDER | SWT.SINGLE
					| SWT.READ_ONLY);
			GridData gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
			pathText.setLayoutData(gd);

			Button browseButton = new Button(newPuzzleGroup, SWT.NULL);
			browseButton.setText("Open");
			gd = new GridData(GridData.BEGINNING, GridData.CENTER, false, false);
			gd.widthHint = 65;
			browseButton.setLayoutData(gd);
			browseButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					FileDialog dialog = new FileDialog(shell, SWT.NULL);
					String path = dialog.open();
					if (path != null) {
						try {
							pathText.setText(path);
							puzzle.parse(path);

							solCount = 0;
							curSol = -1;
							tileComposite = null;
							prevButton = null;
							solLabel = null;
							nextButton = null;
							solContainer = null;
							pieceContainer = null;

							for (int i = 0; i < 3; i++) {
								if (pieceItem[i] != null) {
									pieceItemContainer[i].dispose();
									pieceItemContainer[i] = null;
									pieceItem[i].dispose();
									pieceItem[i] = null;
								}
							}
							createSolutionItem();
							createBoardItem();
							createTileItem();
							pieceItem[0].setExpanded(true);
							pieceItem[1].setExpanded(true);
							pieceItem[2].setExpanded(false);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			});
		}

		private Button chkSingleSol;
		private Button chkRotate;
		private Button chkReflection;
		private Button solveButton;
		
		private static boolean  EnabledColor = true;
		private static boolean  DisabledColor = false;

		private void createConfigArea(Composite container) {
			Group configGroup = new Group(container, SWT.NONE);
			configGroup.setText("Solver Configuration");
			configGroup.setLayoutData(new GridData(GridData.FILL, GridData.CENTER,
					true, false));

			GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			configGroup.setLayout(layout);

			chkSingleSol = new Button(configGroup, SWT.CHECK);
			chkSingleSol.setText("All Solutions");

			chkRotate = new Button(configGroup, SWT.CHECK);
			chkRotate.setText("Enable Rotation");
			// chkRotate.setSelection(true);

			chkReflection = new Button(configGroup, SWT.CHECK);
			chkReflection.setText("Enable Reflection");
			GridData gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
			chkReflection.setLayoutData(gd);
			// chkReflection.setSelection(true);

			solveButton = new Button(configGroup, SWT.NULL);
			solveButton.setText("Solve Puzzle");

			solveButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (puzzle.getBoard() == null)
						return;
					final boolean singleSolution = !chkSingleSol.getSelection();
					final boolean useRotation = chkRotate.getSelection();
					final boolean useReflection = chkReflection.getSelection();

					solCount = 0;
					curSol = -1;

					pieceItem[1].setExpanded(false);
					pieceItem[2].setExpanded(true);
					pieceItem[2].setText("Solutions");
					if (tileComposite != null) {
						tileComposite.dispose();
						tileComposite = null;
					}

					Thread solverThread = new Thread() {
						@Override
						public void run() {
							puzzle.solver(singleSolution, useRotation,
											useReflection, monitor);
							
							display.syncExec(new Runnable() {
								@Override
								public void run() {
									pieceItem[2]
											.setText(String
													.format(
															"Solution: (%s solutions in %s min %s sec)",
															puzzle.solutionSize, puzzle.getTotalTime() / 60000,
															(puzzle.getTotalTime() % 60000) / 1000.0));
								}
							});
						}
					};
					solverThread.setDaemon(true);
					solverThread.start();
                 while(1 == 1){
					if (solverThread.getState() != Thread.State.RUNNABLE){
						if (curSol == -1) {
							curSol++;
							System.out.println("curSol: " + curSol );
							

							if (tileComposite != null) {
								tileComposite.dispose();
								tileComposite = null;
							}
//							System.out.println("Solution Size: " + puzzle.solver.arraySolutions.size());
//							if(puzzle.solver.arraySolutions.size() != 0){
//							      tileComposite = new TileComposite(pieceContainer,
//									SWT.NULL, puzzle.maxTile, EnabledColor, puzzle.solver.arraySolutions.get(curSol));
//							
//							       tileComposite.pack();
//							}
							System.out.println("Solution Size: " + puzzle.getSolutions().size());
							if(puzzle.getSolutions().size() != 0){
							      tileComposite = new TileComposite(pieceContainer,
									SWT.NULL, puzzle.maxTile, EnabledColor, puzzle.getSolutions().get(curSol));
							
							       tileComposite.pack();
							}
							pieceContainer.pack();
							pieceItem[2].setHeight(solContainer.computeSize(
									SWT.DEFAULT, SWT.DEFAULT).y);
						}
						prevButton.setEnabled(puzzle.solutionSize > 0);
						nextButton.setEnabled(curSol < puzzle.solutionSize - 1);
						solLabel.setText(String.format("Solution %s of %s",
								(curSol + 1), puzzle.solutionSize));
						break;
					}
				}
				}
			});
		}

//		private ExpandBar pieceBar;
//		private ExpandItem[] pieceItem = new ExpandItem[3];
//		private Composite[] pieceItemContainer = new Composite[3];

		private void createPieceBar(Composite container) {
			pieceBar = new ExpandBar(container, SWT.V_SCROLL);
			pieceBar.setSpacing(8);
			pieceBar.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true,
					true));
		}

		private void createTileItem() {
			createExpandItem(0, String
					.format("Tile (%s)", puzzle.getTiles().length), puzzle
					.getTiles());
		}

		private void createBoardItem() {
			createExpandItem(1, "Board", puzzle.getMaxTile());
		}

		private void createExpandItem(int index, String title, Tile... tiles) {
			ScrolledComposite sc = new ScrolledComposite(pieceBar, SWT.H_SCROLL
					| SWT.V_SCROLL);

			Composite wrapper = new Composite(sc, SWT.NULL);
			sc.setContent(wrapper);
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			layout.marginWidth = layout.marginHeight = 0;
			wrapper.setLayout(layout);

			Composite container = new Composite(wrapper, SWT.NULL);
			layout = new GridLayout();
			layout.numColumns = tiles.length;
			container.setLayout(layout);
			for (Tile tile : tiles) {
				new TileComposite(container, SWT.NULL, tile, EnabledColor, tile.getColorBoard());
			}
			wrapper.pack();

			ExpandItem item = new ExpandItem(pieceBar, SWT.NONE, 0);
			item.setText(title);

			item.setHeight(sc.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(sc);
//			item.setImage(ImageUtil.getImageOfTile());

			pieceItemContainer[index] = sc;
			pieceItem[index] = item;
		}


		private void createSolutionItem() {
			solContainer = new Composite(pieceBar, SWT.NULL);
			GridLayout layout = new GridLayout();
			layout.numColumns = 1;
			solContainer.setLayout(layout);

			pieceContainer = new Composite(solContainer, SWT.NULL);
			layout = new GridLayout();
			layout.numColumns = 1;
			pieceContainer.setLayout(layout);
			pieceContainer.setLayoutData(new GridData(GridData.CENTER,
					GridData.CENTER, true, false));

			Composite buttonContainer = new Composite(solContainer, SWT.NULL);
			layout = new GridLayout();
			layout.numColumns = 3;
			buttonContainer.setLayout(layout);
			buttonContainer.setLayoutData(new GridData(GridData.CENTER,
					GridData.CENTER, true, false));

			prevButton = new Button(buttonContainer, SWT.NULL);
			prevButton.setText("<<");
			prevButton.setEnabled(false);

			solLabel = new Label(buttonContainer, SWT.NULL);
			solLabel.setText(String.format("Solution %s of %s", (curSol + 1),
					solCount));
			solLabel.setAlignment(SWT.CENTER);
			GridData gd = new GridData(GridData.FILL, GridData.CENTER, true, false);
			gd.widthHint = 150;
			solLabel.setLayoutData(gd);

			nextButton = new Button(buttonContainer, SWT.NULL);
			nextButton.setText(">>");
			nextButton.setEnabled(false);

			ExpandItem item = new ExpandItem(pieceBar, SWT.NONE, 0);
			item.setText("Solutions");
			item.setHeight(solContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
			item.setControl(solContainer);
//			item.setImage(ImageUtil.getImageOfTile());

			pieceItem[2] = item;
			pieceItemContainer[2] = solContainer;

			prevButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					curSol--;

					if (tileComposite != null) {
						tileComposite.dispose();
						tileComposite = null;
					}
					tileComposite = new TileComposite(pieceContainer, SWT.NULL, puzzle.maxTile, EnabledColor
						,puzzle.getSolutions().get(curSol));
					tileComposite.pack();
					pieceContainer.pack();
					pieceItem[2].setHeight(solContainer.computeSize(SWT.DEFAULT,
							SWT.DEFAULT).y);

					prevButton.setEnabled(curSol > 0);
					nextButton.setEnabled(curSol < puzzle.solutionSize - 1);
					solLabel.setText(String.format("Solution %s of %s",
							(curSol + 1), puzzle.solutionSize));
				}
			});

			nextButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					curSol++;

					if (tileComposite != null) {
						tileComposite.dispose();
						tileComposite = null;
					}
					tileComposite = new TileComposite(pieceContainer, SWT.NULL,
							puzzle.maxTile, EnabledColor, puzzle.getSolutions().get(curSol));
					tileComposite.pack();
					pieceContainer.pack();
					pieceItem[2].setHeight(solContainer.computeSize(SWT.DEFAULT,
							SWT.DEFAULT).y);

					prevButton.setEnabled(curSol > 0);
					nextButton.setEnabled(curSol < puzzle.solutionSize - 1);
					solLabel.setText(String.format("Solution %s of %s",
							(curSol + 1), puzzle.solutionSize));
				}
			});
		}  

//		@Override
		public void update(int event) {
			display.syncExec(new Runnable() {
				@Override
				public void run() {
					solCount++;
					if (curSol == -1) {
						curSol++;
						System.out.println("curSol: " + curSol );
						

						if (tileComposite != null) {
							tileComposite.dispose();
							tileComposite = null;
						}
//						System.out.println("Solution Size: " + puzzle.solver.arraySolutions.size());
//						if(puzzle.solver.arraySolutions.size() != 0){
//						      tileComposite = new TileComposite(pieceContainer,
//								SWT.NULL, puzzle.maxTile, EnabledColor, puzzle.solver.arraySolutions.get(curSol));
//						
//						       tileComposite.pack();
//						}
//						System.out.println("Solution Size: " + puzzle.getSolutions().size());
//						if(puzzle.getSolutions().size() != 0){
//						      tileComposite = new TileComposite(pieceContainer,
//								SWT.NULL, puzzle.maxTile, EnabledColor, puzzle.getSolutions().get(curSol));
//						
//						       tileComposite.pack();
//						}
						pieceContainer.pack();
						pieceItem[2].setHeight(solContainer.computeSize(
								SWT.DEFAULT, SWT.DEFAULT).y);
					}
					prevButton.setEnabled(curSol > 0);
					nextButton.setEnabled(curSol < solCount - 1);
					solLabel.setText(String.format("Solution %s of %s",
							(curSol + 1), solCount));
				}
			});
		}
	
	
}
