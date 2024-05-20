package sample.cafekiosk.spring.docs.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import sample.cafekiosk.spring.api.controller.product.ProductController;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.product.ProductService;
import sample.cafekiosk.spring.api.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.product.response.ProductResponse;
import sample.cafekiosk.spring.docs.RestDocsSupport;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);
    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @DisplayName("신규 상품을 등록하는 api")
    @Test
    void createProduct() throws Exception{
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
                .willReturn(ProductResponse.builder()
                        .id(1L)
                        .productNumber("001")
                        .type(HANDMADE)
                        .sellingStatus(SELLING)
                        .name("아메리카노")
                        .price(4000)
                        .build()
                );

        mockMvc.perform( // mockMvc.perform: MockMvc를 사용하여 HTTP 요청을 수행합니다.
                        post("/api/v1/products/new")// MockMvcRequestBuilders.post: POST 요청을 보냅니다. (/api/v1/products/new).
                                .content(objectMapper.writeValueAsString(request)) // .content: 요청 본문에 request 객체를 JSON 형태로 추가합니다.
                                .contentType(MediaType.APPLICATION_JSON) // .contentType: 요청의 콘텐츠 타입을 application/json으로 설정합니다.
                )
                .andDo(print()) // 요청이 어떻게 날아갔는지 확인가능
                .andExpect( // .andExpect: 응답 결과에 대한 예상값을 설정합니다
                        status().isOk() // MockMvcResultMatchers.status().isOk(): 응답 상태 코드가 200 OK일 것으로 예상합니다.
                )

                // 문서를 만들기 위한 체이닝 시작
                .andDo(document("product-create-예시",
                      preprocessRequest(prettyPrint()), // 문서 생성시 JSON파일이 이쁘게 정렬됨
                      preprocessResponse(prettyPrint()),// 문서 생성시 JSON파일이 이쁘게 정렬됨
                      // 어떤 요청을 넣고 어떤 응답을 넣을 것인가 정의 하면됨
                      requestFields(
                              fieldWithPath("type").type(JsonFieldType.STRING)
                                      .description("상품 타입"),
                              fieldWithPath("sellingStatus").type(JsonFieldType.STRING)
                                      .optional()
                                      .description("상품 판매상태"),
                              fieldWithPath("name").type(JsonFieldType.STRING)
                                      .description("상품 이름"),
                              fieldWithPath("price").type(JsonFieldType.NUMBER)
                                      .description("상품 가격")
                      ), // body가 있는 요청과
                      responseFields(

                              fieldWithPath("code").type(JsonFieldType.NUMBER)
                                      .description("코드"),
                              fieldWithPath("status").type(JsonFieldType.STRING)
                                      .description("상태"),
                              fieldWithPath("message").type(JsonFieldType.STRING)
                                      .description("메시지"),
                              fieldWithPath("data").type(JsonFieldType.OBJECT)
                                      .description("응답 데이터"),
                              fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                                      .description("상품 ID"),
                              fieldWithPath("data.productNumber").type(JsonFieldType.STRING)
                                      .description("상품 번호"),
                              fieldWithPath("data.type").type(JsonFieldType.STRING)
                                      .description("상품 타입"),
                              fieldWithPath("data.sellingStatus").type(JsonFieldType.STRING)
                                      .description("상품 판매상태"),
                              fieldWithPath("data.name").type(JsonFieldType.STRING)
                                      .description("상품 이름"),
                              fieldWithPath("data.price").type(JsonFieldType.NUMBER)
                                      .description("상품 가격")
                      ) // 응답이 있기 때문에 넣음
                ));
    }
}
