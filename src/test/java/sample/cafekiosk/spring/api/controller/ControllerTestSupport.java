package sample.cafekiosk.spring.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.OrderController;


import sample.cafekiosk.spring.api.controller.product.ProductControllerTest;
import sample.cafekiosk.spring.api.product.ProductService;
import sample.cafekiosk.spring.api.service.order.OrderService;

@WebMvcTest(controllers = {
        OrderController.class
      //  , ProductControllerTest.class
})
public abstract class ControllerTestSupport {
    @MockBean
    protected OrderService orderService;
    @Autowired
    protected MockMvc mockMvc; // MockMvc는 스프링 애플리케이션의 실제 웹 서버를 기동하지 않고, 애플리케이션의 웹 계층(컨트롤러, 필터 등)을 테스트할 수 있도록 하는 가상의 MVC 환경을 제공합니다.
    @Autowired
    protected ObjectMapper objectMapper; // 직렬화 역직렬화를 하기 위해 사용
    @MockBean // 컨테이너에 목키토로만든 목 객체를 넣어주는 역할
    protected ProductService productService;
}
