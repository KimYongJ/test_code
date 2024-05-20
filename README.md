## **[ Unit Test ]**

- **단위 테스트**
  - **작은** 코드( class , Method ) 단위를 **독립적**으로 검증하는 테스트이고 검증 속도가 빠르고 안정적입니다.
- **JUnit 5**
  - 단위 테스트를 위한 프레임워크 ( 5는 버전이다 3,4,5등.. )
  - **공식 사이트 :** [https://junit.org/junit5/docs/current/user-guide/](https://junit.org/junit5/docs/current/user-guide/)
- **AssertJ**
  - 라이브러리로 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
  - JUnit API보다 풍부한 API와 메서드 체이닝( 점(.)을 찍어 검증 후 또 이어서 할 수 있게 해주는 ) 지원
  - [https://joel-costigliola.github.io/assertj/](https://joel-costigliola.github.io/assertj/)
  - [https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html](https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html)

<br/> 

## **[ 테스트 케이스 세분화 ]**

- **경계 값 테스트 :** 범위(이상, 이하, 초과, 미만), 특정 구간, 날짜등의 경계 값으로 테스트 케이스를 작성합니다.
- **해피 케이스, 예외 케이스 작성**

<br/> 

## **[ TDD ( Test Driven Development ) ]**

- **프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론**
  - **구현순서 :** 테스트 코드 → 실제 코드
  - **레드 그린 리팩토링 :** 실패하는 테스트 작성(빨간색) → 최대한빨리 테스트 통과 되도록 코딩(그린) → 리팩토링(구현 코드 개선 테스트 통과 유지)
- **선 구현 후 테스트 작성시 문제**
  - 테스트 자체의 누락 가능성
  - 특정 테스트(해피) 케이스만 검증할 가능성
  - 잘못된 구현을 다소 늦게 발견할 가능성
- **선 테스트 작성 후 기능 구현시 장점**
  - 유연하며 유지 보수가 쉬운, 테스트 가능한 코드로 구현할 수 있게 합니다.
  - 쉽게 발견하기 어려운 Edge 케이스를 놓치지 않게 해줍니다.
  - 구현에 대한 빠른 피드백을 받을 수 있습니다.
  - 과감한 리팩토링이 가능해집니다.

<br/> 

## [ BDD ( Behavior Driven Development ) / given , when , then ]

- TDD에서 파생된 개발 방법으로 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 **테스트케이스(TC) 자체에** 집중하여 테스트 하는 것 입니다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장합니다.
- **BDD 단계**
  - **Given :** 시나리오 진행에 필요한 모든 준비 과정 ( 객체, 값, 상태 등 )
  - **When :** 시나리오 행동 진행
  - **Then :** 시나리오 진행에 대한 결과 명시, 검증

<br/> 

## [ DisplayName 작성 ]

- **메서드 자체의 관점 보다, 도메인 정책 관점으로 작성 해야 합니다.**
- **Given / When / Then 방식 기술**
- **테스트의 현상을 중점으로 기술하지 말고 Given / When / Then 방식으로 작성 합니다. ( A이면 B이다 or A이면 B가 아니고 C다 )**
  - **음료 1개를 추가함면 주문 목록에 담긴다.(**테스트 행위에 대한 결과까지 입력)
  - **영업 시작 시간 이전에는 주문을 생성할 수 없다.**( 메서드 자체의 관점보다 도메인 정책 관점으로 한층 더 추상화된 내용 작성 )

<br/> 

## [ Transactional 작성 팁 ]

- **트랜젝션 어노테이션 사용시 전체 클래스에는 readOnly=true를 하고, 별도 수정 작업이 있는 함수만 트랜젝션 어노테이션을 따로 넣는 것이 좋습니다. 이유는 아래와 같습니다.**
- **성능 최적화**
  - **리소스 사용 감소**: **readOnly=true**로 설정된 트랜잭션에서는 데이터 수정이 없으므로, JPA 같은 ORM 도구는 변경 감지(dirty checking)나 스냅샷 관리 같은 추가적인 작업을 수행할 필요가 없어집니다. 이는 메모리 사용량을 줄이고, 전반적인 트랜잭션 처리 속도를 향상시킵니다.
  - **데이터베이스 최적화**: 대부분의 데이터베이스 시스템은 읽기 전용 쿼리의 최적화에 효과적인 메커니즘을 가지고 있습니다. **readOnly=true**를 사용하면 데이터베이스가 이를 인식하고 더 효율적으로 쿼리를 처리합니다.
- **명확한 의도 표현**
  - 코드에서 **readOnly=true**를 명시적으로 사용함으로써, 해당 서비스나 메서드가 데이터를 변경하지 않고 단지 읽기만을 수행한다는 것을 분명하게 표현합니다.
- **CQRS (Command Query Responsibility Segregation) 원칙의 적용**
  - **명령과 조회의 분리**: CQRS 패턴은 시스템을 명령(쓰기 작업)과 조회(읽기 작업)로 분리하도록 권장합니다. 이 패턴을 적용하면 시스템의 복잡성을 관리하기 쉬워지고, 성능과 확장성이 향상될 수 있습니다. 읽기 작업의 빈도가 높은 대부분의 애플리케이션에서 특히 유용합니다.
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/main/java/sample/cafekiosk/spring/api/product/ProductService.java)

<br/> 

## [ Repository 테스트 ]

- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/domain/product/ProductRepositoryTest.java)
- **deleteAllInBatch, deleteAll 차이**
  - 각 테스크 메소드가 끝난 후 Repository 클린을 위해 AfterEach 어노테이션을 사용해 deleteAll과 deleteAllInBatch를 사용할 수 있습니다.
  - **deleteAllInBatch :** 테이블을 지울 때 외래키 조건 등 영향을 받기 때문에 삭제할 레포지토리 순서를 잘 계산해야 합니다.
  - **deleteAll :** select로 해당 테이블을 불러 온 후 하나씩 데이터를 지웁니다. 그렇기에 쿼리가 더 많이 실행 됩니다. deleteAllInBatch 어노테이션 보다는 삭제할 레포지토리 순서를 신경쓰지 않을 수 있습니다. (순서가 아주 상관없지는 않고, 외래키를 찾을 수 있다면 순서에 상관없이 select 후 지울 수 있습니다.)

<br/> 

## [ 비지니스 계층 (Service) 테스트 ]

- 비지니스 계층 테스트 주의사항
  - 하나의 함수 테스트시는 문제 없지만 테스트 클래스 전체를 테스트할 때 레포지토리에 저장한 내용이 다른 테스트 함수에 영향을 줍니다. 이를 해결하기 위해 AfterEach 어노테이션을 사용합니다.
  - **@AfterEach**는 JUnit 5에서 제공하는 어노테이션으로, 테스트 메소드가 실행된 후에 매번 실행되어야 하는 메소드를 지정할 때 사용합니다. 이는 각 테스트가 다른 테스트에 영향을 주지 않도록 환경을 초기화하거나 정리하는 용도로 쓰입니다. 테스트 간의 독립성을 보장하여 어떤 테스트가 실패하거나 예외 상황에서도 다른 테스트가 올바르게 실행될 수 있도록 합니다. 메소드이름을 **tearDown** 으로하여 가독성을 높일 수 있습니다.
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/api/service/order/OrderServiceTest.java)

<br/> 

## [ 컨트롤러 테스트 ]

- **Presentation계층 테스트시 서비스 레이어 이하로는 모킹 처리하여 테스트 합니다.**
- **Mock 개념 :** 컨틀롤러 테스트시 서비스 계층이나, 레포지토리는 잘 작동한다는 것을 가정하고 MockMvc라는 스프링에서 제공하는 테스트 프레임워크를 사용합니다.
- **MockMvc :** Mock(가짜) 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 테스트 프레임워크
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/api/controller/product/ProductControllerTest.java)

<br/> 

## [ Mock에 대한 설명 ]

- 여러 복잡한 서비스를 테스트할 때 Mock을 활용할 수 있습니다. 예를 들어 메일전송 서비스에서 메일을 항상 보낼 순 없기에 메일을 보내는 함수(ex Mailclient)를 Mock처리 할 수 있습니다 .그러나, Mock처리를 하면 해당 MailClient 함수의 반환 값이 없을 것이기 때문에 행위를 지정해 주어야 합니다. 이 때 사용 하는것이 Mockito 입니다. 이렇게 지정하는 것을 스터빙(stubbing) 이라 합니다.
- **Mockito._when_(mailSendClient.sendEmail(_any_(String.class), _any_(String.class),_any_(String.class),_any_(String.class))).thenReturn(true);**
  - **Mockito의 when메서드를 사용해 모킹처리한 함수를 넣는다. 이 때 파라미터는 any 함수를 사용해 매개변수의 클래스를 넣어주고, thenReturn 함수를 사용해 반환 값을 정해준다.**
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/api/service/mail/MailServiceTest.java)

<br/> 

## [ BDDMockito ]

- **Mockito를 상속받은 래핑 클래스로써, 모든 동작이 Mockito와 동일하나 BDD스타일로 커스텀한 것입니다.**
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/api/service/mail/MailServiceTest.java)

<br/> 

## [ 테스트 사용시 BeforeAll , BeforeEach 사용은 지양 ]

- Given 데이터를 BeforeEach 나 BeforeAll 어노테이션을 사용한 함수에 미리 작성해 놓을 경우, 하나의 테스트 클래스가 그 데이터에 종속되기 때문에 사용은 가급적 지양 하는 것이 좋습니다.

<br/> 

## [ ParameterizedTest ]

- 값이나 환경에 대한 데이터들을 바꿔가면서 여러번 테스트하고 싶을 때 일반 Test어노테이션이 아니라 ParameterizedTest 어노테이션을 사용합니다.
- **CsvSource 어노테이션 :** comma-separated values의 약자로 몇 가지 필드를 콤마(,)로 구분한 텍스트 데이터 파일로 해당 어노테이션에 값을 넣어 테스트합니다.
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/domain/product/ProductTypeTest.java)

<br/> 

## [ DynamicTest ]

- 단계 별로 행위 검증을 수행하고 싶을 때 사용, 일반 테스트 어노테이션이 아닌 TestFactory 어노테이션을 사용합니다. 리턴 값으로 Collection을 반환합니다.
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/domain/stock/StockDynamicTest.java)

<br/> 

## [ 테스트 환경 통합하기 ]

- 테스트 환경을 통합하지 않고 전체 테스트를 진행하면 시간이 오래 거릴 수 있습니다. 그 이유는 테스트 진행시 각 클래스마다 스프링 부트를 별도로 띄워 실행하기 때문입니다. 이를 해결하기 위해 Support 클래스를 만들고 다른 클래스들이 해당 클래스를 상속 받아 사용하도록 할 수 있습니다.
- [코드 링크 1](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/api/controller/ControllerTestSupport.java)
- [코드 링크 2](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/IntegrationTestSupport.java)

<br/> 

## [ 학습 테스트 작성 예시 ]

- 잘 모르는 기능이나 라이브러리, 프레임워크를 학습하기 위해 작성하는 테스트 이며 Guava를 학습하는 예시 입니다.
- [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/learning/GuavaLearningTest.java)

<br/> 

## [ REST Docs ]

- REST Docs는 API를 문서화 하고 싶을 때 사용하는 API 문서 자동화 도구입니다. 기본적으로 AsciiDoc을 사용해 문서를 작성합니다.
- **장점**
  - 테스트를 통과해야 문서가 만들어짐(신뢰도가 높다)
  - 프로덕션 코드에 비침투적
- **단점**
  - 코드 양이 많고, 설정이 어려움
- [https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/](https://docs.spring.io/spring-restdocs/docs/current/reference/htmlsingle/)
- [https://asciidoctor.org/](https://asciidoctor.org/)
- IDE Marketplace에서 AssciiDoc 검색 후 아스키독 미리보기 설치를 해놓는 것이 좋습니다.
- **의존성 주입 :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/build.gradle)
- **RestDocs 기본 설정 클래스 생성 :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/docs/RestDocsSupport.java)
- **RestDocs 구현 클래스 :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/test/java/sample/cafekiosk/spring/docs/product/ProductControllerDocsTest.java)
- **테스트 코드를 작성 후 실행하면 설정한 build경로에 adoc파일들이 생성 :** [코드 링크](https://github.com/KimYongJ/test_code/tree/main/build/generated-snippets/product-create-%EC%98%88%EC%8B%9C)
- **src폴더 → docs폴더 → asciidoc 폴더 → index.adoc파일 생성 → 그 안에 해당 문법에 맞게 작성합니다 :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/docs/asciidoc/index.adoc?plain=1)
- **그래들의 Tasks → build → build 클릭시 빌드( or Tasks →documentation→asciidoctor) , 빌드 후 html 파일이 생성됩니다 :** [파일 링크](https://github.com/KimYongJ/test_code/blob/main/build/docs/asciidoc/index.html)

<br/> 

## [ REST Docs 생성시 Optional한 데이터 표시법 ]

- API에서 특정 값은 필수이고, 다른 것은 아닌 것이 있을 수 있습니다. 이 때 RestDoc 생성시 커스텀한 컬럼을 추가할 수 있습니다.
- **test디렉토리안에 resources 디렉토리 → org → springframework → restdocs → templates 까지 만든 후 response, request의 템플릿을 만들 수 있습니다**
  - [작성 링크 1](https://github.com/KimYongJ/test_code/blob/main/src/test/resources/org/springframework/restdocs/templates/request-fields.snippet)
  - [작성 링크 2](https://github.com/KimYongJ/test_code/blob/main/src/test/resources/org/springframework/restdocs/templates/response-fields.snippet)

<br/> 

## [ 여러개의 REST Docs 합치는 방법 ]

- **메인 index.adoc에서 include 명령어로 가능합니다. :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/src/docs/asciidoc/index.adoc?plain=1)
- 그러나 위와 같이만 하면 미리보기에서만 보이고 실제 빌드시 나오지 않습니다. **build.gradle 설정에서 asciidoctor에 아래 내용을 추가해야 합니다.**

```jsx
	sources{ // 특정 파일만 html로 만든다.
		include("**/index.adoc")
	}
	baseDirFollowsSourceFile() // 다른 adoc 파일을 include 할 때 경로를 baseDir로 맞춘다.
```

- **그래들 링크 :** [코드 링크](https://github.com/KimYongJ/test_code/blob/main/build.gradle)

<br/> 

## [ 실제 배포시 REST Docs 보는 방법 ]

- **docs의 index.html을 만든 곳의 소스 경로를 그대로 타이핑하면 됩니다.**
- **Local TEST 예시 :** [http://localhost:8080/docs/index.html](http://localhost:8080/docs/index.html#_response_fields)
