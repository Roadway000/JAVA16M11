import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreamUtils {
    static <ARG1, ARG2, RESULT> Stream<RESULT> zip(
            Stream<ARG1> s1,
            Stream<ARG2> s2,
            BiFunction<ARG1, ARG2, RESULT> combiner) {
        final var i2 = s2.iterator();
        return s1.map(x1 -> i2.hasNext() ? combiner.apply(x1, i2.next()) : null)
                .takeWhile(Objects::nonNull);
    }
}

class StreamUtilsTest {
    @ParameterizedTest
    @MethodSource("shouldZipTestCases")
    <ARG1, ARG2, RESULT>
    void shouldZip(
            String testName,
            Stream<ARG1> s1,
            Stream<ARG2> s2,
            BiFunction<ARG1, ARG2, RESULT> combiner,
            Stream<RESULT> expected) {
        var actual = StreamUtils.zip(s1, s2, combiner);

        assertEquals(
                expected.collect(Collectors.toList()),
                actual.collect(Collectors.toList()),
                testName);
    }

    private static Stream<Arguments> shouldZipTestCases() {
        return Stream.of(
                Arguments.of(
                        "Two empty streams",
                        Stream.empty(),
                        Stream.empty(),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.empty()),
                Arguments.of(
                        "One singleton and one empty stream",
                        Stream.of(1),
                        Stream.empty(),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.empty()),
                Arguments.of(
                        "One empty and one singleton stream",
                        Stream.empty(),
                        Stream.of(1),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.empty()),
                Arguments.of(
                        "Two singleton streams",
                        Stream.of("blah"),
                        Stream.of(1),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.of(pair("blah", 1))),
                Arguments.of(
                        "One singleton, one multiple stream",
                        Stream.of("blob"),
                        Stream.of(2, 3),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.of(pair("blob", 2))),
                Arguments.of(
                        "One multiple, one singleton stream",
                        Stream.of("foo", "bar"),
                        Stream.of(4),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.of(pair("foo", 4))),
                Arguments.of(
                        "Two multiple streams",
                        Stream.of("nine", "eleven"),
                        Stream.of(10, 12),
                        (BiFunction<Object, Object, Object>) StreamUtilsTest::combine,
                        Stream.of(pair("nine", 10), pair("eleven", 12)))
        );
    }

    private static List<Object> pair(Object o1, Object o2) {
        return List.of(o1, o2);
    }

    static private <T1, T2> List<Object> combine(T1 o1, T2 o2) {
        return List.of(o1, o2);
    }

    @Test
    static void shouldLazilyEvaluateInZip() {
        final var a = new AtomicInteger();
        final var b = new AtomicInteger();
        final var zipped = StreamUtils.zip(
                Stream.generate(a::incrementAndGet),
                Stream.generate(b::decrementAndGet),
                (xa, xb) -> xb + 3 * xa);

        assertEquals(0, a.get(), "Should not have evaluated a at start");
        assertEquals(0, b.get(), "Should not have evaluated b at start");

        final var takeTwo = zipped.limit(2);

        assertEquals(0, a.get(), "Should not have evaluated a at take");
        assertEquals(0, b.get(), "Should not have evaluated b at take");

        final var list = takeTwo.collect(Collectors.toList());

        assertEquals(2, a.get(), "Should have evaluated a after collect");
        assertEquals(-2, b.get(), "Should have evaluated b after collect");
        assertEquals(List.of(2, 4), list);
    }

    public static void main(String[] args) {
        System.out.println(shouldZipTestCases());
    }
}