package com.example.catalogo.domains.core.exceptions;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import com.example.catalogo.exceptions.InvalidDataException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InvalidDataExceptionTest {

    static Stream<Object[]> exceptionProvider() {
        Throwable cause = new Throwable();
        Map<String, String> errors = new HashMap<>();
        errors.put("field1", "error1");

        return Stream.of(
                new Object[] { "message", null, null, false, false },
                new Object[] { "message", cause, null, false, false },
                new Object[] { "message", cause, null, true, true },
                new Object[] { "message", null, errors, false, false },
                new Object[] { "message", cause, errors, false, false },
                new Object[] { "message", cause, errors, true, true });
    }

    @ParameterizedTest
    @MethodSource("exceptionProvider")
    public void testInvalidDataException(String message, Throwable cause, Map<String, String> errors,
            boolean enableSuppression, boolean writableStackTrace) {
        InvalidDataException exception = new InvalidDataException(message, cause, errors, enableSuppression,
                writableStackTrace);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
        assertEquals(errors, exception.getErrors());
    }
}