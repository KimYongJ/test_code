package sample.cafekiosk.spring.api.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.IntegrationTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

//@SpringBootTest
//@ActiveProfiles("test")
class ProductServiceTest extends IntegrationTestSupport {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void beforeAll(){
        // 테스트 클래스 전체 실행전에 무언가 한번 작업을 하는 함수
    }

    @BeforeEach
    void setUp(){
        // 매 테스트 메소드 전에 동작을 하는 메서드
    }

    @AfterAll
    static void af_terAll(){
        // 모든 테스트가 끝나고 진행할 것
    }

    @AfterEach
    void tearDown(){
        productRepository.deleteAllInBatch();
    }
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    @Test
    void createProduct(){
        // Given
        Product product = createProduct("001",HANDMADE, SELLING,"아메리카노", 4000);
        productRepository.save(product);
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
        // When
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());
        // Then
        assertThat(productResponse)
                .extracting("productNumber","type","sellingStatus","name","price")
                .contains("002",HANDMADE,SELLING,"카푸치노",5000);
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber","type","sellingStatus","name","price")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE,SELLING,"아메리카노",4000),
                        tuple("002",HANDMADE,SELLING,"카푸치노",5000)
                );

    }
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품번호는 001이다. ")
    @Test
    void createProductWhenProductIsEmpty(){
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();
        // When
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());
        // Then
        assertThat(productResponse)
                .extracting("productNumber","type","sellingStatus","name","price")
                .contains("001",HANDMADE,SELLING,"카푸치노",5000);
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber","type","sellingStatus","name","price")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE,SELLING,"카푸치노",5000)
                );
    }
    private Product createProduct(String productNumber, ProductType handmade, ProductSellingStatus productSellingStatus, String name, int price){
        return  Product.builder()
                .productNumber(productNumber)
                .type(handmade)
                .sellingStatus(productSellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}