package br.com.webcrawler.imdb.exception;

public class BusinessRulesException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessRulesException(String message) {
        super(message);
    }
}