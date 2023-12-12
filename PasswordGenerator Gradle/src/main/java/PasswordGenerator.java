import java.security.SecureRandom;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Класс PasswordGenerator предоставляет функционал для генерации случайных паролей
 * на основе пользовательских параметров, таких как длина, язык, включение заглавных/
 * строчных букв, цифр и специальных символов.
 * @author Elena Furashova
 * @version 1.0
 */
public class PasswordGenerator {
    /**
     * logger используется для логирования программы.
     */
    private static final Logger logger = LogManager.getLogger(PasswordGenerator.class);
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
    private static final ArrayList<String> LANGUAGES = new ArrayList<>(Arrays.asList("english", "german", "russian"));
    /**
     * Используется для получения пользовательского ввода из консоли.
     */
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Используется для выбора случайного числа.
     */
    private static final SecureRandom random = new SecureRandom();

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
        String password = generatePassword(length, selectedLanguages, includeUppercase, includeLowercase, selectedDigits, includeSpecialChars);
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
                if (!(LANGUAGES.contains(language.trim()))) {
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
    private static String generateRandomPassword(int length, String availableChars) {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(availableChars.length());
            password.append(availableChars.charAt(randomIndex));
        }
        return password.toString();
    }
}
