package com.hocinesehanine.gestao_vagas.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized user");
    }
}
