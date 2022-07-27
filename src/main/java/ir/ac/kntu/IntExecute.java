package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;

public class IntExecute {
    public static String findResultOfCommand(String firstElementArray, String secondElementArray, String name, CommandType commandType) {
        ArrayList<Integer> d1 = Main.findArrayDimensionsDomain(firstElementArray, 3);
        ArrayList<Integer> d2 = Main.findArrayDimensionsDomain(secondElementArray, 3);
        if (Main.countArrayDimension(firstElementArray) != Main.countArrayDimension(secondElementArray)) {
            System.out.println("inequality of dimensions");
            return null;
        }
        for (int i = 0; (i < 3) && (d1.get(i) != null); i++) {
            if (d1.get(i) != d2.get(i)) {
                System.out.println("inequality of sizes");
                return null;
            }
        }
        String result = executeCommand(firstElementArray, secondElementArray, commandType);
        String string = name;
        string += "=";
        string += result;
        return string;
    }

    public static String executeCommand(String firstElement, String secondElement, CommandType commandType) {
        String result = "";
        if (Main.countArrayDimension(firstElement) == 3) {
            int[][][] firstIntArray = convertStringTo3DIntArray(firstElement);
            int[][][] secondIntArray = convertStringTo3DIntArray(secondElement);
            result = executeTheCommandFor3DArrays(firstIntArray, secondIntArray, commandType);
        } else if (Main.countArrayDimension(firstElement) == 2) {
            int[][] firstIntArray = convertStringTo2DIntArray(firstElement);
            int[][] secondIntArray = convertStringTo2DIntArray(secondElement);
            result = executeTheCommandFor2DArrays(firstIntArray, secondIntArray, commandType);
        } else if (Main.countArrayDimension(firstElement) == 1) {
            int[] firstIntArray = convertStringTo1DIntArray(firstElement);
            int[] secondIntArray = convertStringTo1DIntArray(secondElement);
            result = executeTheCommandFor1DArrays(firstIntArray, secondIntArray, commandType);
        } else {
            System.out.println("can not execute the command");
            return null;
        }
        return result;
    }

    public static String executeTheCommandFor3DArrays(int[][][] first, int[][][] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.deepToString(first), 3);
        int d1 = dimensionDomains.get(2), d2 = dimensionDomains.get(1), d3 = dimensionDomains.get(0);
        int[][][] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoIntArray(first, second, d1, d2, d3);
                break;
            case SUM:
                resultArray = sumTwoIntArray(first, second, d1, d2, d3);
                break;
            case SUB:
                resultArray = subTwoIntArray(first, second, d1, d2, d3);
                break;
            default:
                resultArray = divideTwoIntArray(first, second, d1, d2, d3);
                break;
        }
        String string2 = Arrays.deepToString(resultArray);
        String string3 = "";
        for (int i = 0; i < string2.length(); i++) {
            if (string2.charAt(i) != ' ') {
                string3 += string2.charAt(i);
            }
        }
        return string3;
    }

    public static String executeTheCommandFor2DArrays(int[][] first, int[][] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.deepToString(first), 2);
        int d1 = dimensionDomains.get(1), d2 = dimensionDomains.get(0);
        int[][] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoIntArray(first, second, d1, d2);
                break;
            case SUM:
                resultArray = sumTwoIntArray(first, second, d1, d2);
                break;
            case SUB:
                resultArray = subTwoIntArray(first, second, d1, d2);
                break;
            default:
                resultArray = divideTwoIntArray(first, second, d1, d2);
                break;
        }
        String string2 = Arrays.deepToString(resultArray);
        String string3 = "";
        for (int i = 0; i < string2.length(); i++) {
            if (string2.charAt(i) != ' ') {
                string3 += string2.charAt(i);
            }
        }
        return string3;
    }

    public static String executeTheCommandFor1DArrays(int[] first, int[] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.toString(first), 1);
        int d1 = dimensionDomains.get(0);
        int[] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoIntArray(first, second, d1);
                break;
            case SUM:
                resultArray = sumTwoIntArray(first, second, d1);
                break;
            case SUB:
                resultArray = subTwoIntArray(first, second, d1);
                break;
            default:
                resultArray = divideTwoIntArray(first, second, d1);
                break;
        }
        String string2 = Arrays.toString(resultArray);
        String string3 = "";
        for (int i = 0; i < string2.length(); i++) {
            if (string2.charAt(i) != ' ') {
                string3 += string2.charAt(i);
            }
        }
        return string3;
    }

    public static int[][][] multiplyTwoIntArray(int[][][] first, int[][][] second, int d1, int d2, int d3) {
        int[][][] resultArray = new int[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] * second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static int[][][] sumTwoIntArray(int[][][] first, int[][][] second, int d1, int d2, int d3) {
        int[][][] resultArray = new int[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] + second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static int[][][] subTwoIntArray(int[][][] first, int[][][] second, int d1, int d2, int d3) {
        int[][][] resultArray = new int[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] - second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static int[][][] divideTwoIntArray(int[][][] first, int[][][] second, int d1, int d2, int d3) {
        int[][][] resultArray = new int[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    if (second[i][j][k] != 0) {
                        resultArray[i][j][k] = first[i][j][k] / second[i][j][k];
                    }
                }
            }
        }
        return resultArray;
    }

    public static int[] multiplyTwoIntArray(int[] first, int[] second, int d1) {
        int[] resultArray = new int[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] * second[i];
        }
        return resultArray;
    }

    public static int[] sumTwoIntArray(int[] first, int[] second, int d1) {
        int[] resultArray = new int[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] + second[i];
        }
        return resultArray;
    }

    public static int[] subTwoIntArray(int[] first, int[] second, int d1) {
        int[] resultArray = new int[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] - second[i];
        }
        return resultArray;
    }

    public static int[] divideTwoIntArray(int[] first, int[] second, int d1) {
        int[] resultArray = new int[d1];
        for (int i = 0; i < d1; i++) {
            if (second[i] != 0) {
                resultArray[i] = first[i] / second[i];
            }
        }
        return resultArray;
    }

    public static int[][] multiplyTwoIntArray(int[][] first, int[][] second, int d1, int d2) {
        int[][] resultArray = new int[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] * second[i][j];
            }
        }
        return resultArray;
    }

    public static int[][] sumTwoIntArray(int[][] first, int[][] second, int d1, int d2) {
        int[][] resultArray = new int[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] + second[i][j];
            }
        }
        return resultArray;
    }

    public static int[][] subTwoIntArray(int[][] first, int[][] second, int d1, int d2) {
        int[][] resultArray = new int[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] - second[i][j];
            }
        }
        return resultArray;
    }

    public static int[][] divideTwoIntArray(int[][] first, int[][] second, int d1, int d2) {
        int[][] resultArray = new int[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                if (second[i][j] != 0) {
                    resultArray[i][j] = first[i][j] / second[i][j];
                }
            }
        }
        return resultArray;
    }

    public static int[][][] convertStringTo3DIntArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 3);
        int d1 = dimensionDomains.get(2), d2 = dimensionDomains.get(1), d3 = dimensionDomains.get(0);
        int[][][] intArray = new int[d1][d2][d3];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        int[] arrayElementsWithoutWhiteSpace = new int[d1 * d2 * d3];
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Integer.parseInt(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    intArray[i][j][k] = arrayElementsWithoutWhiteSpace[p];
                    p++;
                }
            }
        }
        return intArray;
    }

    public static int[][] convertStringTo2DIntArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 2);
        int d1 = dimensionDomains.get(1), d2 = dimensionDomains.get(0);
        int[][] intArray = new int[d1][d2];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        int[] arrayElementsWithoutWhiteSpace = new int[d1 * d2];
        //arrayElementsWithoutWhiteSpace = arrayElements;
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Integer.parseInt(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                intArray[i][j] = arrayElementsWithoutWhiteSpace[p];
                p++;
            }
        }
        return intArray;
    }

    public static int[] convertStringTo1DIntArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 1);
        int d1 = dimensionDomains.get(0);
        int[] intArray = new int[d1];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        int[] arrayElementsWithoutWhiteSpace = new int[d1];
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Integer.parseInt(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            intArray[i] = arrayElementsWithoutWhiteSpace[p];
            p++;
        }
        return intArray;
    }

    public static String findArrayType(String string) {
        if (string.matches("[^.]*[0-9]*[^.]*")) {
            return "int";
        } else if (string.matches(".*([0-9]*?\\.[0-9]+).*|.*([0-9]+\\.[0-9]*?).*")) {
            return "float";
        } else {
            System.out.println("wrong type");
        }
        return null;
    }

    public static String[] splitCommand(String multiplyCommand, CommandType commandType) {
        String[] splitedCommand = new String[4];
        String[] splitByEqualSign = multiplyCommand.split("\\s?=\\s?");
        String[] splitByElementWiseSign = new String[2];
        if (commandType == CommandType.MULTIPLY) {
            splitByElementWiseSign = splitByEqualSign[1].split("\\s?\\*\\s?");
            splitedCommand[3] = "*";
        } else if (commandType == CommandType.SUM) {
            splitByElementWiseSign = splitByEqualSign[1].split("\\s?\\+\\s?");
            splitedCommand[3] = "+";
        } else if (commandType == CommandType.SUB) {
            splitByElementWiseSign = splitByEqualSign[1].split("\\s?-\\s?");
            splitedCommand[3] = "-";
        } else if (commandType == CommandType.DIVISION) {
            splitByElementWiseSign = splitByEqualSign[1].split("\\s?/\\s?");
            splitedCommand[3] = "/";
        }
        splitedCommand[0] = splitByEqualSign[0];
        splitedCommand[1] = splitByElementWiseSign[0];
        splitedCommand[2] = splitByElementWiseSign[1];
        return splitedCommand;
    }
}
