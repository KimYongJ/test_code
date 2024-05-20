package sample.cafekiosk.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.client.mail.MailSendClient;
@Transactional
@ActiveProfiles("test")//yml의 프로파일 명시
@SpringBootTest // 테스트를 위해 제공하는 어노테이션
public abstract class IntegrationTestSupport {
//    @MockBean
//    protected MailSendClient mailSendClient; // MockBean처리 한 것들을 모아서 상위에서 생성할 때 protected로 하여 자식 클래스가 사용할 수 있게한다. 그래야 클래스마다 Spring이 뜨는걸 막음
}
