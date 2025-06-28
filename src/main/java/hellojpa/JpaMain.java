package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 엔티티 매니저를 엔티티 매니저 팩토리에서 꺼내온다.
        EntityManager em = emf.createEntityManager();
        
        //code

        // 사용 후 엔티티 매니저 반환
        em.close();
        // 애플리케이션 종료시 엔티티 매니저 팩토리 종료
        emf.close();
    }
}
