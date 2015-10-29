package invariantInspector;

import invariantInspector.logic.invariant.InvariantParser;
import invariantInspector.model.VariableSelected;
import invariantInspector.model.gui.InvariantInspectorModel;
import invariantInspector.model.invariants.Invariant;

import java.io.File;
import java.util.List;

import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InvariantInspectorTabController {
	
	@FXML
	private TableView<Invariant> invariants;

	@FXML
	private TableColumn<Invariant, String> lhs;
	
	@FXML
	private TableColumn<Invariant, String> arrow;
	
	@FXML
	private TableColumn<Invariant, String> rhs;
	
	@FXML
	private ListView<VariableSelected> lhsVariableFilter;
	
	@FXML
	private ListView<VariableSelected> rhsVariableFilter;
	
	private InvariantInspectorModel model = new InvariantInspectorModel();

	private Stage stage;
	
	private FilteredList<Invariant> filteredData;
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		lhsVariableFilter.setItems(model.getLhsVariables());
		rhsVariableFilter.setItems(model.getRhsVariables());
		
		lhsVariableFilter.setCellFactory(CheckBoxListCell.forListView(VariableSelected::selectedProperty));
		rhsVariableFilter.setCellFactory(CheckBoxListCell.forListView(VariableSelected::selectedProperty));
		
		lhs.setCellValueFactory(cellData -> cellData.getValue().inputAsString);
		arrow.setCellValueFactory(cellData -> cellData.getValue().arrow);
		rhs.setCellValueFactory(cellData -> cellData.getValue().outputAsString);
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
        filteredData = new FilteredList<>(model.getInvariants(), p -> true);
        
        lhsVariableFilter.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                   VariableSelected currentItemSelected = lhsVariableFilter.getSelectionModel().getSelectedItem();
                   
                   //System.out.println(currentItemSelected.toString());
                   
                   filteredData.setPredicate(invariant -> {
                       if(invariant.containsVariableName(currentItemSelected.getVariable().name.get())){
                    	   return true;
                       }
                       return false; // Does not match.
                   });
                }
            }
        });
        
        rhsVariableFilter.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {
                if (click.getClickCount() == 2) {
                   VariableSelected currentItemSelected = rhsVariableFilter.getSelectionModel().getSelectedItem();
                   
                   //System.out.println(currentItemSelected.toString());
                   
                   filteredData.setPredicate(invariant -> {
                       if(invariant.containsVariableName(currentItemSelected.getVariable().name.get())){
                    	   return true;
                       }
                       return false; // Does not match.
                   });
                }
            }
        });
        
     // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Invariant> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(invariants.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        invariants.setItems(sortedData);
	}

	public void setPrimaryStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void filter(){
		filteredData.setPredicate(invariant -> {
			if(invariant.isActive(model.getLhsVariables(), model.getRhsVariables())){
				 return true;
			}
            return false; // Does not match.
        });
	}

	public void loadData(File selectedFile) {
		InvariantParser parser = new InvariantParser();
		
		parser.parse(selectedFile, model.getInvariants());
		
		model.loadVariables();
	}
	
	public void loadData(List<Invariant> invariants){
				model.getInvariants().addAll(invariants);
				model.loadVariables();
	}

}
