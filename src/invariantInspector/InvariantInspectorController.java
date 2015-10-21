package invariantInspector;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

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
