package invariantInspector.model.gui;

import java.util.HashSet;
import java.util.Set;

import invariantInspector.model.Variable;
import invariantInspector.model.VariableSelected;
import invariantInspector.model.invariants.InvariantValue;
import invariantInspector.model.invariants.Invariant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class InvariantInspectorModel {
	private ObservableList<Invariant> invariants = FXCollections.observableArrayList();
	
	private ObservableList<VariableSelected> lhsVariables = FXCollections.observableArrayList();
	private ObservableList<VariableSelected> rhsVariables = FXCollections.observableArrayList();
	
	public ObservableList<Invariant> getInvariants() {
		return invariants;
	}
	
	public ObservableList<VariableSelected> getLhsVariables() {
		return lhsVariables;
	}
	
	public ObservableList<VariableSelected> getRhsVariables() {
		return rhsVariables;
	}
	
	public void loadVariables(){
		Set<String> tempsetLHS = new HashSet<>();
		Set<String> tempsetRHS = new HashSet<>();
		for (Invariant invariant : invariants) {
			for (InvariantValue invariantValue : invariant.getInputs()) {
				tempsetLHS.add(invariantValue.getName());
			}
			for (InvariantValue invariantValue : invariant.getOutputs()) {
				tempsetRHS.add(invariantValue.getName());
			}
		}
		
		for (String string : tempsetLHS) {
			VariableSelected variable = new VariableSelected(new Variable(string));
			lhsVariables.add(variable);
		}
		
		for (String string : tempsetRHS) {
			VariableSelected variable = new VariableSelected(new Variable(string));
			rhsVariables.add(variable);
		}
	}
}
