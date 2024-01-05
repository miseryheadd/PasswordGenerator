package com.example;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

    /**
 * Класс PasswordGenerator предоставляет функционал для генерации случайных паролей
 * на основе пользовательских параметров, таких как длина, язык, включение заглавных/
 * строчных букв, цифр и специальных символов.
 * @author Elena Furashova
 * @version 2.0
 */

public class PasswordGeneratorMain {
    /**
     * logger используется для логирования программы.
     */
    private static final Logger logger = LogManager.getLogger(PasswordGenerator.class);
    /**
     * Используется для получения пользовательского ввода из консоли.
     */
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Метод main является точкой входа в программу.
     *
     * @param args Аргументы командной строки (не используются).
     */
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "../resources/log4j2.xml");
        logger.info("Password Generator started");
        int length = getPasswordLength();
        String[] selectedLanguages = getSelectedLanguages();
        boolean includeUppercase = getYesOrNoInput("Use uppercase letters?");
        boolean includeLowercase = getYesOrNoInput("Use lowercase letters?");
        boolean includeDigits = getYesOrNoInput("Use numbers?");
        String selectedDigits = "";
        if (includeDigits) {
            selectedDigits = getCustomDigits();
        }
        boolean includeSpecialChars = getYesOrNoInput("Add special characters?");

        logger.info("Password generation initiated with parameters: length={}, languages={}, includeUppercase={}, includeLowercase={}, includeDigits={}, includeSpecialChars={}",
                length, Arrays.toString(selectedLanguages), includeUppercase, includeLowercase, includeDigits, includeSpecialChars);

        long startTime = System.currentTimeMillis();
        String password = PasswordGenerator.generatePassword(length, selectedLanguages, includeUppercase, includeLowercase, selectedDigits, includeSpecialChars);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Generated password: " + password);
        System.out.println("Execution time: " + executionTime + " ms");
        logger.info("Generated password: {}", password);
        logger.info("Execution time: {} ms", executionTime);
        logger.info("Password Generator completed successfully");
    }

    /**
     * Получает длину пароля от пользователя.
     *
     * @return Длина пароля, введенная пользователем.
     */
    private static int getPasswordLength() {
        int length = 0;
        while (true) {
            boolean validLengthEntered = false;
            while (!validLengthEntered) {
                try {
                    System.out.print("Enter the length of the password (10,000 to 1,000,000 characters): ");
                    length = scanner.nextInt();
                    scanner.nextLine();

                    if (length >= 10000 && length <= 1000000) {
                        validLengthEntered = true;
                    } else {
                        System.out.println("Please enter a number between 10,000 and 1,000,000.");
                    }
                } catch (InputMismatchException e) {
                    logger.error("InputMismatchException occurred: {}", e.getMessage());
                    System.out.println("Please enter a number for the length of the password.");
                    scanner.nextLine();
                }
            }

            if (validLengthEntered) {
                break;
            }
        }
        return length;
    }
    /**
     * Получает выбранные пользователем языки для генерации пароля.
     *
     * @return Массив строк с выбранными языками.
     */
    private static String[] getSelectedLanguages() {
        String[] languages;
        while (true) {
            System.out.print("Select the language(s) separeted by the space from the suggested languages: english, russian, german: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.isEmpty()) {
                System.out.println("No language is selected.");
                continue;
            }

            languages = input.split("\\s+");
            boolean invalidInput = false;

            for (String language : languages) {
                if (!(PasswordGenerator.LANGUAGES.contains(language.trim()))) {
                    System.out.println("Incorrect language input: " + language);
                    invalidInput = true;
                    break;
                }
            }
            if (!invalidInput) {
                break;
            }
        }
        return languages;
    }
    /**
     * Запрашивает у пользователя ответ "да" или "нет" на заданный вопрос.
     *
     * @param message Сообщение, которое будет выведено для пользователя.
     * @return true, если пользователь ввел "да", иначе - false.
     */
    private static boolean getYesOrNoInput(String message) {
        while (true) {
            System.out.print(message + " (yes/no): ");
            String input = scanner.nextLine().toLowerCase();

            if (input.equals("yes") || input.equals("no")) {
                return input.equals("yes");
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }
    /**
     * Получает от пользователя цифры, которые необходимо добавить в пароль.
     *
     * @return Строка, содержащая только введенные пользователем цифры.
     */
    private static String getCustomDigits() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the digits you want to add to the password: ");
        return scanner.nextLine().replaceAll("[^0-9]", "");
    }
}
