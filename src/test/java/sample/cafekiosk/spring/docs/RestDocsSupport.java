package sample.cafekiosk.spring.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith(RestDocumentationExtension.class) // RestDoc에 대한 확장을 주입해주어야 함
//@SpringBootTest // MockMvcBuilders.webAppContextSetup 로 생성시 스프링을 띄워야 하기 때문에 springboottest어노테이션이필요함
public abstract class RestDocsSupport { // RestDoc에 대한 설정을 할 수 있는 상위 클래스
    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper = new ObjectMapper();
//    @BeforeEach // 스프링을 띄울 때 설정
//    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider provider){
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(provider))
//                .build();
//    }
    @BeforeEach
    void setUp(RestDocumentationContextProvider provider){
        this.mockMvc = MockMvcBuilders.standaloneSetup(initController()) // 문서로 만들 클래스를 넣어준다.
                .apply(documentationConfiguration(provider))
                .build();
    }
    protected abstract Object initController(); // 자식 클래스에서 문서로 만들 클래스를 지정 한다
}