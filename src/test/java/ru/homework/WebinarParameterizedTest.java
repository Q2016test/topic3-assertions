package ru.homework;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.homework.support.TestLifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ru.homework.support.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

/** Задача 2: четыре метода с @ParameterizedTest. */
@Tag("webinar")
class WebinarParameterizedTest extends TestLifecycle {
    @ParameterizedTest(name = "getGrade: {0} -> {1}")
    @CsvFileSource(resources = "/grades.csv", numLinesToSkip = 1, encoding = "UTF-8")
    @MethodSource("randomGrades")
    void getGrade(int score, String expected) {
        assertEquals(expected, BasicJava.getGrade(score), "getGrade(" + score + ")");
    }

    @ParameterizedTest(name = "sumToN: {0} -> {1}")
    @CsvFileSource(resources = "/sums.csv", numLinesToSkip = 1, encoding = "UTF-8")
    @MethodSource("randomSums")
    void sumToN(int n, int expected) {
        assertEquals(expected, BasicJava.sumToN(n), "sumToN(" + n + ")");
    }

    @ParameterizedTest(name = "getEvenInRange: [{0}, {1}] -> {2}")
    @CsvFileSource(resources = "/ranges.csv", numLinesToSkip = 1, encoding = "UTF-8")
    @MethodSource("randomRanges")
    void getEvenInRange(int start, int end, String expected) {
        assertEquals(expected, BasicJava.getEvenInRange(start, end),
                "getEvenInRange(" + start + ", " + end + ")");
    }

    @ParameterizedTest(name = "removeSpecificName: {0}, remove={1}")
    @MethodSource("nameRemovalCases")
    void removeSpecificName(List<String> input, String nameToRemove, List<String> expected) {
        List<String> snapshot = input == null ? null : new ArrayList<>(input);
        List<String> actual = BasicJava.removeSpecificName(input, nameToRemove);
        assertAll("removeSpecificName(" + snapshot + ", " + nameToRemove + ")",
                () -> assertEquals(expected, actual, "Список после удаления всех совпадений"),
                () -> assertEquals(snapshot, input, "Исходный список должен сохраниться"),
                () -> assertNotSame(input, actual, "Ожидался новый список; получена исходная ссылка"));
    }

    static Stream<Arguments> randomGrades() {
        return Arrays.stream(randomNumbers(10, -20, 120))
                .mapToObj(score -> Arguments.of(score, expectedGrade(score)));
    }

    static Stream<Arguments> randomSums() {
        return Arrays.stream(randomNumbers(10, -10, 1000))
                .mapToObj(n -> Arguments.of(n, n <= 0 ? 0 : n * (n + 1) / 2));
    }

    static Stream<Arguments> randomRanges() {
        return IntStream.range(0, 10).mapToObj(i -> {
            int start = randomInt(-25, 25);
            int end = randomInt(-25, 25);
            String expected = IntStream.rangeClosed(start, end).filter(n -> (n & 1) == 0)
                    .mapToObj(Integer::toString).collect(Collectors.joining(" "));
            return Arguments.of(start, end, expected);
        });
    }

    static Stream<Arguments> nameRemovalCases() {
        Stream<Arguments> randomCases = IntStream.range(0, 10).mapToObj(i -> {
            List<String> input = new ArrayList<>(Arrays.asList(randomNames()));
            String[] names = {"Alex", "Maria", "Ivan", "alex", null};
            String nameToRemove = names[randomInt(0, names.length - 1)];
            List<String> expected = input.stream().filter(name -> !Objects.equals(name, nameToRemove))
                    .collect(Collectors.toList());
            return Arguments.of(input, nameToRemove, expected);
        });
        return Stream.concat(csvNameRemovalCases(), randomCases);
    }

    /** Внутри ячейки имена разделены точкой с запятой; NULL обозначает null. */
    private static Stream<Arguments> csvNameRemovalCases() {
        return csvRows("/names.csv", 3).stream().map(columns -> Arguments.of(
                parseNames(columns[0]), parseName(columns[1]), parseNames(columns[2])));
    }

    private static List<String> parseNames(String cell) {
        if ("NULL".equals(cell)) {
            return null;
        }
        if (cell.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(cell.split(";", -1)).map(WebinarParameterizedTest::parseName)
                .collect(Collectors.toList());
    }

    private static String parseName(String cell) {
        return "NULL".equals(cell) ? null : cell;
    }
}
