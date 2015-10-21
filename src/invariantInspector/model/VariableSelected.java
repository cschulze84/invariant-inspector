package invariantInspector.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VariableSelected {
	public BooleanProperty selected;
	private Variable variable;
	
	public VariableSelected(Variable variable) {
		selected = new SimpleBooleanProperty(true);
		this.variable = variable;
	}
	
    public BooleanProperty selectedProperty() {
        return selected;
    }
    
    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
	
	public Variable getVariable() {
		return variable;
	}
	
	@Override
	public String toString() {
		return variable.name.get();
	}
}
