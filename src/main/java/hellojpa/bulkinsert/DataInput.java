package hellojpa.bulkinsert;

import hellojpa.entity.Member;
import jakarta.persistence.EntityManager;

public class DataInput {
	
	public static void bulk(EntityManager em) throws Exception {
		
		Member[] arrMember = new Member[10];
		
		for (int i = 0; i < 10; i++)
		{
			String str = "hello";
			
			arrMember[i] = Member.builder()
			                     .name("hello" + (i + 1))
			                     .build();
			
			em.persist(arrMember[i]);
		}
		
		System.out.println("데이터 입력 완료");
		
	}
}
