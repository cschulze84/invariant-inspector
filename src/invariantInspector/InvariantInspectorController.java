package invariantInspector;

import invariantInspector.logic.invariant.InvariantComperator;
import invariantInspector.logic.invariant.InvariantParser;
import invariantInspector.model.invariants.Invariant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class InvariantInspectorController {
	
	@FXML
	Button comparisonButton;
	
	@FXML
	Button inspectionButton;
	
	@FXML
	TabPane tabPane;

	private Stage stage;

	public void setPrimaryStage(Stage stage) {
		this.stage = stage;
	}
	
	@FXML
	public void handleComparisonButton(){
		InvariantParser parser = new InvariantParser();
		
		List<Invariant> invariants1 = new ArrayList<>();
		List<Invariant> invariants2 = new ArrayList<>();
		
		File file1 = new File("C:\\Users\\Christoph\\OneDrive\\newAnalysis\\invariantsPruned_10000.txt");
		File file2 = new File("C:\\Users\\Christoph\\OneDrive\\newAnalysis\\invariantsPruned_2000.txt");
		//File file1 = new File("C:\\Users\\Christoph\\OneDrive\\newAnalysis\\experiment_rear_fogLight_random\\invariantsPruned1.txt");
		//File file2 = new File("C:\\Users\\Christoph\\OneDrive\\newAnalysis\\experiment_rear_fogLight_random\\invariantsPruned2.txt");
		
		parser.parse(file1, invariants1);
		parser.parse(file2, invariants2);
		
		List<Invariant> extra1 = new ArrayList<>();
		List<Invariant> extra2 = new ArrayList<>();
		List<Invariant> common = new ArrayList<>();
		
		InvariantComperator comperator = new InvariantComperator();
		comperator.compare(invariants1, invariants2, extra1, common);
		
		comperator.compare(invariants2, invariants1, extra2, null);
		
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane)loader.load(getClass().getResource("InvariantInspectorTab.fxml").openStream());
			InvariantInspectorTabController controller = (InvariantInspectorTabController) loader.getController();
			controller.setPrimaryStage(stage);
			controller.loadData(extra1);
			
			Tab tab = new Tab("Extra1");
			tab.setContent(root);
			
			tabPane.getTabs().add(tab);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane)loader.load(getClass().getResource("InvariantInspectorTab.fxml").openStream());
			InvariantInspectorTabController controller = (InvariantInspectorTabController) loader.getController();
			controller.setPrimaryStage(stage);
			controller.loadData(extra2);
			
			Tab tab = new Tab("Extra2");
			tab.setContent(root);
			
			tabPane.getTabs().add(tab);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			FXMLLoader loader = new FXMLLoader();
			BorderPane root = (BorderPane)loader.load(getClass().getResource("InvariantInspectorTab.fxml").openStream());
			InvariantInspectorTabController controller = (InvariantInspectorTabController) loader.getController();
			controller.setPrimaryStage(stage);
			controller.loadData(common);
			
			Tab tab = new Tab("Common");
			tab.setContent(root);
			
			tabPane.getTabs().add(tab);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleInspectionButton(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("C:\\Users\\Christoph\\OneDrive\\newAnalysis"));
		fileChooser.setTitle("Choose Invariant File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Invariant Files", "*.txt"));
		
		File selectedFile = fileChooser.showOpenDialog(stage);
		if (selectedFile != null) {
			try {
				FXMLLoader loader = new FXMLLoader();
				BorderPane root = (BorderPane)loader.load(getClass().getResource("InvariantInspectorTab.fxml").openStream());
				InvariantInspectorTabController controller = (InvariantInspectorTabController) loader.getController();
				controller.setPrimaryStage(stage);
				controller.loadData(selectedFile);
				
				Tab tab = new Tab();
				tab.setContent(root);
				
				tabPane.getTabs().add(tab);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
