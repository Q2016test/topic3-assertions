package ru.homework;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.homework.support.TestLifecycle;

import java.util.Arrays;
import java.util.stream.Stream;

import static ru.homework.support.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Тесты лекции из темы 2, дополненные ассертами и повторами для темы 3. */
@Tag("lecture")
class LectureTest extends TestLifecycle {
    @RepeatedTest(10)
    void isEvenTenTimes() {
        int n = randomInt(1, 100);
        assertEquals((n & 1) == 0, BasicJava.isEven(n), "isEven(" + n + ")");
    }

    @RepeatedTest(20)
    void checkAccessTwentyTimes() {
        int age = randomInt(0, 99);
        assertEquals(age >= 19 ? "Allowed" : "Denied", BasicJava.checkAccess(age),
                "checkAccess(" + age + "): доступ разрешён строго после 18 лет");
    }

    @ParameterizedTest(name = "getGrade: score={0}")
    @MethodSource("randomScores")
    void getGradeWithRandomArray(int score) {
        assertEquals(expectedGrade(score), BasicJava.getGrade(score), "getGrade(" + score + ")");
    }

    static Stream<Integer> randomScores() {
        int[] scores = randomNumbers(10, 0, 100);
        return Arrays.stream(scores).boxed();
    }
}
