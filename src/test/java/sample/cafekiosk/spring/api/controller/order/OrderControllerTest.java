package sample.cafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.ControllerTestSupport;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.OrderService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest extends ControllerTestSupport {
    // Support 클래스를 상속받아 사용하므로 주석처리
//    @MockBean
//    private OrderService orderService;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;

    @DisplayName("신규 주문을 등록한다.")
    @Test
    void createOrder() throws Exception{
        // Given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001"))
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/orders/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isOk() // MockMvcResultMatchers.status().isOk(): 응답 상태 코드가 200 OK일 것으로 예상합니다.
                )
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
    @DisplayName("신규 주문을 등록할 때 상품번호는 1개 이상이어야 한다.")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception{
        // Given
        OrderCreateRequest request = OrderCreateRequest.builder()
                .build();
        // When Then
        // mockMvc.perform 메서드로 요청을 시뮬레이션합니다.
        // MockMvcRequestBuilders로 요청을 생성하고, MockMvcResultMatchers로 결과를 검증합니다.
        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/orders/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isBadRequest() // MockMvcResultMatchers.status().isOk(): 응답 상태 코드가 200 OK일 것으로 예상합니다.
                )
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품 번호 리스트는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}