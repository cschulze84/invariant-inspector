package invariantInspector.logic.invariant;

import invariantInspector.model.invariants.Invariant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.collections.ObservableList;

public class InvariantParser {
	public void parse(File selectedFile, ObservableList<Invariant> list){
		try {
			BufferedReader br = new BufferedReader(new FileReader(selectedFile));
			for(String line; (line = br.readLine()) != null; ) {
				line = line.trim();
				if(line.isEmpty()){
					continue;
				}
				
				Invariant dataPoint = Invariant.parse(line);
				list.add(dataPoint);
				//System.out.println(dataPoint);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void parse(File selectedFile, List<Invariant> list){
		try {
			BufferedReader br = new BufferedReader(new FileReader(selectedFile));
			for(String line; (line = br.readLine()) != null; ) {
				line = line.trim();
				if(line.isEmpty()){
					continue;
				}
				
				Invariant dataPoint = Invariant.parse(line);
				list.add(dataPoint);
				//System.out.println(dataPoint);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
