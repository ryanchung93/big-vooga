package authoring_UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ActionConditionClasses.ResourceBundleUtil;
import authoring.AuthoringEnvironmentManager;
import authoring.SpriteCreatorSpriteManager;
import authoring.Sprite.AbstractSpriteObject;
import authoring.Sprite.SpriteAnimationSequenceTabsAndInfo;
import authoring.Sprite.SpriteInventoryTabAndInfo;
import authoring.Sprite.SpriteParameterTabsAndInfo;
import authoring.Sprite.SpriteUtilityTabAndInfo;
import authoring_actionconditions.ActionRow;
import authoring_actionconditions.ActionTab;
import authoring_actionconditions.ConditionRow;
import authoring_actionconditions.ConditionTab;
import authoring_actionconditions.ControllerConditionActionTabs;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

public class SpriteCreatorDisplayPanel extends VBox {
	private TabPane myParamTabs;
	private TabPane mySpriteTabs;
	private VBox myParamTabVBox;
	private TextArea myParameterErrorMessage;
	private SpriteParameterTabsAndInfo mySParameterTAI;
	private SpriteInventoryTabAndInfo mySInventoryTAI;
	private SpriteUtilityTabAndInfo mySUtilityTAI;
	private SpriteAnimationSequenceTabsAndInfo mySAnimationSequenceTAI;
	private ObjectProperty<Boolean> multipleCellsActiveProperty;
	private VBox spriteEditorAndApplyButtonVBox;
	private ActionTab<ActionRow> actions;
	private ConditionTab<ConditionRow> conditions;
	private ControllerConditionActionTabs controllerConditionActionTabs;
	private AuthoringEnvironmentManager myAEM;

	private static final String ACTIONCONDITIONTITLES_PATH = "TextResources/ConditionActionTitles";
	private static final double DISPLAY_PANEL_WIDTH = MainAuthoringGUI.AUTHORING_WIDTH / 2
			- ViewSideBar.VIEW_MENU_HIDDEN_WIDTH - 155;
	private static final double DISPLAY_PANEL_HEIGHT = 495;

	public static final ResourceBundle conditionActionTitles = ResourceBundle.getBundle(ACTIONCONDITIONTITLES_PATH);
	// private SpriteSetHelper mySSH;

	public SpriteCreatorDisplayPanel(SpriteCreatorSpriteManager spriteManager, AuthoringEnvironmentManager AEM) {
		myAEM = AEM;
		multipleCellsActiveProperty = new SimpleObjectProperty<Boolean>();
		mySParameterTAI = new SpriteParameterTabsAndInfo();
		mySInventoryTAI = new SpriteInventoryTabAndInfo(myAEM);
		mySAnimationSequenceTAI = new SpriteAnimationSequenceTabsAndInfo();
		mySUtilityTAI = new SpriteUtilityTabAndInfo();
		;
		setUpMenu();
	}

	private void setErrorMessage() {
		myParameterErrorMessage = new TextArea("Either no active cells or active cells have different parameters");
	}

	private void setSpriteInfoAndVBox() {
		spriteEditorAndApplyButtonVBox = new VBox();
		spriteEditorAndApplyButtonVBox.getChildren().addAll(mySpriteTabs, this.makeApplyButton());
	}

	// private SpriteObject getActiveCell() throws Exception {
	// // ;
	// return mySPSM.getActiveSprite();
	// }
	//
	// private void checkMultipleCellsActive() {
	// this.multipleCellsActiveProperty.set(mySPSM.multipleActive());
	// }

	private void setUpMenu() {
		setErrorMessage();
		createParameterCategoryTabs();
		createSpriteTabs();
		// createSpriteCreator();
		this.setPrefSize(DISPLAY_PANEL_WIDTH, DISPLAY_PANEL_HEIGHT);
		setSpriteInfoAndVBox();

		// createStatePane(new VBox());
	}

	private void createActionConditionTabs() {
		conditions = new ConditionTab<ConditionRow>(ResourceBundleUtil.getTabTitle("ConditionsTabTitle"));
		actions = new ActionTab<ActionRow>(ResourceBundleUtil.getTabTitle("ActionsTabTitle"));
		controllerConditionActionTabs = new ControllerConditionActionTabs(conditions, actions);
		//applyButtonController = new ApplyButtonController();
		mySpriteTabs.getTabs().addAll(conditions, actions);
	}

	private void createParameterTab() {
		Tab parameters = new Tab("Parameters");
		parameters.setContent(createContainingVBoxToPlaceInParameterTab());
		mySpriteTabs.getTabs().addAll(parameters);
	}

	private void createDialogueTab() {
		Tab dialogue = new Tab("Dialogue");
		dialogue.setContent(new TextArea("dialogue goes here"));
		mySpriteTabs.getTabs().addAll(dialogue);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			dialogue.setDisable(newStatus);
		});
	}

	private void createInventoryTab() {
		Tab inventory = new Tab("Inventory");
		inventory.setContent(mySInventoryTAI.getContainingVBox());
		mySpriteTabs.getTabs().addAll(inventory);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			inventory.setDisable(newStatus);
		});
	}

	private void createUtilityTab() {
		Tab utility = new Tab("Utility");
		utility.setContent(mySUtilityTAI.getScrollPane());
		mySpriteTabs.getTabs().addAll(utility);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			utility.setDisable(newStatus);
		});
	}

	private void createAnimationTab() {
		Tab animations = new Tab("Animations");
		animations.setContent(mySAnimationSequenceTAI.getAnimationBox());
		mySpriteTabs.getTabs().add(animations);
		multipleCellsActiveProperty.addListener((observable, oldStatus, newStatus) -> {
			animations.setDisable(newStatus);
		});
	}

	private void createSpriteTabs() {
		mySpriteTabs = new TabPane();
		mySpriteTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		mySpriteTabs.setSide(Side.TOP);
		createParameterTab();
		createDialogueTab();
		createActionConditionTabs();
		createInventoryTab();
		createUtilityTab();
		createAnimationTab();
		// .setOnSelectionChanged(e->{displayParams();});
		// parameters.setContent(myParamTabs);
		// mySpriteTabs.getTabs().addAll(parameters, dialogue);

	}

	/**
	 * Creates the VBox that will contain the TabPane of the Active Sprite's
	 * parameters This VBox will be the content of the Tab that says parameters.
	 * 
	 * @author Samuel
	 * @return VBox - the VBox to contain Sprite Parameter Info
	 */
	private VBox createContainingVBoxToPlaceInParameterTab() {
		myParamTabVBox = new VBox();

		myParamTabVBox.getChildren().addAll(myParamTabs);

		setDefaultErrorNoSpriteTabPane();

		// theHorizTabs = myParamTabs;

		// myParamTabVBox.getChildren().addAll(theHorizTabs, applyButton);
		// addParameterErrorMessage();
		return myParamTabVBox;
	}

	/**
	 * Make's the apply button that the user will click to apply changes the Active
	 * Sprite Makes button's set on action call the 'apply' method in this class.
	 * 
	 * @author Samuel
	 * @return Button - the button UI component.
	 */
	private Button makeApplyButton() {
		Button applyButton = new Button();
		applyButton.textProperty().setValue("Apply");
		applyButton.setOnAction(e -> {
			apply();
		});
		return applyButton;
	}

	/**
	 * Ensures that the instance variable containing the TabPane of the Active
	 * Sprite's Parameters is pointed to the class controlling that info.
	 * 
	 * @author Samuel
	 * 
	 */
	private void createParameterCategoryTabs() {
		// mySPTAI.createCategoryTabs();
		myParamTabs = mySParameterTAI.getTabPane();

		// myParamTabs = new TabPane();
		// myParamTabs.setSide(Side.RIGHT);
		// myParamTabs.setPrefHeight(500);
		// myParamTabs.setPrefWidth(400);

	}

	private void clearAllSpriteEditorTabs() {
		// myParamTabs.getTabs().clear();
		mySParameterTAI.clearTabPane();
		mySInventoryTAI.reset();
		// mySUtilityTAI.reset();
		// mySAnimationSequenceTAI.reset();
	}

	protected void removeSpriteEditorVBox() {
		if (this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().remove(spriteEditorAndApplyButtonVBox);
		}
	}

	public void addSpriteEditorVBox() {
		if (!this.getChildren().contains(spriteEditorAndApplyButtonVBox)) {
			this.getChildren().addAll(spriteEditorAndApplyButtonVBox);
		}
	}

	private void setDefaultErrorNoSpriteTabPane() {
		clearAllSpriteEditorTabs();
		removeSpriteEditorVBox();
		addSpriteEditorErrorMessage();
	}

	private void removeSpriteEditorErrorMessage() {
		if (this.getChildren().contains(myParameterErrorMessage)) {
			this.getChildren().remove(myParameterErrorMessage);
		}
	}

	private void addSpriteEditorErrorMessage() {
		if (!this.getChildren().contains(myParameterErrorMessage)) {
			// ;
			int numChildren = this.getChildren().size();
			this.getChildren().add(numChildren, myParameterErrorMessage);
		}
	}

	public void updateParameterTab(AbstractSpriteObject s) {

		;
		try {
			clearAllSpriteEditorTabs();
			removeSpriteEditorErrorMessage();
			mySParameterTAI.create(s);
			mySInventoryTAI.setSpriteObjectAndUpdate(s);
			mySUtilityTAI.setSpriteObjectAndUpdate(s);
			mySAnimationSequenceTAI.setSpriteObject(s);
			addSpriteEditorVBox();
		} catch (Exception e) {
			// throw new RuntimeException();
			e.printStackTrace();
			setDefaultErrorNoSpriteTabPane();
		}
		this.setPrefWidth(DISPLAY_PANEL_WIDTH);
	}

	private void apply() {
		mySParameterTAI.apply();
		mySInventoryTAI.apply();
		mySAnimationSequenceTAI.apply();
		// OwensActiosn.apply()
		// mySPSM.apply();
	}

	private void addParameter() {
		List<String> choices = new ArrayList<>();
		choices.add("Boolean");
		choices.add("String");
		choices.add("Double");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Boolean", choices);
		dialog.setTitle("Add Parameter");
		dialog.setContentText("Choose Parameter Type:");

		Optional<String> result = dialog.showAndWait();
		result.ifPresent(type -> createNewParameter(type));
	}

	private void createNewParameter(String type) {
	}
}
