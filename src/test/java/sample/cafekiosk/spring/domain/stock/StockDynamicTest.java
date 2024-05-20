package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StockDynamicTest {

    @DisplayName("재고 차감 시나리오")
    @TestFactory
    Collection<DynamicTest> stockDeductionDynamicTest(){
        // Given
        Stock stock = Stock.create("001",1);
        return List.of(
                DynamicTest.dynamicTest("재고를 주어진 개수 만큼 차감할 수 있다.",()->{
                    // given
                    int quantity = 1;
                    // when
                    stock.deductQuantity(quantity);
                    // then
                    assertThat(stock.getQuantity()).isZero();
                }),
                DynamicTest.dynamicTest("재고 보다 많은 수의 수량을 차감 시도하는 경우 예외가 발생한다.",()->{
                    // given
                    int quantity = 1;
                    // when // then
                    assertThatThrownBy(()->stock.deductQuantity(quantity))
                            .isInstanceOf(IllegalArgumentException.class)
                            .hasMessage("차감할 재고 수량이 없습니다.");

                })
        );
    }

}
