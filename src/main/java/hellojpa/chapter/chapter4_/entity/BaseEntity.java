package hellojpa.chapter.chapter4_.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity { // 추상 클래스로 만들어 상속 받아 쓰는 클래스임을 명시
	
	// LocalDateTime 사용 시 @Temporal 를 붙이지 않아도 알아서 해준다.(java8 이상)
	@Column(name = "created_at", updatable = false) // 만든 날짜는 수정X
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at") // 최근 수정 날짜 기록
	private LocalDateTime updatedAt;
	
	@Column(name = "deleted_at") // soft-delete 구현 시 사용
	private LocalDateTime deletedAt;
	
	@Column(name = "is_deleted") // soft-delete 구현 시 사용
	private Boolean isDeleted;
	
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		this.updatedAt = now;
		this.createdAt = now;
		isDeleted = false;
		deletedAt = null;
	}
}
