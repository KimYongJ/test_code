package sample.cafekiosk.spring.api.product;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.api.product.response.ProductResponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;
/**
 * readOnly = true : 읽기 전용
 * CRUD에서 CUD작업이 동작을 하지 않고 조회만 가능
 * JPA : CUD 스냅샷 저장, 변경 감지를 하지 않아도 되는 이점이생김(성능 향상)
 *
 * CQRS - Command / Query 분리( read 요청이 보통 많기 때문에 분리하여 처리 )
 * 전체 클래스에 readOnly를 건 후 , 메서드단위로 CUD작업시 트렌젝션을 거는 것이 좋다.
 * */

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductNumberFactory productNumberFactory;
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {

        String nextProductNumber = productNumberFactory.createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());

    }




}
