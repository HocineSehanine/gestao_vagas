package com.hocinesehanine.gestao_vagas.exceptions;

public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException() {
        super("usuário já existe");
    }
}
