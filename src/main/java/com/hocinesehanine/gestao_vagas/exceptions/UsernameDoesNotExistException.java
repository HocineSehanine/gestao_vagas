package com.hocinesehanine.gestao_vagas.exceptions;

public class UsernameDoesNotExistException extends RuntimeException{
    public UsernameDoesNotExistException() {
        super("usuário não existe");
    }
}
