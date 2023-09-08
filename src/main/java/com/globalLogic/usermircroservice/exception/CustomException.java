package com.globalLogic.usermircroservice.exception;

import java.util.ArrayList;
import java.util.List;

public class CustomException extends RuntimeException {
    private List<ErrorDetail> errors = new ArrayList<>();

    public CustomException(ErrorDetail errorDetail) {
        this.errors.add(errorDetail);
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }
}
