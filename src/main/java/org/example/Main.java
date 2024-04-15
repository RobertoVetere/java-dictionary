package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static BufferedReader dictionary;
    private static Map<String, String> englishToSpanishMap;
    private static Map<String, String> spanishToEnglishMap;
    private static FileReader dictionaryFile;

    public static void main(String[] args) {
        initialize();
        printExamples();
        runDictionaryInterface();
    }

    private static void printExamples() {
        try {
            String line;
            dictionary = new BufferedReader(new FileReader("X:\\Temario Resumenes DAM\\Java\\ejercicios9_4\\dictionary.txt"));
            while ((line = dictionary.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error al imprimir ejemplos: " + e.getMessage());
        }
    }

    private static void runDictionaryInterface() {
        while (true) {
            try {
                displayMenu();
                int opcion = scanner.nextInt();
                scanner.nextLine();
                processOption(opcion);
            } catch (InputMismatchException e) {
                System.out.println("Error: Por favor, introduzca un número válido.");
                scanner.nextLine();
            }
        }
    }

    private static void initialize() {
        scanner = new Scanner(System.in);
        englishToSpanishMap = new HashMap<>();
        spanishToEnglishMap = new HashMap<>();
        readDictionaryFromTxt();
        createDictionaryMap();
    }

    private static void createDictionaryMap() {
        try {
            String line;
            while ((line = dictionary.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2) {
                    String englishWord = parts[0].trim();
                    String spanishWord = parts[1].trim();
                    englishToSpanishMap.put(englishWord, spanishWord);
                    spanishToEnglishMap.put(spanishWord,englishWord);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el diccionario: " + e.getMessage());
        } finally {
            if (dictionary != null) {
                try {
                    dictionary.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el diccionario: " + e.getMessage());
                }
            }
        }
    }

    private static void readDictionaryFromTxt() {
        try{
        dictionaryFile = new FileReader("X:\\Temario Resumenes DAM\\Java\\ejercicios9_4\\dictionary.txt");
            dictionary = new BufferedReader(dictionaryFile);
        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            System.out.println("Archivo no encontrado");
        }
    }

    private static void displayMenu() {
        System.out.println("\n1. Traducir del Español al Inglés");
        System.out.println("2. Translate from English to Spanish");
        System.out.println("3. Salir");
        System.out.println("Seleccione una opción: ");
    }

    private static void processOption(int opcion) {
        switch (opcion) {
            case 1:
                System.out.println("Introduce una palabra en español para traducir: ");
                String spanishWord = scanner.nextLine();
                translate(spanishWord, true);
                break;
            case 2:
                System.out.println("Introduce una palabra en inglés para traducir: ");
                String englishWord = scanner.nextLine();
                translate(englishWord, false);
                break;
            case 3:
                exitProgram();
                break;
            default:
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
        }
    }
    private static void translate(String word, boolean spanishToEnglish) {
        if (spanishToEnglish) {
            String translation = spanishToEnglishMap.get(word);
            printTranslation(word, translation);
        } else {
            String translation = englishToSpanishMap.get(word);
            printTranslation(word, translation);
        }
    }

    private static void printTranslation(String originalWord, String translation) {
        if (translation != null) {
            System.out.println("La traducción de '" + originalWord + "' es: " + translation);
        } else {
            System.out.println("No se encontró traducción para '" + originalWord + "'");
        }
    }

    private static void exitProgram() {
        System.out.println("Saliendo del programa...");
        scanner.close();
        System.exit(0);
    }
}