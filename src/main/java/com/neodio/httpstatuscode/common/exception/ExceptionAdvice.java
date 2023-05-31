package com.neodio.httpstatuscode.common.exception;

import com.neodio.httpstatuscode.common.CommonResponse;
import com.neodio.httpstatuscode.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse DefaultHandlerExceptionResolver(MethodArgumentNotValidException e){
        log.error("DTO validation 에러 발생");
        String error=e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("[에러 내용] : {}",error);
        return CommonResponse.fail(e.getBindingResult().getFieldError().getDefaultMessage(), ErrorCode.COMMON_ILLEGAL_STATUS.getErrorMsg());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchTypeException.class)
    public CommonResponse NotMatchTypeExceptionResolver(NotMatchTypeException e){
        return CommonResponse.fail(e.getMessage(),e.getErrorCode().getErrorMsg());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public CommonResponse BaseException(BaseException e){
        return CommonResponse.fail(e.getMessage(),e.getErrorCode().getErrorMsg());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UrlException.class)
    public CommonResponse UrlException(BaseException e){
        return CommonResponse.fail(e.getMessage(),e.getErrorCode().getErrorMsg());
    }
}
