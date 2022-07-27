package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;

public class FloatExecute {
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
        //System.out.println(countArrayDimension(firstElement));
        String result = "";
        if (Main.countArrayDimension(firstElement) == 3) {
            float[][][] firstFloatArray = convertStringTo3DFloatArray(firstElement);
            float[][][] secondFloatArray = convertStringTo3DFloatArray(secondElement);
            result = executeTheCommandFor3DArrays(firstFloatArray, secondFloatArray, commandType);
        } else if (Main.countArrayDimension(firstElement) == 2) {
            float[][] firstFloatArray = convertStringTo2DFloatArray(firstElement);
            float[][] secondFloatArray = convertStringTo2DFloatArray(secondElement);
            result = executeTheCommandFor2DArrays(firstFloatArray, secondFloatArray, commandType);
        } else if (Main.countArrayDimension(firstElement) == 1) {
            float[] firstFloatArray = convertStringTo1DFloatArray(firstElement);
            float[] secondFloatArray = convertStringTo1DFloatArray(secondElement);
            result = executeTheCommandFor1DArrays(firstFloatArray, secondFloatArray, commandType);
        } else {
            System.out.println("can not execute the command");
            return null;
        }
        return result;
    }

    public static String executeTheCommandFor3DArrays(float[][][] first, float[][][] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.deepToString(first), 3);
        int d1 = dimensionDomains.get(2), d2 = dimensionDomains.get(1), d3 = dimensionDomains.get(0);
        float[][][] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoFloatArray(first, second, d1, d2, d3);
                break;
            case SUM:
                resultArray = sumTwoFloatArray(first, second, d1, d2, d3);
                break;
            case SUB:
                resultArray = subTwoFloatArray(first, second, d1, d2, d3);
                break;
            default:
                resultArray = divideTwoFloatArray(first, second, d1, d2, d3);
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

    public static String executeTheCommandFor2DArrays(float[][] first, float[][] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.deepToString(first), 2);
        int d1 = dimensionDomains.get(1), d2 = dimensionDomains.get(0);
        float[][] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoFloatArray(first, second, d1, d2);
                break;
            case SUM:
                resultArray = sumTwoFloatArray(first, second, d1, d2);
                break;
            case SUB:
                resultArray = subTwoFloatArray(first, second, d1, d2);
                break;
            default:
                resultArray = divideTwoFloatArray(first, second, d1, d2);
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

    public static String executeTheCommandFor1DArrays(float[] first, float[] second, CommandType commandType) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(Arrays.toString(first), 1);
        int d1 = dimensionDomains.get(0);
        float[] resultArray;
        switch (commandType) {
            case MULTIPLY:
                resultArray = multiplyTwoFloatArray(first, second, d1);
                break;
            case SUM:
                resultArray = sumTwoFloatArray(first, second, d1);
                break;
            case SUB:
                resultArray = subTwoFloatArray(first, second, d1);
                break;
            default:
                resultArray = divideTwoFloatArray(first, second, d1);
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

    public static float[][][] multiplyTwoFloatArray(float[][][] first, float[][][] second, int d1, int d2, int d3) {
        float[][][] resultArray = new float[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] * second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static float[][][] sumTwoFloatArray(float[][][] first, float[][][] second, int d1, int d2, int d3) {
        float[][][] resultArray = new float[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] + second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static float[][][] subTwoFloatArray(float[][][] first, float[][][] second, int d1, int d2, int d3) {
        float[][][] resultArray = new float[d1][d2][d3];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    resultArray[i][j][k] = first[i][j][k] - second[i][j][k];
                }
            }
        }
        return resultArray;
    }

    public static float[][][] divideTwoFloatArray(float[][][] first, float[][][] second, int d1, int d2, int d3) {
        float[][][] resultArray = new float[d1][d2][d3];
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

    public static float[] multiplyTwoFloatArray(float[] first, float[] second, int d1) {
        float[] resultArray = new float[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] * second[i];
        }
        return resultArray;
    }

    public static float[] sumTwoFloatArray(float[] first, float[] second, int d1) {
        float[] resultArray = new float[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] + second[i];
        }
        return resultArray;
    }

    public static float[] subTwoFloatArray(float[] first, float[] second, int d1) {
        float[] resultArray = new float[d1];
        for (int i = 0; i < d1; i++) {
            resultArray[i] = first[i] - second[i];
        }
        return resultArray;
    }

    public static float[] divideTwoFloatArray(float[] first, float[] second, int d1) {
        float[] resultArray = new float[d1];
        for (int i = 0; i < d1; i++) {
            if (second[i] != 0) {
                resultArray[i] = first[i] / second[i];
            }
        }
        return resultArray;
    }

    public static float[][] multiplyTwoFloatArray(float[][] first, float[][] second, int d1, int d2) {
        float[][] resultArray = new float[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] * second[i][j];
            }
        }
        return resultArray;
    }

    public static float[][] sumTwoFloatArray(float[][] first, float[][] second, int d1, int d2) {
        float[][] resultArray = new float[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] + second[i][j];
            }
        }
        return resultArray;
    }

    public static float[][] subTwoFloatArray(float[][] first, float[][] second, int d1, int d2) {
        float[][] resultArray = new float[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                resultArray[i][j] = first[i][j] - second[i][j];
            }
        }
        return resultArray;
    }

    public static float[][] divideTwoFloatArray(float[][] first, float[][] second, int d1, int d2) {
        float[][] resultArray = new float[d1][d2];
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                if (second[i][j] != 0) {
                    resultArray[i][j] = first[i][j] / second[i][j];
                }
            }
        }
        return resultArray;
    }

    public static float[][][] convertStringTo3DFloatArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 3);
        int d1 = dimensionDomains.get(2), d2 = dimensionDomains.get(1), d3 = dimensionDomains.get(0);
        float[][][] floatArray = new float[d1][d2][d3];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        float[] arrayElementsWithoutWhiteSpace = new float[d1 * d2 * d3];
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Float.parseFloat(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                for (int k = 0; k < d3; k++) {
                    floatArray[i][j][k] = arrayElementsWithoutWhiteSpace[p];
                    p++;
                }
            }
        }
        return floatArray;
    }

    public static float[][] convertStringTo2DFloatArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 2);
        int d1 = dimensionDomains.get(1), d2 = dimensionDomains.get(0);
        float[][] floatArray = new float[d1][d2];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        float[] arrayElementsWithoutWhiteSpace = new float[d1 * d2];
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Float.parseFloat(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            for (int j = 0; j < d2; j++) {
                floatArray[i][j] = arrayElementsWithoutWhiteSpace[p];
                p++;
            }
        }
        return floatArray;
    }

    public static float[] convertStringTo1DFloatArray(String string) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(string, 1);
        int d1 = dimensionDomains.get(0);
        float[] floatArray = new float[d1];
        String[] arrayElements = string.split("(\\[)|(,)|(])");
        float[] arrayElementsWithoutWhiteSpace = new float[d1];
        for (int i = 0, p = 0; i < arrayElements.length; i++) {
            if (!arrayElements[i].equals("")) {
                arrayElementsWithoutWhiteSpace[p] = Float.parseFloat(arrayElements[i]);
                p++;
            }
        }
        int p = 0;
        for (int i = 0; i < d1; i++) {
            floatArray[i] = arrayElementsWithoutWhiteSpace[p];
            p++;
        }
        return floatArray;
    }
}
