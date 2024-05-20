package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverages.Americano;
import sample.cafekiosk.unit.beverages.Beverage;
import sample.cafekiosk.unit.beverages.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CafeKioskRunnerTest {

    @DisplayName("음료 추가시 주문 목록에 개수와 함께 저장되고 저장된 음료 객체를 삭제할 수 있다")
    @Test
    void add_and_remove(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // When
        cafeKiosk.add(americano,1);

        // then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        assertThat(cafeKiosk.getBeverages().get(0).getPrice()).isEqualTo(4000);

        // When
        cafeKiosk.remove(americano);
        // then
        assertThat(cafeKiosk.getBeverages()).hasSize(0);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }


    @DisplayName("음료 추가시 주문 목록에 개수가 같이 저장된다.")
    @Test
    void addServeralBeverages(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when
        cafeKiosk.add(americano,2);

        // then
        assertThat(cafeKiosk.getBeverages()).hasSize(2);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @DisplayName("음료는 1잔이상만 주문 할 수 있다.")
    @Test
    void addZeroBeverages(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        // when Then
        assertThatThrownBy(()-> cafeKiosk.add(americano,0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");

    }
    @DisplayName("음료 추가시 주문목록에 담기고, 주문을 한꺼번에 삭제할 수 있다")
    @Test
    void clear(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte         = new Latte();
        // when
        cafeKiosk.add(americano,1);
        cafeKiosk.add(latte,1);
        // then
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        // when
        cafeKiosk.clear();
        // then
        assertThat(cafeKiosk.getBeverages()).hasSize(0);
    }

    @DisplayName("음료 추가해 주문목록에 저장 하고, 총 가격을 출력할 수 있다.")
    @Test
    void calculateTotalPrice(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage beverage = new Americano();
        cafeKiosk.add(beverage,1);
        beverage = new Latte();
        cafeKiosk.add(beverage,1);

        // when
        int totalPrice = cafeKiosk.calculateTotalPrice();


        // then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @DisplayName("오더 생성시 마감, 오픈 시간 외에는 주문을 생성할 수 없다.")
    @Test
    void createOrder(){
        // Given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Beverage beverage = new Americano();
        cafeKiosk.add(beverage,1);

        // when
        Order order = cafeKiosk.cerateOrder(LocalDateTime.of(2024,4,23,15,00));
        // then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getPrice()).isEqualTo(4000);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");

        assertThatThrownBy(()->cafeKiosk.cerateOrder(LocalDateTime.of(2024,4,23,23,00)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");
    }

}