package hellojpa.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;

public class MyH2CustomFunction implements FunctionContributor {
	
	@Override
	public void contributeFunctions(FunctionContributions functionContributions) {
		
	}
	
}
