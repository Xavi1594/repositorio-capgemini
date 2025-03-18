package com.example.catalogo.domains.core.exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import com.example.catalogo.exceptions.DuplicateKeyException;

import java.util.stream.Stream;

public class DuplicateKeyExceptionTest {

    static Stream<Object[]> exceptionProvider() {
        Throwable cause = new Throwable();

        return Stream.of(
                new Object[] { "Duplicate key", null, false, false },
                new Object[] { "Custom message", null, false, false },
                new Object[] { "Duplicate key", cause, false, false },
                new Object[] { "Custom message", cause, false, false },
                new Object[] { "Duplicate key", cause, true, true },
                new Object[] { "Custom message", cause, true, true });
    }

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    public void testDuplicateKeyException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        DuplicateKeyException exception = new DuplicateKeyException(message, cause, enableSuppression,
                writableStackTrace);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}