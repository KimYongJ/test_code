package sample.cafekiosk.spring.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 애노테이션은 이 클래스가 데이터베이스 테이블과 직접 매핑되지 않고, 상속받은 하위 클래스에 공통 매핑 정보를 제공한다는 것을 지정합니다. 즉, BaseEntity 클래스의 필드들이 하위 엔티티 테이블에 포함되도록 할 때 사용합니다.
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate // 엔티티가 생성되어 저장될 때의 시간을 자동으로 설정합니다. 이 필드는 처음 엔티티가 저장될 때 한 번만 값이 설정되며, 이후에는 변경되지 않습니다.
    private LocalDateTime createdDateTime;

    @LastModifiedDate // 엔티티가 수정될 때마다 그 시간을 자동으로 갱신합니다. 엔티티의 내용이 변경되어 데이터베이스에 다시 저장될 때마다 이 필드의 시간이 업데이트됩니다.
    private LocalDateTime modifiedDateTime;
}
