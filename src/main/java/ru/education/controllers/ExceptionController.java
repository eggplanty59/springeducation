package ru.education.controllers;

import org.springframework.data.annotation.Transient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.education.exceptions.*;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({EntityIllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private ErrorResponseEntity handleEntityIllegalArgumentException(EntityIllegalArgumentException e){
        return createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityAlreadeExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    private ErrorResponseEntity handleEntityAlreadeExistsException(EntityAlreadeExistsException e){
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    private ErrorResponseEntity handleEntityConflictException(EntityConflictException e){
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityHasDetailsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    private ErrorResponseEntity handleEntityHasDetailsException(EntityHasDetailsException e){
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private ErrorResponseEntity handleEntityNotFoundException(EntityNotFoundException e){
        return createErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }

    private static ErrorResponseEntity createErrorResponseEntity(BaseException e, HttpStatus httpStatus){
        return new ErrorResponseEntity(e.getMessage(), httpStatus.getReasonPhrase(), httpStatus.value());
    }
}
