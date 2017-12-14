package authoring_UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import authoring.SpriteNameManager;
import authoring.Sprite.SpriteObject;
import authoring.Sprite.Parameters.SpriteParameterI;
import authoring.drawing.ImageCanvasPane;
import authoring_UI.SpriteCreatorTab.SpriteCreatorManagerSlack;
import gui.welcomescreen.WelcomeScreen;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class for image panel in SpriteCreator
 * 
 * @author taekwhunchung
 *
 */
public class SpriteImagePanel extends VBox {

	private static final String PATH = "resources/";
	// private static final String IMAGE_PATH =
	// "data/UserCreatedGames/TestProject/Sprites/CustomSprites/";
	private static final String SPRITECREATORRESOURCES_PATH = "TextResources/SpriteCreatorResources";
	private static final int PANE_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH - ViewSideBar.VIEW_MENU_HIDDEN_WIDTH;

	private ResourceBundle spriteCreatorResources;
	private HBox buttonBox;
	private HBox nameBox;
	private HBox imageBox;
	private HBox nameCategoryBox;
	private VBox paramBox;
	private String fileName;
	private TextField nameField;
	private TextField categoryField;
	private SpriteObject newSprite;
	private SpriteNameManager mySNM;
	private SpriteCreatorManagerSlack mySM;
	private SpriteCreatorImageGrid myImageGrid;
	private AuthoringEnvironmentManager myAEM;
	private SpriteCreatorDisplayPanel myDP;
	private SpriteCreatorSpriteSelector mySC;
//	private SpriteCreatorGridHandler myGridHandler;

	protected SpriteImagePanel(AuthoringEnvironmentManager AEM,
			SpriteCreatorManagerSlack SM) {
		spriteCreatorResources = ResourceBundle.getBundle(SPRITECREATORRESOURCES_PATH);
		this.setMinWidth(PANE_WIDTH / 2 - 300);
		this.setMinHeight(500);
		this.setMaxSize(PANE_WIDTH / 2 - 300, WelcomeScreen.HEIGHT);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		BorderStroke border = new BorderStroke(Color.LIGHTGREY, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY,
				BorderWidths.DEFAULT);
		this.setStyle("-fx-background-color: transparent;");
		this.setBorder(new Border(border));
		myAEM = AEM;
		mySNM = new SpriteNameManager();
		
//		myGridHandler = mySCGridHandler;
//		myGridHandler.setImagePanel(this);

		mySM = SM;
		buttonBox = new HBox(10);
		nameCategoryBox = new HBox(10);

		imageBox = new HBox();
		paramBox = new VBox();

		addButtons();
		addNameCategoryBox();
//		addImageGrid(imageGrid);

		paramBox.getChildren().addAll(buttonBox, nameCategoryBox);

		this.getChildren().addAll(paramBox, imageBox);
	}

	private void addNameCategoryBox() {
		Text enterName = new Text(spriteCreatorResources.getString("NameField"));
		enterName.setStroke(Color.WHITE);
		nameField = new TextField();
		Text enterCategory = new Text(spriteCreatorResources.getString("CategoryField"));
		enterCategory.setStroke(Color.WHITE);
		categoryField = new TextField();

		nameCategoryBox.getChildren().addAll(enterName, nameField, enterCategory, categoryField);

	}

//	private void addImageGrid(SpriteCreatorImageGrid imageGrid) {
//		myImageGrid = imageGrid;
//		imageBox.getChildren().add(myImageGrid);
//
//	}

	private void addButtons() {
		Button loadImageButton = new Button(spriteCreatorResources.getString("LoadImageButton"));
		loadImageButton.setOnAction(e -> {
			try {
				openImage();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Button createImageButton = new Button(spriteCreatorResources.getString("CreateImageButton"));
		createImageButton.setOnAction(e -> {
			Stage newStage = new Stage();
			ImageCanvasPane paint = new ImageCanvasPane(500, 500, s -> setImage(s));
			Scene paintScene = new Scene(paint);
			newStage.setScene(paintScene);
			newStage.show();
		});

		Button createSpriteButton = new Button(spriteCreatorResources.getString("CreateSpriteButton"));

		createSpriteButton.setOnAction(e -> {

			String spriteName = nameField.getText();
			if (mySNM.isNameValidTemplate(spriteName)) {
				try {
					newSprite.setName(spriteName);
					if (categoryField.getText() == null || categoryField.getText().replaceAll("\\s+", "").length() == 0) {
						myAEM.addUserSprite(newSprite);
					} else {
						myAEM.addUserSprite(categoryField.getText(), newSprite);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				;

				mySNM.addTemplateName(spriteName);

				nameField.clear();
				categoryField.clear();

//				myImageGrid.getImageStack().getChildren().remove(0);
//				mySM.setActiveSprite(null);
//				myImageGrid.setCurrentSprite(null);

				mySC.updateSpriteTabs();
				// this.getChildren().removeAll(newSprite, nameBox);
				// newSprite = new SpriteObject();

				// this.getChildren().addAll(newSprite, nameBox);

				// this.getChildren().remove(1);
				// this.getChildren().add(addSpriteTabs());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Name already exists");
				alert.setContentText("Change to a unique name");
				alert.showAndWait();
			}

		});
		buttonBox.getChildren().addAll(loadImageButton, createImageButton, createSpriteButton);
	}

	private void openImage() throws IOException {
		FileChooser imageChooser = new FileChooser();
		imageChooser.setInitialDirectory(new File("resources/"));
		imageChooser.setTitle(spriteCreatorResources.getString("ImageChooser"));
		File file = imageChooser.showOpenDialog(new Stage());

		if (file != null) {
			Files.copy(file.toPath(), Paths.get(PATH + file.getName()), StandardCopyOption.REPLACE_EXISTING);

			fileName = file.getName();
			nameField.setText(fileName.substring(0, fileName.indexOf(".")));
			categoryField.setText("General");

			newSprite = new SpriteObject();
			newSprite.setImageURL(fileName);
			newSprite.setNumCellsWidthNoException(1);
			newSprite.setNumCellsHeightNoException(1);

			myImageGrid.setSprite(newSprite);

			myDP.addSpriteEditorVBox();
			myDP.updateParameterTab(newSprite);

			// add params
			ArrayList<SpriteParameterI> myParams = new ArrayList<SpriteParameterI>();

			;
		}
	}

	private File saveToFile(Image image) {
		File outputFile = new File("resources/AAAA.png");
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		try {
			ImageIO.write(bImage, "png", outputFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return outputFile;
	}

	public String getName() {
		return nameField.getText();
	}

	public String getCategory() {
		return categoryField.getText();
	}

	public void setName(String s) {
		nameField.setText(s);
	}

	public void setCategory(String s) {
		categoryField.setText(s);
	}
	
	public void setDefaultName() {
		setName("USER_CREATED_SPRITE");
	}
	
	public void setDefaultCategory() {
		setCategory("General");
	}

	public void setImage(Image image) {
		File file = saveToFile(image);

		newSprite = new SpriteObject();
		newSprite.setImageURL(file.getName());
		newSprite.setNumCellsWidthNoException(1);
		newSprite.setNumCellsHeightNoException(1);

		myImageGrid.setSprite(newSprite);

		myDP.addSpriteEditorVBox();
		myDP.updateParameterTab(newSprite);
	}

}
