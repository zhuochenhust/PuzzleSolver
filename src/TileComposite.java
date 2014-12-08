import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class TileComposite extends Composite {


	public static int RESOLUTION = 25;

	public TileComposite(Composite parent, int style, Tile tile, boolean enabledColor, int[][] colorBoard) {
		super(parent, style);
		drawOriginalTile(tile, enabledColor, colorBoard );
	}

	public void drawOriginalTile(Tile tile, boolean enabledColor, int[][] colorBoard){
		char[][] charBoard = tile.getCharBoard();
		int rowCount = tile.height();
		int colCount = tile.width();
		
		GridLayout layout = new GridLayout();
		layout.numColumns = colCount;
		layout.marginWidth = layout.marginHeight = 5;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		this.setLayout(layout);
		
		Label lbl;
		GridData gd;
		Composite wrapper;
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				wrapper = new Composite(this, SWT.NULL);
				gd = new GridData(GridData.FILL, GridData.FILL, false, false);
				gd.widthHint = RESOLUTION;
				gd.heightHint = RESOLUTION;
				wrapper.setLayoutData(gd);

				layout = new GridLayout();
				layout.numColumns = 1;
				layout.marginWidth = layout.marginHeight = 0;
				layout.horizontalSpacing = layout.verticalSpacing = 0;
				wrapper.setLayout(layout);

				lbl = new Label(wrapper, SWT.NULL);
				gd = new GridData(GridData.CENTER, GridData.CENTER, true, true);
				lbl.setLayoutData(gd);

				if (charBoard[row][col] != Tile.BLANK) {
					lbl.setText("" + charBoard[row][col]);
					if(enabledColor){
						wrapper.setBackground(DemoDialog.COLORS[colorBoard[row][col]]);
						lbl.setBackground(DemoDialog.COLORS[colorBoard[row][col]]);
					}
				}
			}
		}
	}


}
