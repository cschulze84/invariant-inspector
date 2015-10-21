package invariantInspector.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Variable {
	public StringProperty name = new SimpleStringProperty();
	
	public Variable(String name) {
		this.name.set(name);
	}

	@Override
	public boolean equals(Object variable) {
		if(variable instanceof Variable){
			return name.get().equalsIgnoreCase(((Variable) variable).name.get());
		}
		else{
			return false;
		}
		
	}
}
