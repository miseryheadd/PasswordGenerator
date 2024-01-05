package com.example;

import java.security.SecureRandom;
import java.util.*;

/**
 *  Класс PasswordGenerator хранит неизменяемые переменные(такие как алфавиты) и отвечает за методы
 *  генерации строки из всех доступных для пароля символов и генерации самого пароля путем
 *  случайного выбора этих символов.
 */
public class PasswordGenerator {

    /**
     * Строка, содержащая английский алфавит в нижнем регистре.
     * Используется для генерации паролей на основе английского языка.
     */
    private static final String LOWERCASE_EN = "abcdefghijklmnopqrstuvwxyz";
    /**
     * Строка, содержащая русский алфавит в нижнем регистре.
     * Используется для генерации паролей на основе русского языка.
     */
    private static final String LOWERCASE_RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    /**
     * Строка, содержащая немецкий алфавит в нижнем регистре.
     * Используется для генерации паролей на основе немецкого языка.
     */
    private static final String LOWERCASE_DE = "aäbcdefghijklmnoöpqrsßtuüvwxyz";
    /**
     * Строка, содержащая английский алфавит в верхнем регистре.
     * Используется для генерации паролей на основе английского языка.
     */
    private static final String UPPERCASE_EN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Строка, содержащая русский алфавит в верхнем регистре.
     * Используется для генерации паролей на основе русского языка.
     */
    private static final String UPPERCASE_RU = "АБВГДЕЕËЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    /**
     * Строка, содержащая немецкий алфавит в верхнем регистре.
     * Используется для генерации паролей на основе немецкого языка.
     */
    private static final String UPPERCASE_DE = "AÄBCDEFGHIJKLMNOÖPQRSßTUÜVWXYZ";
    /**
     * Строка, содержащая спец. символы.
     * Используется для генерации паролей, если выбраны спец. символы.
     */
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";
    /**
     * Массив, содержащий доступные языки.
     * Можно добавить новый язык при условии добавления новых переменных UPPERCASE_LANG, LOWERCASE_LANG.
     */
    protected static final ArrayList<String> LANGUAGES = new ArrayList<>(Arrays.asList("english", "german", "russian"));

    /**
     * Используется для выбора случайного числа.
     */
    private static final SecureRandom random = new SecureRandom();

    /**
     * Генерирует строку, состоящую из символов на основе заданных параметров. Затем генерирует пароль с помощью метода {@link #generateRandomPassword(int, String) generateRandomPassword}
     *
     * @param length              Длина пароля.
     * @param languages           Массив выбранных пользователем языков.
     * @param includeUppercase    Флаг включения заглавных букв.
     * @param includeLowercase    Флаг включения строчных букв.
     * @param selectedDigits      Цифры, введенные пользователем.
     * @param includeSpecialChars Флаг включения специальных символов.
     * @return Сгенерированный пароль на основе указанных параметров.
     */
    // protected потому что junit не может работать с private методами
    protected static String generatePassword(int length, String[] languages, boolean includeUppercase, boolean includeLowercase, String selectedDigits, boolean includeSpecialChars) {
        StringBuilder chars = new StringBuilder();

        for (String language : languages) {
            switch (language.trim()) {
                case "english" -> {
                    if (includeUppercase) {
                        chars.append(UPPERCASE_EN);
                    }
                    if (includeLowercase) {
                        chars.append(LOWERCASE_EN);
                    }
                }
                case "russian" -> {
                    if (includeUppercase) {
                        chars.append(UPPERCASE_RU);
                    }
                    if (includeLowercase) {
                        chars.append(LOWERCASE_RU);
                    }
                }
                case "german" -> {
                    if (includeUppercase) {
                        chars.append(UPPERCASE_DE);
                    }
                    if (includeLowercase) {
                        chars.append(LOWERCASE_DE);
                    }
                }
            }
        }

        chars.append(selectedDigits);

        if (includeSpecialChars) {
            chars.append(SPECIAL_CHARACTERS);
        }

        return generateRandomPassword(length, chars.toString());
    }

    /**
     * Генерирует случайный пароль на основе выбранных символов.
     *
     * @param length         Длина пароля.
     * @param availableChars Строка символов, из которых будет сгенерирован пароль.
     * @return Сгенерированный случайный пароль.
     */
    protected static String generateRandomPassword(int length, String availableChars) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(availableChars.length());
            password.append(availableChars.charAt(randomIndex));
        }
        return password.toString();
    }
}