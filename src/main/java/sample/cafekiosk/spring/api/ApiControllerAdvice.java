package sample.cafekiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@ControllerAdvice @ControllerAdvice는 일반적인 컨트롤러에 대한 전역 예외 처리 등을 제공하고, 주로 뷰(View) 이름을 반환하여 페이지 이동에 사용됩니다.
@RestControllerAdvice // REST 컨트롤러에 대한 전역 예외 처리를 위해 사용되며, JSON/XML 응답을 자동으로 처리합니다.
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResponse<Object> bindException(BindException e){
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        return ApiResponse.of(HttpStatus.BAD_REQUEST, objectError.getDefaultMessage(),null);
    }

}
