package org.shoppingMall.web.DTO.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private Integer code;
    private String message;
    private String detailMessage;
    private Object request;
}
