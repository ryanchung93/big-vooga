package authoring.SpritePanels;

import authoring.GridManagers.*;
import authoring.Sprite.*;
import authoring.SpritePanels.*;
import authoring.util.*;
import authoring_UI.Map.*;
import authoring_UI.*;
import authoring.*;
import authoring_UI.Inventory.*;
public class InventorySpritePanels extends SpritePanels{
	
	InventorySpritePanels(){
		super();
	}
	
	public InventorySpritePanels(SpriteGridHandler mySGH, AuthoringEnvironmentManager myAEM) {
		super(mySGH, myAEM);
		
	}
	
	@Override
	public void makeLayerDisplayPanel(AuthoringEnvironmentManager myAEM){
		displayPanel = new DisplayPanel(SPSM, myAEM);  
	}

}
