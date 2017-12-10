package authoring.Layers;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.Sprite.Parameters.*;
import authoring.Sprite.AnimationSequences.*;
import authoring.Sprite.UtilityTab.*;
import authoring.Sprite.InventoryTab.*;
import authoring.SpriteManagers.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;

public class BackgroundLayer extends MapLayer {
	
	public BackgroundLayer(int rows, int columns, SpriteGridHandler SGH){
		this(rows, columns, 0, SGH, Color.TRANSPARENT);
	}

	BackgroundLayer(int rows, int columns, int layerNum, SpriteGridHandler SGH, Color c) {
		super(rows, columns, layerNum, SGH, c);
//		setDefaultColor(Color.YELLOW);
		setName("Background");
	}
	
	@Override 
	public void setBackgroundImage(String imagePath){
		AuthoringMapStackPane AMSP = this.getChildAtPosition(0, 0);
		AbstractSpriteObject ASO = new SpriteObject(imagePath);
		AMSP.addChild(ASO);
		AMSP.setRowSpan(this.numRowsProperty.get());
		AMSP.setColSpan(this.numColumnsProperty.get());
		numColumnsProperty.addListener((observable, oldNumColumns, newNumColumns)->{
			AMSP.setRowSpan(newNumColumns);
		});
	
	
	

}