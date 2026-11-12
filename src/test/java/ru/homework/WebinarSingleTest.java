package ru.homework;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import ru.homework.support.TestLifecycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static ru.homework.support.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

/** Четыре теста из прошлого ДЗ: теперь каждый использует ассерты и 10 повторов. */
@Tag("webinar")
class WebinarSingleTest extends TestLifecycle {
    @RepeatedTest(10)
    void isEven() {
        for (String[] row : csvRows("/basics.csv", 3)) {
            int value = Integer.parseInt(row[0]);
            assertEquals(Boolean.parseBoolean(row[1]), BasicJava.isEven(value), "isEven(" + value + ") из CSV");
        }
        int n = randomInt(-1000, 1000);
        assertEquals((n & 1) == 0, BasicJava.isEven(n), "isEven(" + n + ")");
    }

    @RepeatedTest(10)
    void isPositive() {
        for (String[] row : csvRows("/basics.csv", 3)) {
            int value = Integer.parseInt(row[0]);
            assertEquals(Boolean.parseBoolean(row[2]), BasicJava.isPositive(value),
                    "isPositive(" + value + ") из CSV: ноль считается неотрицательным");
        }
        int n = randomInt(-1000, 1000);
        assertEquals(Integer.signum(n) != -1, BasicJava.isPositive(n), "isPositive(" + n + ")");
    }

    @RepeatedTest(10)
    void hasBug() {
        String[] messages = randomMessages();
        boolean expected = Arrays.stream(messages).anyMatch("bug"::equalsIgnoreCase);
        assertEquals(expected, BasicJava.hasBug(messages), "hasBug(" + Arrays.toString(messages) + ")");
    }

    @RepeatedTest(10)
    void reverse() {
        String[] input = randomNames();
        String[] snapshot = input.clone();
        List<String> expected = new ArrayList<>(Arrays.asList(input));
        Collections.reverse(expected);
        String[] actual = BasicJava.reverse(input);
        assertAll("reverse(" + Arrays.toString(snapshot) + ")",
                () -> assertArrayEquals(expected.toArray(new String[0]), actual, "Обратный порядок элементов"),
                () -> assertArrayEquals(snapshot, input, "Исходный массив должен сохраниться"),
                () -> assertNotSame(input, actual, "Ожидался новый массив; получена исходная ссылка"));
    }
}
