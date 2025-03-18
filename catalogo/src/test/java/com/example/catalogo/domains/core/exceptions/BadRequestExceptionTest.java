package com.example.catalogo.domains.core.exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import com.example.catalogo.exceptions.BadRequestException;

import java.util.stream.Stream;

public class BadRequestExceptionTest {

    static Stream<Object[]> exceptionProvider() {
        Throwable cause = new Throwable();

        return Stream.of(
                new Object[] { "message", null, false, false },
                new Object[] { "message", cause, false, false },
                new Object[] { "message", cause, true, true });
    }

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    public void testBadRequestException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        BadRequestException exception = new BadRequestException(message, cause, enableSuppression, writableStackTrace);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}