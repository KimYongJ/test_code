package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType(){
        // Given
        ProductType givenType1 = ProductType.HANDMADE;
        ProductType givenType2 = ProductType.BAKERY;
        ProductType givenType3 = ProductType.BOTTLE;
        // When
        boolean result1 = ProductType.containsStockType(givenType1);
        boolean result2 = ProductType.containsStockType(givenType2);
        boolean result3 = ProductType.containsStockType(givenType3);
        // Then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
        assertThat(result3).isTrue();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @CsvSource({"HANDMADE, false", "BOTTLE,true","BAKERY,true"})
    @ParameterizedTest // 일반 Test 어노테이션이 아니라, 값을 주고 싶을 때 사용
    void containsStockType4(ProductType productType, boolean expected){
        // When
        boolean result = ProductType.containsStockType(productType);
        // Then
        assertThat(result).isEqualTo(expected);
    }

//    @DisplayName("")
//    @TestFactory
//    Collection<DynamicTest> dynamicTest(){
//        // 데이터 세팅
//        return List.of(
//                DynamicTest.dynamicTest("",()->{
//                    // 어떤 변화를 줌 1
//                }),
//                DynamicTest.dynamicTest("",()->{
//                    // 어떤 변화를 줌 2
//                })
//        );
//    }



}