package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.domain.product.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@ActiveProfiles("test")
//@SpringBootTest
class StockRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private StockRepository stockRepository;

    @DisplayName("재고의 수량이 제공된 수량 보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan(){
        // Given
        Stock stock = Stock.create("001", 1);
        int quantity1 = 2;
        int quantity2 = 1;
        // When
        boolean result1 = stock.isQuantityLessThan(quantity1);
        boolean result2 = stock.isQuantityLessThan(quantity2);
        // Then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
    @Test
    void deductQuantity(){
        // Given
        Stock stock = Stock.create("001", 1);
        int quantity = 1;

        // When
        stock.deductQuantity(quantity);

        // Then
        assertThat(stock.getQuantity()).isZero();
    }

    @DisplayName("재고 보다 많은 수량으로 차감시도하는 경우 예외가 발생한다.")
    @Test
    void deductQuantity2(){
        // Given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // When // Then
        assertThatThrownBy(()->stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("차감할 재고 수량이 없습니다.");
    }


    @DisplayName("상품번호 리스트로 재고를 조회한다.")
    @Test
    void findAllByProductNumberIn(){
        // Given
        Stock stock1 = Stock.create("001",1);
        Stock stock2 = Stock.create("002",2);
        Stock stock3 = Stock.create("003",3);
        stockRepository.saveAll(List.of(stock1,stock2,stock3));

        // When
        List<Stock> sotcks = stockRepository.findAllByProductNumberIn(List.of("001","002"));

        // Then
        assertThat(sotcks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001", 1),
                        tuple("002", 2)
                );
    }


}