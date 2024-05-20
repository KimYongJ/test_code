package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.product.ProductService;
import sample.cafekiosk.spring.api.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class) // SpringBootTest어노테이션은 전체 빈을 띄우지만, ProductController만 테스트하기 위해 WebMvcTest 어노테이션을 사용합니다. 이 어노테이션은 테스트할 컨트롤러만 로드하기 때문에 가벼운 테스트를 수행할 수 있습니다.
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc는 스프링 애플리케이션의 실제 웹 서버를 기동하지 않고, 애플리케이션의 웹 계층(컨트롤러, 필터 등)을 테스트할 수 있도록 하는 가상의 MVC 환경을 제공합니다.

    @Autowired
    private ObjectMapper objectMapper; // 직렬화 역직렬화를 하기 위해 사용

    @MockBean // 컨테이너에 목키토로만든 목 객체를 넣어주는 역할
    private ProductService productService;// ProductService를 목(Mock) 객체로 생성하고 컨테이너에 주입합니다. 실제 서비스 대신 목 객체를 사용하여 컨트롤러만 테스트할 수 있습니다.

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception{
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
        )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isOk() // MockMvcResultMatchers.status().isOk(): 응답 상태 코드가 200 OK일 것으로 예상합니다.
                );
    }

    @DisplayName("신규 상품을 등록시 상품 타입은 필수입니다")
    @Test
    void createProductWithoutType() throws Exception{
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록시 상품 판매상태는 필수입니다.")
    @Test
    void createProductWithoutsellingStatus() throws Exception{
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 판매상태는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @DisplayName("신규 상품을 등록시 상품 이름은 필수입니다.")
    @Test
    void createProductWithoutname() throws Exception{
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .price(4000)
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @DisplayName("신규 상품을 등록시 상품 가격은 양수여야 합니다.")
    @Test
    void createProductWithoutPrice() throws Exception{
        // Given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(0)
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isBadRequest()
                )
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 가격은 양수여야 합니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("판매 상품을 조회한다.")
    @Test
    void getSellingProducts() throws Exception{
        // Given When Then
        List<ProductResponse> result = List.of();
        when(productService.getSellingProducts()).thenReturn(result);
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        get("/api/v1/products/selling")// MockMvcRequestBuilders.get: get 요청을 보냅니다.
//                                .queryParam("name","김상가") // 쿼리파람 넣는 법
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isOk() // MockMvcResultMatchers.status().isOk(): 응답 상태 코드가 200 OK일 것으로 예상합니다.
                )
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray());
    }


}