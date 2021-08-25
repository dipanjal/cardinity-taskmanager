package com.cardinity.assessment.utils;

import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@UtilityClass
public class StreamUtils {

    public static <T> Optional<T> find(Stream<T> stream, Predicate<T> predicate){
        return stream
                .filter(predicate)
                .findFirst();
    }
}
