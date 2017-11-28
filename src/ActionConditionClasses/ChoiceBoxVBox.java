package ActionConditionClasses;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class ChoiceBoxVBox<T> extends VBoxList<T> implements ChoiceBoxVBoxI<T> {
	
	private Label topLabel;
	private ChoiceBox<T> choiceBox;

	public ChoiceBoxVBox(String label,List<T> options) {
		super(options);
		topLabel = new Label(label);
		choiceBox = new ChoiceBox<T>();
		setNewOptions(options);
		getChildren().addAll(topLabel,choiceBox);
	}

	@Override
	public Object getCurrentValue() {
		return choiceBox.getValue();
	}

	@Override
	public void setValue(Object newValue) {
		choiceBox.setValue((T) newValue);
	}

	@Override
	public void changeLabel(String newLabel) {
		Label tempLabel = new Label(newLabel);
		topLabel = tempLabel;
	}

	@Override
	public void realizeNewOptions(ObservableList<T> newOptions) {
		choiceBox.setItems(newOptions);
	}

}
