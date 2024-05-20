package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;
//@ActiveProfiles("test") //yml의 프로파일 명시
//@SpringBootTest // 통합테스트를 위해 제공하는 어노테이션
// @DataJpaTest // 스프링 부트 테스트보다 가볍다 jpa관련 빈들만 주입 후 서버를 띄우기 때문에속도빠르게 띄워짐
class OrderTest extends IntegrationTestSupport {

    @DisplayName("주문 생성시 상품 리스트에서 주문의 총 금액을 계산합니다.")
    @Test
    void calculateTotalPrice(){
        // Given

        List<Product> products = List.of(
                createProduct("001",1000),
                createProduct("002",2000)
                );
        // When
        Order order = Order.create(products, LocalDateTime.now());
        // Then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }


    @Disabled
    @DisplayName("주문 생성시 주문 상태는 INIT이다.")
    @Test
    void init(){
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001",1000),
                createProduct("002",2000)
        );
        // When
        Order order = Order.create(products, now);
        // Then
        assertThat(order.getOrderStatus()).isEqualTo(INIT);
        assertThat(order.getRegisteredDateTime()).isEqualTo(now);
        assertThat(order.getRegisteredDateTime()).isNotEqualTo(LocalDateTime.now());
    }

    @DisplayName("주문 생성시 주문 등록 시간을 기록한다.")
    @Test
    void registeredDateTime(){
        // Given
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001",1000),
                createProduct("002",2000)
        );
        // When
        Order order = Order.create(products, now);
        // Then
        assertThat(order.getOrderStatus()).isEqualTo(INIT);
        assertThat(order.getRegisteredDateTime()).isEqualTo(now);
    }



    private Product createProduct(String productNumber, int price){
        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴이름")
                .build();
    }

}