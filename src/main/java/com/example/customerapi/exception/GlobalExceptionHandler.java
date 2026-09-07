package com.example.customerapi.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.method.ParameterErrors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.validation.method.ParameterValidationResult;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCustomerNotFound(CustomerNotFoundException ex){
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
    }
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.visitResults(new HandlerMethodValidationException.Visitor() {

            @Override
            public void other(ParameterValidationResult result) {

                result.getResolvableErrors().forEach(error -> {

                    errors.put(
                            "customer",
                            error.getDefaultMessage()
                    );
                });
            }

            /**
             * Handle results for {@code @CookieValue} method parameters.
             *
             * @param cookieValue the annotation declared on the parameter
             * @param result      the validation result
             */
            @Override
            public void cookieValue(CookieValue cookieValue, ParameterValidationResult result) {

            }

            /**
             * Handle results for {@code @MatrixVariable} method parameters.
             *
             * @param matrixVariable the annotation declared on the parameter
             * @param result         the validation result
             */
            @Override
            public void matrixVariable(MatrixVariable matrixVariable, ParameterValidationResult result) {

            }

            /**
             * Handle results for {@code @ModelAttribute} method parameters.
             *
             * @param modelAttribute the optional {@code ModelAttribute} annotation,
             *                       possibly {@code null} if the method parameter is declared without it.
             * @param errors         the validation errors
             */
            @Override
            public void modelAttribute(@Nullable ModelAttribute modelAttribute, ParameterErrors errors) {

            }

            /**
             * Handle results for {@code @PathVariable} method parameters.
             *
             * @param pathVariable the annotation declared on the parameter
             * @param result       the validation result
             */
            @Override
            public void pathVariable(PathVariable pathVariable, ParameterValidationResult result) {

            }

            /**
             * Handle results for {@code @RequestBody} method parameters.
             *
             * @param requestBody the annotation declared on the parameter
             * @param errors      the validation error
             */
            @Override
            public void requestBody(
                    RequestBody requestBody,
                    ParameterErrors parameterErrors) {

                parameterErrors.getFieldErrors().forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });
            }

            /**
             * Handle results for {@code @RequestHeader} method parameters.
             *
             * @param requestHeader the annotation declared on the parameter
             * @param result        the validation result
             */
            @Override
            public void requestHeader(RequestHeader requestHeader, ParameterValidationResult result) {

            }

            /**
             * Handle results for {@code @RequestParam} method parameters.
             *
             * @param requestParam the optional {@code RequestParam} annotation,
             *                     possibly {@code null} if the method parameter is declared without it.
             * @param result       the validation result
             */
            @Override
            public void requestParam(@Nullable RequestParam requestParam, ParameterValidationResult result) {

            }

            @Override
            public void requestPart(
                    RequestPart requestPart,
                    ParameterErrors parameterErrors){

            }
        });

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed.",
                errors
        );
    }
}