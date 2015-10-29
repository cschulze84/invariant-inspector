package invariantInspector.logic.invariant;

import invariantInspector.model.invariants.Invariant;

import java.util.List;

public class InvariantComperator {
	public void compare(List<Invariant> toCompare, List<Invariant> golden, List<Invariant> extra, List<Invariant> common) {
	int equal = 0;
	int missing = 0;
	int extraI = 0;
	double completeness = 0;
	
	boolean found = false;
	for (Invariant dataCompare : toCompare) {
		for (Invariant dataGolden : golden) {
			if (dataCompare.isTheSame(dataGolden)) {
				found = true;
				break;
			}
		}
		if(!found){
			extra.add(dataCompare);
		}
		else{
			equal++;
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
		completeness = (double) equal / (double) golden.size();
	}
	
	System.out.println("Completeness: " + completeness);
	System.out.println("Extra: " + extraI);
	System.out.println("Missing: " + missing);
	
}
}