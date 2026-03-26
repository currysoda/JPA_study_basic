package hellojpa.dialect;

import org.hibernate.boot.model.FunctionContributions;
import org.hibernate.boot.model.FunctionContributor;
import org.hibernate.type.StandardBasicTypes;

public class MyH2CustomFunction implements FunctionContributor {
	
	@Override
	public void contributeFunctions(FunctionContributions functionContributions) {
		// Hibernate 6+ 사용자 정의 함수 등록 방식
		// registerFunction() 은 Hibernate 5 방식 → FunctionContributor 사용
		functionContributions.getFunctionRegistry()
		                     .registerNamed(
			                     "group_concat", // group_concat 은 이미 H2 에 내장된 함수 이런 함수가 있다고 알려줌
			                     functionContributions.getTypeConfiguration()
			                                          .getBasicTypeRegistry()
			                                          .resolve(StandardBasicTypes.STRING)
		                                   );
	}
}
