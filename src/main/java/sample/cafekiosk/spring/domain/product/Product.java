package sample.cafekiosk.spring.domain.product;

import lombok.*;
import sample.cafekiosk.spring.domain.BaseEntity;

import javax.persistence.*;

@Builder
@Getter
//NoArgsConstructor : 파라미터 없는 생성자 자동생성 , access 키워드를 통해 생성자의 접근 수준을 지정 PROTECTED는 생성자가 PROTECTED로 되는것
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String name;
    private int price;
}
