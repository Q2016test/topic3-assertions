package ru.homework;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import ru.homework.support.TestLifecycle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.homework.support.TestData.randomInt;

/** Тема 3, лекция: ровно четыре тестовых метода с информативными ассертами. */
@Tag("lecture")
class AssertionLectureTest extends TestLifecycle {
    @RepeatedTest(10)
    void booleanResult() {
        int n = randomInt(0, 100) * 2;
        assertEquals(true, BasicJava.isEven(n), "isEven(" + n + "): чётное число");
    }

    @RepeatedTest(10)
    void listResult() {
        List<String> input = Arrays.asList("Alex", "Maria", "Alex");
        List<String> actual = BasicJava.removeSpecificName(input, "Alex");
        assertEquals(Collections.singletonList("Maria"), actual,
                "removeSpecificName(" + input + ", Alex): удалить все совпадения");
    }

    @RepeatedTest(10)
    void numericResult() {
        int n = randomInt(1, 1000);
        int expected = n * (n + 1) / 2;
        assertEquals(expected, BasicJava.sumToN(n), "sumToN(" + n + "): сумма чисел от 1 до n");
    }

    @Tag("expected-failure")
    @RepeatedTest(10)
    void incorrectResultMustFail() {
        int left = randomInt(1, 100);
        int right = randomInt(1, 100);
        int expected = left + right;
        int actual = IncorrectCalculator.add(left, right);
        // Этот ассерт действительно падает. Запуск: .\gradlew.bat failingAssertions
        assertEquals(expected, actual,
                "Учебная ошибка в add(" + left + ", " + right + "): ожидалось сложение, получено вычитание");
    }
}
