package sample.cafekiosk.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class) // 테스트 시작시 모키토 사용해 목객체를 만든다는것을 명시해주어야 함
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    @InjectMocks // mailService는 객체 주입을 해야하기 때문에 어노테이션이 다르다.
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail(){
        // Given
        BDDMockito.given(mailSendClient.sendEmail(anyString(),anyString(),anyString(),anyString()))
                        .willReturn(true);
//        Mockito.when(mailSendClient.sendEmail(anyString(),any(String.class),anyString(),any(String.class)))
//                .thenReturn(true);// Spy사용시 Mockito.when사용불가

        // When
        boolean result = mailService.sendMail("", "", "", "");

        // Then
        assertThat(result).isTrue();
        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
    }
}
//@ExtendWith(MockitoExtension.class) // 테스트 시작시 모키토 사용해 목객체를 만든다는것을 명시해주어야 함
//class MailServiceTest {
//
//    @Spy
//    private MailSendClient mailSendClient;
//    @Mock
//    private MailSendHistoryRepository mailSendHistoryRepository;
//
//    @InjectMocks // mailService는 객체 주입을 해야하기 때문에 어노테이션이 다르다.
//    private MailService mailService;
//
//    @DisplayName("메일 전송 테스트")
//    @Test
//    void sendMail(){
//        // Given
////        Mockito.when(mailSendClient.sendEmail(anyString(),any(String.class),anyString(),any(String.class)))
////                .thenReturn(true); Spy사용시 Mockito.when사용불가
//
//        Mockito.doReturn(true)
//                .when(mailSendClient)
//                .sendEmail(anyString(),anyString(),anyString(),anyString());
//        // When
//        boolean result = mailService.sendMail("", "", "", "");
//
//        // Then
//        assertThat(result).isTrue();
//        Mockito.verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
//    }
//}