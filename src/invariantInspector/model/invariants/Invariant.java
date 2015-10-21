package invariantInspector.model.invariants;

import invariantInspector.model.VariableSelected;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;


public class Invariant implements Comparable<Invariant> {
	private List<InvariantValue> inputs;
	private List<InvariantValue> outputs;
	
	public StringProperty inputAsString = new SimpleStringProperty();
	public StringProperty outputAsString = new SimpleStringProperty();
	public StringProperty arrow = new SimpleStringProperty("->");

	public void setInputs(List<InvariantValue> inputs) {
		this.inputs = inputs;
		inputAsString.set(getListAsString(inputs));
	}

	private String getListAsString(List<InvariantValue> values) {
		String string = new String();
		for (InvariantValue implicationValue : values) {
			string += implicationValue.toString() + " ";
		}
		return string;
	}

	public List<InvariantValue> getInputs() {
		return inputs;
	}

	public void setOutputs(List<InvariantValue> outputs) {
		this.outputs = outputs;
		outputAsString.set(getListAsString(outputs));
	}

	boolean equalRules(Invariant obj) {
		for (InvariantValue value : inputs) {
			if (!obj.containsInputs(value)) {
				return false;
			}
		}

		for (InvariantValue value : outputs) {
			if (value.isNumeric()) {
				if (!obj.containsOutputs(value)) {
					return false;
				}
			} else {
				return obj.equalsOutputs(value);
			}

		}

		return true;

	}
	

	private boolean equalsOutputs(InvariantValue value) {
		for (InvariantValue valueHere : outputs) {
			if (valueHere.equalsComplete(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean containsInputs(InvariantValue value) {
		for (InvariantValue valueHere : inputs) {
			if (valueHere.equals(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean containsOutputs(InvariantValue value) {
		for (InvariantValue valueHere : outputs) {
			if (valueHere.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public String reactisExport(){
		String returnString = new String();
		boolean first = true;
		
		returnString += "!(";
		for (InvariantValue value : inputs) {
			if (first) {
				returnString += value.toString();
				first = false;
			} else {
				returnString += " && " + value.toString();
			}
		}

		returnString += ") || ";
		
		if(outputs.size() > 1){
			returnString += "(";
		}

		first = true;
		for (InvariantValue value : outputs) {
			if (first) {
				returnString += value.toString();
				first = false;
			} else {
				returnString += " && " + value.toString();
			}
		}
		if(outputs.size() > 1){
			returnString += ")";
		}

		return returnString;
	}

	@Override
	public String toString() {
		String returnString = new String();
		boolean first = true;
		for (InvariantValue value : inputs) {
			if (first) {
				returnString += value.toString();
				first = false;
			} else {
				returnString += " & " + value.toString();
			}
		}

		returnString += " -> ";

		first = true;
		for (InvariantValue value : outputs) {
			if (first) {
				returnString += value.toString();
				first = false;
			} else {
				returnString += " & " + value.toString();
			}
		}

		return returnString;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Invariant) {
			return equalRules((Invariant) obj);
		}
		return false;
	}
	
	public boolean isTheSame(Invariant data){
		if(data == null){
			return false;
		}
		if(inputs.size() != data.inputs.size() && outputs.size() != data.outputs.size()){
			return false;
		}
		
		for (InvariantValue inputValueToCompare : inputs) {
			boolean found = false;
			for (InvariantValue value : data.inputs) {
				if(inputValueToCompare.equalsComplete(value)){
					found =  true;
				}
			}
			if(!found){
				return false;
			}
		}
		for (InvariantValue outputValueToCompare : outputs) {
			boolean found = false;
			for (InvariantValue value : data.outputs) {
				if(outputValueToCompare.equalsComplete(value)){
					found =  true;
				}
			}
			if(!found){
				return false;
			}
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		String hashValue = getHashValue();
		return hashValue.hashCode();
	}

	private String getHashValue() {
		String hashString = "";
		for (InvariantValue value : inputs) {
			hashString += " " + value.getName();
		}

		for (InvariantValue value : outputs) {
			if (value.isNumeric()) {
				hashString += " " + value.getName();
			} else {
				hashString += value.toString();
			}
		}
		return hashString;
	}

	public void process(Invariant data) {
		for (InvariantValue value : data.getInputs()) {
			InvariantValue processValue = this.getValueByName(value.getName());
			if (processValue == null) {
				continue;
			}
			processValue.process(value);
		}
		for (InvariantValue value : data.getOutputs()) {
			InvariantValue processValue = this.getValueByName(value.getName());
			if (processValue == null) {
				continue;
			}
			processValue.process(value);
		}

	}

	public List<InvariantValue> getOutputs() {
		return outputs;
	}

	private InvariantValue getValueByName(String name) {
		for (InvariantValue value : inputs) {
			if (value.getName().equals(name)) {
				return value;
			}
		}
		for (InvariantValue value : outputs) {
			if (value.getName().equals(name)) {
				return value;
			}
		}
		return null;
	}

	public String getPartitionString() {
		String returnString = new String();
		boolean first = true;
		for (InvariantValue value : inputs) {
			if (first) {
				returnString += value.getPartitionString();
				first = false;
			} else {
				returnString += " & " + value.getPartitionString();
			}
		}

		returnString += " -> ";

		first = true;
		for (InvariantValue value : outputs) {
			if (first) {
				returnString += value.getPartitionString();
				first = false;
			} else {
				returnString += " & " + value.getPartitionString();
			}
		}

		return returnString;
	}

	@Override
	public int compareTo(Invariant data) {
		for (InvariantValue value : inputs) {
			InvariantValue compareValue = data.getValueByName(value.getName());
			if (value.isNumeric()) {
				int compare = Double.compare(value.getNumeric(),
						compareValue.getNumeric());
				if (compare == 0) {
					continue;
				}
				return compare;

			} else {
				int compare = value.getValue().compareTo(
						compareValue.getValue());
				if (compare == 0) {
					continue;
				}
				return compare;
			}
		}
		return 0;
	}

	public String getPartitionCode() {
		String returnString = new String();
		returnString += "if(!(";
		boolean first = true;
		for (InvariantValue value : inputs) {
			if (first) {
				returnString += value.getCodeStringRange();
				first = false;
			} else {
				returnString += " && " + value.getCodeStringRange();
			}
		}

		returnString += ") || ( ";

		first = true;
		for (InvariantValue value : outputs) {
			if (first) {
				returnString += value.getCodeString();
				first = false;
			} else {
				returnString += " && " + value.getCodeString();
			}
		}
		returnString += ")){\n";
		returnString += "\ti=a;\n";
		returnString += "}\nelse{\n";
		returnString += "\ti=b;\n}";

		return returnString;
	}

	public boolean isSubsetOf(Invariant data) {
		if(data == null){
			return false;
		}
		if(inputs.size() >= data.inputs.size() || outputs.size() != data.outputs.size()){
			return false;
		}
		
		
		for (InvariantValue outputValueToCompare : outputs) {
			boolean found = false;
			for (InvariantValue value : data.outputs) {
				if(outputValueToCompare.equalsComplete(value)){
					found =  true;
				}
			}
			if(!found){
				return false;
			}
		}
		
		int numberOfOperators = inputs.size();
		
		for (InvariantValue inputValueToCompare : inputs) {
			for (InvariantValue value : data.inputs) {
				if(inputValueToCompare.equalsComplete(value)){
					numberOfOperators--;
				}
			}
		}
		
		if(numberOfOperators>0){
			return false;
		}
		
		return true;
	}
	
	public String printReactis(){
		String reactisString = "!(";
		boolean first = true;
		for (InvariantValue value : inputs) {
			if(first){
				first = false;
				reactisString += value.toString();
			}
			else{
				reactisString += " && " + value.toString();
			}
		}
		
		reactisString += ") || (";
		
		for (InvariantValue value : outputs) {
			if(first){
				first = false;
				reactisString += value.toString();
			}
			else{
				reactisString += " && " + value.toString();
			}
		}
		
		reactisString += ")";
		
		return reactisString;
	}

	public static Invariant parse(String line) {
		String[] inOutPutArray = line.split("->");

		if (inOutPutArray.length != 2) {
			return null;
		}
		Invariant data = new Invariant();

		data.setInputs(parseValues(inOutPutArray[0]));

		data.setOutputs(parseValues(inOutPutArray[1]));

		return data;
	}

	private static List<InvariantValue> parseValues(String valuesString) {
		List<InvariantValue> values = new ArrayList<>();
		String[] valueArray = valuesString.split("&");

		for (String valueString : valueArray) {
			valueString = valueString.trim();
			if (valueString.isEmpty()) {
				continue;
			}

			String[] nameValueArray = valueString.split("==");
			if (nameValueArray.length != 2) {
				continue;
			}
			InvariantValue value = new InvariantValue();

			value.setName(nameValueArray[0].trim());
			value.setValue(nameValueArray[1].trim());
			values.add(value);

		}
		return values;
	}

	public boolean containsVariableName(String name) {
		for (InvariantValue invariantValue : inputs) {
			if(invariantValue.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		for (InvariantValue invariantValue : outputs) {
			if(invariantValue.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		
		return false;
	}

	public boolean isActive(ObservableList<VariableSelected> lhsVariables,
			ObservableList<VariableSelected> rhsVariables) {
		
		for (InvariantValue value : inputs) {
			for (VariableSelected variableSelected : lhsVariables) {
				if(value.getName().equalsIgnoreCase(variableSelected.getVariable().name.get())){
					if(!variableSelected.isSelected()){
						return false;
					}
				}
			}
		}
		for (InvariantValue value : outputs) {
			for (VariableSelected variableSelected : rhsVariables) {
				if(value.getName().equalsIgnoreCase(variableSelected.getVariable().name.get())){
					if(!variableSelected.isSelected()){
						return false;
					}
				}
			}
		}
		return true;
	}
}
