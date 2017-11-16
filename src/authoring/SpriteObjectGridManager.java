package authoring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.image.ImageView;

public class SpriteObjectGridManager implements SpriteObjectGridManagerI {
	
	private ArrayList<ArrayList<SpriteObjectI>> spriteGrid;
	private int MAX_ROWS = 10;
	private int MAX_COLS = 10;
	private int CURR_ROWS = 10;
	private int CURR_COLS = 10;
	private SpriteObjectI defaultEmptySprite;
	private Set<Integer []> activeCells;
	
	
	SpriteObjectGridManager() {
		setDefaultEmptySprite(new SpriteObject());
		initializeGrid();
		activeCells = new HashSet<Integer[]>();
	}

	private void initializeGrid() {
		spriteGrid = new ArrayList<ArrayList<SpriteObjectI>>();
		for (int i=0; i<CURR_ROWS;i++){
			spriteGrid.add(new ArrayList<SpriteObjectI>());
			for (int j=0; j<CURR_COLS;j++){
				spriteGrid.get(i).add(defaultEmptySprite.newCopy());
			}
		}
	}
	
	@Override
	public ImageView[][] getGrid() {
		ImageView [][] ret = new ImageView[CURR_ROWS][CURR_COLS];
		for (int i=0;i<CURR_ROWS;i++){
			for (int j=0; j<CURR_COLS;j++){
				ret[i][j] = spriteGrid.get(i).get(j).getImageView();
			}
		}
		return ret;
	}

	@Override
	public ImageView[][] populateCell(SpriteObjectI spriteObject, ArrayList<Integer[]> row_col) {
		for (Integer [] loc: row_col) {
			setCell(spriteObject.newCopy(), loc);
		}
		return getGrid();
	}

	@Override
	public ArrayList<SpriteParameterI> getSpriteParameters(ArrayList<Integer[]> row_col) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setDefaultEmptySprite(SpriteObjectI SPI) {
		defaultEmptySprite  = SPI.newCopy();
	}
	
	@Override
	public void addActiveCells(ArrayList<Integer[]> makeActive){
		activeCells.addAll(makeActive);
	}
	
	@Override
	public void removeActiveCells(ArrayList<Integer[]> makeInactive){
		activeCells.removeAll(makeInactive);
	}
	
	@Override
	public void resetActiveCells() {
		activeCells.clear();
	}
	
	@Override
	public ArrayList<SpriteObjectI> getActiveSpriteObjects(){
		ArrayList<SpriteObjectI> ret = new ArrayList<SpriteObjectI>();
		for (Integer[] loc: activeCells){
			ret.add(getCell(loc));
		}
		return ret;
	}
	
	@Override
	public void clearCells(ArrayList<Integer[]> cellsToClear){
		for (Integer[] loc: cellsToClear){
			setCellAsDefault(loc);
		}
	}
	
	private void setCell(SpriteObjectI SOI, Integer[] loc) {
		spriteGrid.get(loc[0]).set(loc[1], SOI);
	}
	
	private void setCellAsDefault(Integer[] loc) {
		setCell(defaultEmptySprite.newCopy(), loc);
	}
	
	private SpriteObjectI getCell(Integer [] loc){
		return spriteGrid.get(loc[0]).get(loc[1]);
	}

	@Override
	public void matchActiveCellsToSprite(SpriteObjectI firstSprite) {
		for (SpriteObjectI SOI: getActiveSpriteObjects()){
			SOI.applyParameterUpdate(firstSprite.getParameters());
		}
		
	}
	

}
