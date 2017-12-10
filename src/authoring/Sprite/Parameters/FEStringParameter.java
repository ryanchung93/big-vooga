package authoring.Sprite.Parameters;

import authoring.Sprite.Parameters.SpriteParameterI;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class FEStringParameter extends FEParameter {
	SpriteParameterI myParam;
	FEParameterName myName;
	TextArea myValue;
	
	protected FEStringParameter(SpriteParameterI BEParam) {
		myParam = BEParam;
		BorderStroke border = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT);
		myName = new FEParameterName(myParam.getName());
		myName.setBorder(new Border(border));
		myValue = new TextArea(myParam.getValue().toString());
		myValue.setPrefWidth(170);
		myValue.setBorder(new Border(border));
		this.getChildren().addAll(myName, myValue);
		this.setMaxHeight(3);
		this.setSpacing(3);
		
		handleValueChange();
	}
	
	private void handleString() {
		myValue.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				myParam.checkError(newValue);
			} catch (Exception e) {
				displayErrorDialog(newValue, e);
				newValue = "";	
			}
			myValue.setText(newValue);
		});
	}
	
	private void displayErrorDialog(String value, Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(e.getMessage());
		alert.setContentText("Please enter value for: " + myName.getText());

		alert.showAndWait();
//		TextInputDialog dialog = new TextInputDialog(value);
//		dialog.setTitle("Error");
//		dialog.setHeaderText(e.getMessage());
//		dialog.setContentText("Please enter value for: " + myName.getText());
//		dialog.initStyle(StageStyle.UTILITY);
//		
//		Optional<String> result = dialog.showAndWait();
//		result.ifPresent(stringInput -> {
//			try {
//				myParam.checkError(stringInput);
//			} catch (Exception e1) {
//				// if error here, do nothing
//			}
//			myValue.setText(stringInput);
//		});
	}
	
	protected void handleValueChange() {
		handleString();
	}
	
	public void updateParameter() {
		myParam.update(myName.getText(), myValue.getText());
	}
}
