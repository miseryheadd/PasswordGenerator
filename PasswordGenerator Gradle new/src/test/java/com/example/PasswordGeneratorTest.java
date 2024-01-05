package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.PasswordGenerator;

public class PasswordGeneratorTest {

    // Тест проверяет, генерируется ли пароль только с заглавными буквами
    @Test
    public void testOnlyUppercaseLetters() {
        int length = 12000;
        String[] languages = {"english"};
        boolean includeUppercase = true;
        boolean includeLowercase = false;
        boolean includeDigits = false;
        String selectedDigits = "";
        boolean includeSpecialChars = false;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        long startTime = System.currentTimeMillis();
        String password = PasswordGenerator.generatePassword(length, languages, includeUppercase, includeLowercase, selectedDigits, includeSpecialChars);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertEquals(length, password.length());
        assertTrue(password.matches("[A-Z]+"));
        System.out.println("Password: " + password + ", Execution Time: " + executionTime + "ms");
    }

    // Тест проверяет, генерируется ли пароль только с заглавными буквами
    @Test
    public void testOnlyLowercaseLetters() {
        int length = 12000;
        String[] languages = {"english"};
        boolean includeUppercase = false;
        boolean includeLowercase = true;
        boolean includeDigits = false;
        String selectedDigits = "";
        boolean includeSpecialChars = false;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        long startTime = System.currentTimeMillis();
        String password = PasswordGenerator.generatePassword(length, languages, includeUppercase, includeLowercase, selectedDigits, includeSpecialChars);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertEquals(length, password.length());
        assertTrue(password.matches("[a-z]+"));
        System.out.println("Password: " + password + ", Execution Time: " + executionTime + "ms");
    }

    // Тест проверяет, генерируется ли пароль только с цифрами
    @Test
    public void testOnlyDigits() {
        int length = 10000;
        String[] languages = {"english"};
        boolean includeUppercase = false;
        boolean includeLowercase = false;
        boolean includeDigits = true;
        String selectedDigits = "123457";
        boolean includeSpecialChars = false;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        long startTime = System.currentTimeMillis();
        String password = PasswordGenerator.generatePassword(length, languages, includeUppercase, includeLowercase, selectedDigits, includeSpecialChars);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        assertEquals(length, password.length());
        assertTrue(password.matches("[0-9]+"));
        System.out.println("Password: " + password + ", Execution Time: " + executionTime + "ms");
    }

    // Тест проверяет, сколько времени затрачивается на генерацию пароля определенной длины
    @Test
    public void testExecutionTime() {
        int[] testLengths = {10000, 10000, 50000, 100000, 500000, 1000000};

        String[] languages = {"english"};
        boolean includeUppercase = true;
        boolean includeLowercase = true;
        boolean includeDigits = true;
        String selectedDigits = "1234567890";
        boolean includeSpecialChars = true;

        PasswordGenerator passwordGenerator = new PasswordGenerator();

        for (int length : testLengths) {
            long startTime = System.currentTimeMillis();
            PasswordGenerator.generatePassword(length, languages, includeUppercase,
                    includeLowercase, selectedDigits, includeSpecialChars);
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            System.out.println("Generated password length: " + length);
            System.out.println("Execution time: " + executionTime + " ms");
        }
    }
}

