package com.neodio.httpstatuscode.common.exception;

import com.neodio.httpstatuscode.common.ErrorCode;

public class NotMatchTypeException extends BaseException{
    public NotMatchTypeException(){
        super(ErrorCode.COMMON_INVALID_PARAMETER);
    }

    public NotMatchTypeException(String msg){
        super(msg,ErrorCode.COMMON_INVALID_PARAMETER);
    }
}
