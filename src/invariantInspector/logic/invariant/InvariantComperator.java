package invariantInspector.logic.invariant;

import invariantInspector.model.invariants.Invariant;

import java.util.List;

public class InvariantComperator {
	public void compare(List<Invariant> toCompare, List<Invariant> golden, List<Invariant> extra, List<Invariant> common) {
	int equal = 0;
	int missing = 0;
	int extraI = 0;
	int completeness = 0;
	
	boolean found = false;
	for (Invariant dataCompare : toCompare) {
		for (Invariant dataGolden : golden) {
			if (dataCompare.isTheSame(dataGolden)) {
				equal++;
				found = true;
			}
		}
		if(!found){
			extra.add(dataCompare);
		}
		else{
			if(common != null){
				common.add(dataCompare);
			}
			found = false;
		}
	}
	
	extraI = toCompare.size() - equal;
	missing = golden.size() - equal;
	
	if(equal == 0){
		completeness = 0;
	}
	else{
		completeness = golden.size() / equal;
	}
	
	System.out.println("Completeness: " + completeness);
	System.out.println("Extra: " + extraI);
	System.out.println("Missing: " + missing);
	
}
}