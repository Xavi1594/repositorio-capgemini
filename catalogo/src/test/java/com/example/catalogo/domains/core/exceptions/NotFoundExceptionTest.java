package com.example.catalogo.domains.core.exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import com.example.catalogo.exceptions.NotFoundException;

import java.util.stream.Stream;

public class NotFoundExceptionTest {

    static Stream<Object[]> exceptionProvider() {
        Throwable cause = new Throwable();

        return Stream.of(
                new Object[] { "Not found", null, false, false },
                new Object[] { "Custom message", null, false, false },
                new Object[] { "Not found", cause, false, false },
                new Object[] { "Custom message", cause, false, false },
                new Object[] { "Not found", cause, true, true },
                new Object[] { "Custom message", cause, true, true });
    }

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    public void testNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        NotFoundException exception = new NotFoundException(message, cause, enableSuppression, writableStackTrace);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}