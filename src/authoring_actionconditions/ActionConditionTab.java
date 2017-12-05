package authoring_actionconditions;

import java.util.ResourceBundle;
import ActionConditionClasses.ResourceBundleUtil;
import authoring_UI.DisplayPanel;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

public class ActionConditionTab extends Tab implements ActionConditionTabI {
	
	private static final double SPACING = 10;
	
	private ScrollPane actionConditionManager;
	private TopToolBar buttons;
	private ActionConditionVBox actionConditionVBox;
	private boolean isConditionTab;
	private ResourceBundle actionTabResources;
	
	public ActionConditionTab(String title) {
		super(title);
		determineTabType(title);
		actionTabResources = ResourceBundleUtil.getResourceBundle(title);
		actionConditionManager = new ScrollPane();
		setContent(actionConditionManager);
		setUpActionConditionManager(title);
	}
	
	public ActionConditionTab(String title,ActionConditionVBox actionConditionVBox,TopToolBar topToolBar) {
		this(title);
		this.actionConditionVBox = actionConditionVBox;
		buttons = topToolBar;
	}
                                                                                                                                                                                                                                                                          
	private void setUpActionConditionManager(String title) {
		buttons = new TopToolBar(title);
		actionConditionVBox = new ActionConditionVBox(getSelectorLabel(),isConditionTab);
		VBox mainVBox = new VBox(SPACING);
		mainVBox.getChildren().addAll(buttons,actionConditionVBox);
		actionConditionManager.setContent(mainVBox);
	}
	
	protected void addTopToolBarListChangeListener(ListChangeListener<Integer> listChangeListener) {
		buttons.addRemoveRowVBoxListener(listChangeListener);
	}
	
	protected ObservableList<Integer> getCurrentActions() {
		return buttons.getRemoveRowVBoxOptions();
	}
	
	protected void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		actionConditionVBox.setNewActionOptions(newActionOptions);
	}
	
	protected String getActionCondition() {
		return buttons.getOptionsValue();
	}
	
	protected void addConditionAction(String label,ObservableList<Integer> currentActions) {
		actionConditionVBox.addConditionAction(label,currentActions);
	}
	
	protected void addRemoveOption() {
		buttons.addRemoveOption();
	}
	
	protected void removeActionCondtion(Integer row) {
		actionConditionVBox.removeConditionAction(row);
	}
	
	protected void removeRowOption(Integer row) {
		buttons.removeRemoveOption(row);
	}
	
	private void determineTabType(String title) {
		if(title.equals(DisplayPanel.conditionActionTitles.getString("ConditionsTabTitle"))) isConditionTab = true;
		else isConditionTab = false;
	}

	@Override
	public Integer getRemoveValue() {
		return buttons.getRemoveValue();
	}

	@Override
	public void addButtonListener(EventHandler<ActionEvent> e) {
		buttons.addButtonListener(e);
	}

	@Override
	public void addRemoveListener(EventHandler<ActionEvent> e) {
		buttons.addRemoveListener(e);
	}

	@Override
	public void addActionOption() {
		actionConditionVBox.addActionOption();
	}

	@Override
	public void removeActionOption(Integer action) {
		actionConditionVBox.removeActionOption(action);
	}

	@Override
	public ActionConditionVBox getActionConditionVBox() {
		return actionConditionVBox;
	}

	@Override
	public TopToolBar getTopToolBar() {
		return buttons;
	}

	@Override
	public String getSelectorLabel() {
		return actionTabResources.getString("SelectorLabel");
	}
	
	
	
}
