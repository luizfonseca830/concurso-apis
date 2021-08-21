package com.br.concurso.api.exceptionhandler;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*observa toda aplicação*/
@ControllerAdvice
public class ConcursoExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        String messageUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Error> erros = Arrays.asList(new Error(messageUser, messageDeveloper));
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<Error> erros = createListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

//SOMENTE RETORNA NOT_FOUND AO TENTAR EXCLUIR SEM O MESMO EXISTIR
//    /*Tratando Exceptions*/
//    @ExceptionHandler({EmptyResultDataAccessException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public void handleEmptyResultDataAccessException(){
//
//    }

        /*Tratando Exceptions ENVIANDO MESNAGEM PARA DEV E O USUÁRIO*/
    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
        String messageUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ex.toString();
        List<Error> erros = Arrays.asList(new Error(messageUser, messageDeveloper));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private List<Error> createListErrors(BindingResult bindingResult) {
        List<Error> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String messageUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String messageDeveloper = fieldError.toString();
            erros.add(new Error(messageUser, messageDeveloper));
        }

        return erros;
    }


    public static class Error {

        private String messageUser;
        private String messageDeveloper;

        public Error(String messageUser, String messageDeveloper) {
            this.messageUser = messageUser;
            this.messageDeveloper = messageDeveloper;
        }

        public String getMessageUser() {
            return messageUser;
        }

        public String getMessageDeveloper() {
            return messageDeveloper;
        }
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
        String messageUser = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
        String messageDeveloper = ExceptionUtils.getRootCauseMessage(ex);
        List<Error> erros = Arrays.asList(new Error(messageUser, messageDeveloper));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
