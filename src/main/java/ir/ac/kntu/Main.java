package ir.ac.kntu;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum CommandType {
    DEFINE, SUM, SUB,
    MULTIPLY, DIVISION, REPLACEMENT,
    MULTIPLYMATRICES, TRANSPOSE, STICK
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> arrays = new ArrayList<>();
        while (true) {
            String command = sc.nextLine();
            CommandType commandType = recognizeTheCommand(command);
            if (commandType == null) {
                continue;
            }
            executeTheCommand(command, commandType, arrays);
            /*for (int i = 0; i < arrays.size(); i++){
                *System.out.println("  *********  ");
                System.out.println(arrays.get(i));
                System.out.println("  #########  ");
            }*/
        }
    }

    public static CommandType recognizeTheCommand(@NotNull String command) {
        if (command.matches("\\s*[a-zA-Z][a-zA-Z0-9_]*\\s?\\[[0-9]].*=.*")) {
            return CommandType.REPLACEMENT;
        } else if (command.matches("\\s*[a-zA-Z][a-zA-Z0-9_]*\\s*=\\s?\\[.*")) {
            if (isCorrectDefine(command)) {
                return CommandType.DEFINE;
            }
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?\\*\\s?[a-zA-Z][a-zA-Z0-9_]*.*")) {
            return CommandType.MULTIPLY;
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?\\+\\s?[a-zA-Z][a-zA-Z0-9_]*.*")) {
            return CommandType.SUM;
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?-\\s?[a-zA-Z][a-zA-Z0-9_]*.*")) {
            return CommandType.SUB;
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?/\\s?[a-zA-Z][a-zA-Z0-9_]*.*")) {
            return CommandType.DIVISION;
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?@\\s?[a-zA-Z][a-zA-Z0-9_]*.*")) {
            return CommandType.MULTIPLYMATRICES;
        } else if (command.matches(".*\\&\\s?[a-zA-Z][a-zA-Z0-9_]*\\s*")) {
            return CommandType.TRANSPOSE;
        } else if (command.matches(".*[a-zA-Z][a-zA-Z0-9_]*\\s?#\\s?[a-zA-Z][a-zA-Z0-9_]*.*")){
            return CommandType.STICK;
        }
        System.out.println("unknown command");
        return null;
    }

    public static void executeTheCommand(String command, CommandType commandType, ArrayList<String> arrays) {
        switch (commandType) {
            case DEFINE -> Define.defineArray(command, arrays);
            case REPLACEMENT -> Replacing.replacing(command, arrays);
            case MULTIPLY -> wiseElementCommands(command, arrays, CommandType.MULTIPLY);
            case SUM -> wiseElementCommands(command, arrays, CommandType.SUM);
            case SUB -> wiseElementCommands(command, arrays, CommandType.SUB);
            case DIVISION -> wiseElementCommands(command, arrays, CommandType.DIVISION);
            case MULTIPLYMATRICES -> MultiplyMatrices.multiplyMatrices(command, arrays);
            case TRANSPOSE -> Transpose.transpose(command, arrays);
            //case STICK -> Stick.stick(command, arrays);
            default -> {
            }
        }
    }

    public static void wiseElementCommands(String command, ArrayList<String> arrays, CommandType commandType) {
        String[] splitedCommand = IntExecute.splitCommand(command, commandType);
        int indexOfFirstElement = searchIndexOfArrayInArraylist(arrays, splitedCommand[1]);
        int indexOfSecondElement = searchIndexOfArrayInArraylist(arrays, splitedCommand[2]);
        String firstElement = arrays.get(indexOfFirstElement);
        String secondElement = arrays.get(indexOfSecondElement);
        String firstElementArray = firstElement.split("\\s?=\\s?")[1];
        String secondElementArray = secondElement.split("\\s?=\\s?")[1];
        String firstElementType = IntExecute.findArrayType(firstElementArray);
        String secondElementType = IntExecute.findArrayType(secondElementArray);
        if (firstElementType.equals("int") && secondElementType.equals("int")) {
            String string = IntExecute.findResultOfCommand(firstElementArray, secondElementArray, splitedCommand[0], commandType);
            Define.defineArray(string, arrays);
        } else if (firstElementType.equals("float") && secondElementType.equals("float")) {
            String string = FloatExecute.findResultOfCommand(firstElementArray, secondElementArray, splitedCommand[0], commandType);
            Define.defineArray(string, arrays);
        } else {
            System.out.println("inequality of types");
        }
    }

    public static ArrayList<Integer> findArrayDimensionsDomain(String splitSwitchedArray, int numOfArrayDimension) {
        ArrayList<Integer> dimensions = new ArrayList<>();
        String string = splitSwitchedArray;
        for (int i = 0; i < numOfArrayDimension; i++) {
            dimensions.add(dimensionCounter(string));
            string = deleteLastDimension(string);
        }
        return dimensions;
    }

    public static String deleteLastDimension(String array) {
        return array.replaceAll("\\[[^\\[\\]]*]", "a");
    }

    public static int countArrayDimension(String array) {
        int i = 0;
        while (array.charAt(i) == '[') {
            i++;
        }
        return i;
    }

    public static int dimensionCounter(String array) {
        String string = "";
        Pattern pattern = Pattern.compile("\\[[^\\[\\]]*]");
        Matcher matcher = pattern.matcher(array);
        if (matcher.find(0)) {
            string = matcher.group(0);
        }
        int numOfCommas = countCommas(string);
        return numOfCommas + 1;
    }

    public static int searchIndexOfArrayInArraylist(ArrayList<String> arrays, String nameOfReplacingArray) {
        for (int i = arrays.size() - 1; i >= 0; i--) {
            String[] splitedArray = arrays.get(i).split("\\s?=\\s?");
            String nameOfThisElementOfArray = splitedArray[0];
            if (nameOfReplacingArray.equals(nameOfThisElementOfArray)) {
                return i;
            }
        }
        System.out.println("No array is defined by this name");
        return -1;
    }

    public static int countCommas(String string) {
        int len = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == ',') {
                len++;
            }
        }
        return len;
    }

    public static boolean isCorrectDefine(String command) {
        if (countSpecificCharInString(command, '[') == countSpecificCharInString(command, ']')) {
            return true;
        }
        return false;
    }

    public static int countSpecificCharInString(String string, char specificChar) {
        int numberOfSpecificChar = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == specificChar) {
                numberOfSpecificChar++;
            }
        }
        return numberOfSpecificChar;
    }
}
