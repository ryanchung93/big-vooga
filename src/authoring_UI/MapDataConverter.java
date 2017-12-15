package authoring_UI;


import java.util.ArrayList;
import java.util.List;

import authoring.GridManagers.SpriteObjectGridManager;
import engine.utilities.data.GameDataHandler;

public class MapDataConverter {
//	private final XStream SERIALIZER = setupXStream();
	private String myName;
//	private String layerPath;
	private List<LayerDataConverter> gridManagers;
	private GameDataHandler GDH;
	
	public String getName(){
		return myName;
	}
	
	public MapDataConverter getToSerialize(){
		return this;
	}
	
	public MapDataConverter(DraggableGrid grids, GameDataHandler GDH) {
		this.GDH = GDH;
		convertToMDC(grids);
	}
	
//	public String getLayerPath() {
//		return layerPath;
//	}
	
//	public void setLayerPath(String path) {
//		layerPath = path;
//	}
	
	private void convertToMDC(DraggableGrid grids){
		this.myName = grids.getName();
//		List<SpriteObjectGridManager> SOGMs = 
		gridManagers= new ArrayList<LayerDataConverter>();
				grids.getGrids().forEach(grid->{
					gridManagers.add(new LayerDataConverter(grid));
				});		
		GDH = null;
	}
	
	public void setGameDataHandler(GameDataHandler GDH){
		this.GDH = GDH;

	}
	
	public DraggableGrid createDraggableGrid() {
		DraggableGrid newMap = new DraggableGrid(GDH);
		newMap.setName(this.myName);
		List<SpriteObjectGridManager> SOGMs = new ArrayList<SpriteObjectGridManager>();
		this.gridManagers.forEach(LDC->{
			LDC.setGameDataHandler(GDH);
			SOGMs.add(LDC.createLayer());
		});
		newMap.loadLayers(SOGMs);
		return newMap;
	}
}