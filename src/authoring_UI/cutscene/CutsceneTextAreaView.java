package authoring_UI.cutscene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import authoring_UI.displayable.DisplayableTextAreaView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CutsceneTextAreaView extends DisplayableTextAreaView {

	private static final double VBOX_SPACING = 25;
	private static final double CUTSCENE_PROMPT_WIDTH = 1000;
	private static final double CUTSCENE_PROMPT_HEIGHT = 700;
	private static final String NEXT_BUTTON_PROMPT = "Next";
	private static final String PREV_BUTTON_PROMPT = "Previous";
	private static final String ADD_PANEL_BUTTON_PROMPT = "AddPanel";
	private static final String REMOVE_PANEL_BUTTON_PROMPT = "RemovePanel";
	private static final String SAVE_BUTTON_PROMPT = "Save";

	private String currentFontType;
	private int currentFontSize;

	private ArrayList<Pane> paneList;
	private Button nextButton;
	private Button prevButton;
	private Button addPaneButton;
	private Button removePanelButton;
	private Label currentPane;
	private Label totalPanes;
	private HBox dialoguePreview;
	private int currentPaneIndex;

	private SimpleIntegerProperty current;
	private SimpleIntegerProperty totalPaneCount;

	private Runnable save;
	private ArrayList<TextArea> taList;
	
	private double oldHeight = 0;
	private Supplier<Color> currentBgColor;
	private Image currentBgImage;

	protected CutsceneTextAreaView(Runnable save, Supplier <Color> bgColor) {
		currentBgColor = bgColor;
		taList = new ArrayList<TextArea>();
		paneList = new ArrayList<Pane>();
		dialoguePreview = new HBox();
		this.save = save;
		this.setSpacing(15);

		currentPaneIndex = -1;
		current = new SimpleIntegerProperty(0);
		totalPaneCount = new SimpleIntegerProperty(0);
		
		addPanel();

		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(dialoguePreview, makeToolPanel());

	}
	
	protected List<Pane> getCutsceneSequence() {
		return paneList;
	}
	
	protected void setFont(String family, int size) {
		super.setFont(family, size, taList);
	}
	
	protected void setFontColor(String color) {
		super.setFontColor(color, taList);
	}
	
	protected void setBackgroundColor(Color color) {
		super.setBackgroundColor(color, paneList);
	}
	
	protected void setTextAreaBackgroundColor(Color color) {
		super.setTextAreaBackgroundColor(color, taList);
	}
	
	protected void setBackgroundImage(Image image) {
		currentBgImage = image;
		super.setBackgroundImage(image, paneList);
	}

	@Override
	protected void removePanel() {

		if (paneList.size()>1) {
			System.out.println(paneList.size());
			System.out.println(currentPaneIndex);

			if (currentPaneIndex == paneList.size() - 1) {
				prev();
				paneList.remove(currentPaneIndex+1);
				
			} else {
				next();
				paneList.remove(currentPaneIndex-1);
				currentPaneIndex -= 1;
				current.set(current.get()-1);
			}
			totalPaneCount.set(totalPaneCount.get()-1);
		}
	}

	@Override
	protected void addPanel() {
		paneList.add(currentPaneIndex+1, createPane(CUTSCENE_PROMPT_WIDTH, CUTSCENE_PROMPT_HEIGHT));
		if (currentBgImage != null) {
			setBackgroundImage(currentBgImage);
		} else {
			setBackgroundColor(currentBgColor.get());
		}
		setCurrentPane();
		totalPaneCount.set(totalPaneCount.get()+1);
	}
	
	protected void addTextArea() {
		TextArea ta = createTextArea(25, 25, paneList.get(0).getBackground());
		
		taList.add(ta);
		Pane k = (Pane) dialoguePreview.getChildren().get(0);
		k.getChildren().add(ta);
		
		super.makeDraggableAndResizable(ta);	
        
	}
	
	protected void addImage() {
		
	}

	@Override
	protected void setCurrentPane() {
		currentPaneIndex += 1;
		current.set(current.get()+1);
		if (!paneList.isEmpty()) {
			dialoguePreview.getChildren().clear();
		}
		dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
	}

	@Override
	protected void prev() {
		if (currentPaneIndex > 0) {
			currentPaneIndex -= 1;
			System.out.println(currentPaneIndex);
			current.set(current.get()-1);
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
		}
	}

	@Override
	protected void next() {
		if (currentPaneIndex < paneList.size() - 1) {
			currentPaneIndex += 1;
			System.out.println(currentPaneIndex);
			current.set(current.get()+1);
			dialoguePreview.getChildren().clear();
			dialoguePreview.getChildren().add(paneList.get(currentPaneIndex));
		}
	}

	@Override
	protected HBox makeToolPanel() {
		HBox hb = new HBox(55);
		currentPane = new Label();
		currentPane.textProperty().bind(current.asString());
		Label slash = new Label("/");
		totalPanes = new Label();
		totalPanes.textProperty().bind(totalPaneCount.asString());
		HBox panelCountBox = new HBox();
		panelCountBox.getChildren().addAll(currentPane, slash, totalPanes);
		panelCountBox.setAlignment(Pos.CENTER_RIGHT);
		panelCountBox.setPrefWidth(30);
		hb.getChildren().addAll(makeButtonPanel(), panelCountBox);
		return hb;
	}

	@Override
	protected HBox makeButtonPanel() {
		HBox hb = new HBox(15);
		//hb.setAlignment(Pos.CENTER_LEFT);
		nextButton = makeButton(NEXT_BUTTON_PROMPT, e -> next());
		prevButton = makeButton(PREV_BUTTON_PROMPT, e -> prev());
		addPaneButton = makeButton(ADD_PANEL_BUTTON_PROMPT, e -> this.addPanel());
		removePanelButton = makeButton(REMOVE_PANEL_BUTTON_PROMPT, e -> this.removePanel());
		// change number
		// saveButton = makeButton(SAVE_BUTTON_PROMPT, e -> save(nameTF.getText()));
		hb.getChildren().addAll(prevButton, nextButton, addPaneButton, removePanelButton);
		return hb;
	}

	@Override
	protected Button makeButton(String name, EventHandler<ActionEvent> handler) {
		return super.makeButton(name, handler);
	}

}

