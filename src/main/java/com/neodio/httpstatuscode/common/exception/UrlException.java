package com.neodio.httpstatuscode.common.exception;

import com.neodio.httpstatuscode.common.ErrorCode;

public class UrlException extends BaseException {

    public UrlException(){
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public UrlException(String msg){
        super(msg, ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
