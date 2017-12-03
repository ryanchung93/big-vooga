package authoring_UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import authoring.AbstractSpriteObject;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteObject;
import authoring.SpriteObjectGridManagerI;
import authoring.SpriteParameterFactory;
import authoring.SpriteParameterI;
import engine.utilities.data.GameDataHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tools.DisplayLanguage;

public class GameElementSelector extends TabPane implements Observer {
	
	private static final String SPRITES = "Sprites";
	private static final String DIALOGUES = "Dialogues";
	private static final String DEFAULT = "Default";
	private static final String USER = "User";
	private static final String IMPORTED = "Imported";
	
	private DraggableGrid myGrid;
	private SpriteSelectPanel mySprites;
	private SpriteSelectPanel myUserSprites;

	private AuthoringEnvironmentManager myAEM;
	private SpriteParameterFactory mySPF;
	private ArrayList<AbstractSpriteObject> mySpriteObjs = new ArrayList<AbstractSpriteObject>();
	private ArrayList<SpriteObject> myUserSpriteObjs = new ArrayList<SpriteObject>();
	private GameDataHandler myGDH;
	private SpriteObjectGridManagerI mySOGM;
	private SpriteGridHandler mySpriteGridHandler;

	protected GameElementSelector(SpriteGridHandler spriteGridHandler, AuthoringEnvironmentManager AEM,
			SpriteObjectGridManagerI SOGM) {
		mySPF = new SpriteParameterFactory();
		myAEM = AEM;
		// mySpriteGridHandler = myAEM.getSpriteGridHandler();
		mySpriteGridHandler = spriteGridHandler;
		// mySpriteGridHandler = myAEM.getSpriteGridHandler();
		// myAEM = AEM;
		myGDH = AEM.getGameDataHandler();
//		mySOGM = SOGM;
		//mySprites = new SpriteSelectPanel("DEFAULT", mySpriteGridHandler);
		// SpriteSet mySS = new SpriteSetDefault(myGDH);
		// SpriteSet myCustom = new SpriteSetUserDefined(myGDH);
		myAEM.getDefaultSpriteController().getAllSprites().forEach(sprite->{
			System.out.println("Sprite exists, name: "+sprite.getName());
		});
		mySprites = myAEM.getDefaultSpriteController().getSpritePanel(mySpriteGridHandler);
		
//		mySprites.getSpritePanel
		// mySS.getSpritePanel(mySpriteGridHandler);
		// mySS.getAllSpritesAsMap().forEach((a,b)->{
		// System.out.println("Key: "+a+ ", Value: "+b);
		// b.forEach(s->{
		// s.getImageURL();
		// });
		// myAEM.addDefaultSprite(b);
		// });
		// mySS.getAllSpritesAsMap().forEach((a,b)->{
		// b.forEach(item->{
		// try {
		// myGDH.saveDefaultSprite(item);

		// mySS.saveSprite(a, item);
		// System.out.println("Saved: "+item);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// });

		// });
		// myUserSprites = new SpriteSelectPanel("USERDEFINED",
		// mySpriteGridHandler);
		// myUserSprites = myCustom.getSpritePanel(mySpriteGridHandler);
		myUserSprites = myAEM.getCustomSpriteController().getSpritePanel(mySpriteGridHandler);
//		 getParams();
		// createSprites();
		createSpriteTabs();
		this.setPrefWidth(80);
		// myUserSprites.getChildren().add(sp);
		// makeSpriteDraggable(sp);

	}

	private void createSprites() {
		// SpriteObject s1 = mySpriteObjs.get(0);
		// SpriteObject s2 = mySpriteObjs.get(1);
		// SpriteObject s3 = mySpriteObjs.get(2);
		// SpriteObject s4 = mySpriteObjs.get(3);
		// myAEM.addDefaultSprite(s1);
		// myAEM.addDefaultSprite(s2);
		// myAEM.addDefaultSprite(s3);
		// myAEM.addDefaultSprite(s4);
//		setupDefaultSprites();
//		setupUserDefinedSprites();

	}

	public void getUserSpriteParam(String url) {
		SpriteObject userSprite = new SpriteObject(url);
		ArrayList<SpriteParameterI> param = new ArrayList<SpriteParameterI>();
		param.add(mySPF.makeParameter("canFight", false));
		param.add(mySPF.makeParameter("health", 17.0));
		param.add(mySPF.makeParameter("name", "Ryan"));

		for (SpriteParameterI SP : param) {
			userSprite.addParameter(SP);
		}

		myUserSpriteObjs.add(userSprite);
		createUserSprite(userSprite);
	}

	/**
	 * creates new user sprite
	 * 
	 * @author taekwhunchung
	 * @author Samuel
	 * @param sp
	 */

	public void createUserSprite(Object spObj) {
		if (!(spObj instanceof SpriteObject)) {
			throw new RuntimeException("Its not a sprite");
		}
		SpriteObject sp = (SpriteObject) spObj;
		try {
			this.myAEM.addUserSprite(sp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// addNewUserDefinedSprite(sp, myUserSprites.getChildren().size());
	}

	// private void addNewDefaultSprite(SpriteObject sp) {
	// mySprites.addNewDefaultSprite(sp);
	// }

	private void addNewUserDefinedSprite(SpriteObject sp, int spLocation) {
		myUserSprites.addNewDefaultSprite(sp, spLocation);
	}

//	public void setupDefaultSprites() {
//		ArrayList<SpriteObject> defaults = myAEM.getDefaultGameSprites();
//		mySprites.setupDefaultSprites(defaults);
//	}

//	public void setupUserDefinedSprites() {
//		ArrayList<SpriteObject> defaults = myAEM.getUserDefinedSprites();
//		myUserSprites.setupDefaultSprites(defaults);
//	}

	public void getParams() {
		System.out.println("Params!!");
		ArrayList<String> urls = new ArrayList<String>();
		urls.add("file:/Users/Samuel/Documents/workspace/voogasalad_bigvooga/resources/tree.png");
		urls.add("file:/Users/Samuel/Documents/workspace/voogasalad_bigvooga/resources/brick.png");
		urls.add("file:/Users/Samuel/Documents/workspace/voogasalad_bigvooga/resources/pikachu.png");
		urls.add("file:/Users/Samuel/Documents/workspace/voogasalad_bigvooga/resources/water.png");
		 urls.add("file:/Users/Samuel/Documents/workspace/voogasalad_bigvooga/resources/Link.png");
		double i = 10.0;
		ArrayList<String> s = new ArrayList<String>();
		ArrayList<String> names = new ArrayList<String>();
		names.add("tree");
		names.add("brick");
		names.add("pikachu");
		names.add("water");
		s.add("hello");
		s.add("world");
		s.add("bye");
		for (int h = 0; h < 4; h++) {
			AbstractSpriteObject SO = new SpriteObject();
			SO.setImageURL(urls.get(h));
			SO.setName(names.get(h));
			ArrayList<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();
			myParams.add(mySPF.makeParameter("canFight", true));
			myParams.add(mySPF.makeParameter("health", i));
			myParams.add(mySPF.makeParameter("name", s.get(0)));
			for (SpriteParameterI SP : myParams) {
				SO.addParameter(SP);
			}
			mySpriteObjs.add(SO);
			// try {
			// throw new IOException("Dont break");
			
			// } catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			try {
				myGDH.saveDefaultSprite(SO);
				System.out.println("Saved " + SO.getImageURL());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SpriteObject SO = new SpriteObject();
		SO.setName("testinginventory");
		SO.addParameter(mySPF.makeParameter("hellothere" , "aweaponforalesscivilizedage"));
		SO.setInventory(mySpriteObjs);
		SO.setImageURL(urls.get(4));
		
		

		// SpriteObject SO = new SpriteObject(urls.get(4));
		// ArrayList<SpriteParameterI> myParams = new
		// ArrayList<SpriteParameterI>();
		// myParams.add(mySPF.makeParameter("canFight", true));
		// myParams.add(mySPF.makeParameter("health", 10));
		// myParams.add(mySPF.makeParameter("arrows", 10));
		// myParams.add(mySPF.makeParameter("name", s.get(0)));
		// myParams.add(mySPF.makeParameter("stamina", 50));
		// for (SpriteParameterI SP: myParams){
		// SO.addParameter(SP);
		// }
		// mySpriteObjs.add(SO);
	}

	private void createSpriteTabs() {
		
		TabPane spritesTabPane = new TabPane();
		TabPane dialoguesTabPane = new TabPane();
		
		Tab defaultSpriteTab = createSubTab(DEFAULT);
		Tab userSpriteTab = createSubTab(USER);
		Tab importedSpriteTab = createSubTab(IMPORTED);
		Tab defaultDialogueTab = createSubTab(DEFAULT);
		Tab userDialogueTab = createSubTab(USER);
		Tab importedDialogueTab = createSubTab(IMPORTED);

		spritesTabPane.getTabs().addAll(defaultSpriteTab, userSpriteTab, importedSpriteTab);
		spritesTabPane.setSide(Side.RIGHT);
		
		dialoguesTabPane.getTabs().addAll(defaultDialogueTab, userDialogueTab, importedDialogueTab);
		dialoguesTabPane.setSide(Side.RIGHT);

		Tab spritesTab = createElementTab(SPRITES, spritesTabPane);
		Tab dialoguesTab = createElementTab(DIALOGUES, dialoguesTabPane);
		
		this.getTabs().addAll(spritesTab, dialoguesTab);
		this.setSide(Side.TOP);

	}
	
	private Tab createSubTab(String tabName) {
		Tab subTab = new Tab();
		subTab.textProperty().bind(DisplayLanguage.createStringBinding(tabName));
//		defaultSpriteTab.setContent(mySprites);
		subTab.setContent(makeGrid());
		subTab.setClosable(false);
		return subTab;
	}
	
	private Tab createElementTab(String tabName, TabPane tabPane) {
		Tab elementTab = new Tab();
		elementTab.textProperty().bind(DisplayLanguage.createStringBinding(tabName));
		elementTab.setContent(tabPane);
		elementTab.setClosable(false);
		return elementTab;
	}

	private ScrollPane makeGrid() {
		GridPane gp = new GridPane();
		
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 15; j++) {
				StackPane sp = new StackPane();
				sp.setPrefHeight(50);
				sp.setPrefWidth(50);
				sp.setBackground(
						new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED,
						CornerRadii.EMPTY, BorderWidths.DEFAULT);
				sp.setBorder(new Border(border));
				gp.add(sp, i, j);
			}
		}
		
		ScrollPane sp = new ScrollPane(gp);
		return sp;
	}

	// private ImageView createTrash() {
	// ImageView trashCan = new ImageView(new Image("trash.png"));
	// trashCan.setFitWidth(45);
	// trashCan.setFitHeight(45);
	// mySpriteGridHandler.addDropToTrash(trashCan);
	//
	// return trashCan;
	// }

	// adds new user sprites
	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg);
		System.out.println("notified observer");
		System.out.println(mySprites);
		createUserSprite(arg);
	}

}