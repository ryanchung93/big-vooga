package authoring_actionconditions;

import java.util.List;
import java.util.function.Supplier;

import authoring.Sprite.AbstractSpriteObject;
import javafx.collections.ObservableList;

public class ConditionVBox<T> extends ActionConditionVBox<T> implements ConditionVBoxI<T>{
	
	private Supplier<List<AbstractSpriteObject>> supplier;

	public ConditionVBox(Supplier<List<AbstractSpriteObject>> supplier) {
		super();
		this.supplier = supplier;
	}
	
	public ConditionVBox(List<T> rows,Supplier<List<AbstractSpriteObject>> supplier) {
		super(rows);
		this.supplier = supplier;
	}

	@Override
	public void addCondition(ObservableList<Integer> currentActions) {
		ConditionRow conditionRow = new ConditionRow(getRows().size() + 1,currentActions, (ConditionVBox<ConditionRow>) this, supplier);
		addToRows(conditionRow);
		BuildConditionView bcd = new BuildConditionView(this, conditionRow);
	}
	
	@Override
	public void setNewActionOptions(ObservableList<Integer> newActionOptions) {
		getRows().forEach(row -> ((ConditionRow) row).setNewActionCheckBoxVBoxOptions(newActionOptions));
	}
	
	@Override
	public void addActionOption() {
		getRows().forEach(row -> ((ConditionRow) row).addAction());
	}
	
	@Override
	public void removeActionOption(Integer action) {
		getRows().forEach(row -> ((ConditionRow) row).removeAction(action));
	}
	


}
