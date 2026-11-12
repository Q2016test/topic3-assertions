package ru.homework;

/** Намеренная ошибка для демонстрации падающего ассерта в теме 3. */
public final class IncorrectCalculator {
    private IncorrectCalculator() {
    }

    public static int add(int left, int right) {
        // По условию один метод должен вернуть неверное значение: вычитание вместо сложения.
        return left - right;
    }
}
