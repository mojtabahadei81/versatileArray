package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;

public class MultiplyMatrices {
    public static void multiplyMatrices(String command, ArrayList<String> arrays) {
        String[] splitedCommand = splitMultiplyMatricesCommand(command);
        int indexOfFirstElement = Main.searchIndexOfArrayInArraylist(arrays, splitedCommand[1]);
        int indexOfSecondElement = Main.searchIndexOfArrayInArraylist(arrays, splitedCommand[2]);
        String firstElement = arrays.get(indexOfFirstElement);
        String secondElement = arrays.get(indexOfSecondElement);
        String firstElementArray = firstElement.split("\\s?=\\s?")[1];
        String secondElementArray = secondElement.split("\\s?=\\s?")[1];
        String firstElementType = IntExecute.findArrayType(firstElementArray);
        String secondElementType = IntExecute.findArrayType(secondElementArray);
        if (Main.countArrayDimension(firstElementArray) != 2 || Main.countArrayDimension(secondElementArray) != 2) {
            System.out.println("wrong dimension");
            return;
        }
        if (firstElementType.equals("int") && secondElementType.equals("int")) {
            String multiplyAnswer = multiplyIntegerMatrices(firstElementArray, secondElementArray);
            String result = splitedCommand[0] + "=" + multiplyAnswer;
            Define.defineArray(result, arrays);
        } else if (firstElementType.equals("float") && secondElementType.equals("float")) {
            String multiplyAnswer = multiplyFloatMatrices(firstElementArray, secondElementArray);
            String result = splitedCommand[0] + "=" + multiplyAnswer;
            Define.defineArray(result, arrays);
        } else {
            System.out.println("wrong type");
            return;
        }
    }

    public static String multiplyIntegerMatrices(String firstStringArray, String secondStringArray) {
        int[][] firstIntArray = IntExecute.convertStringTo2DIntArray(firstStringArray);
        int[][] secondIntArray = IntExecute.convertStringTo2DIntArray(secondStringArray);
        ArrayList<Integer> firstArrayDomains = Main.findArrayDimensionsDomain(firstStringArray, 2);
        ArrayList<Integer> secondArrayDomains = Main.findArrayDimensionsDomain(secondStringArray, 2);
        int col1 = firstArrayDomains.get(0), row1 = firstArrayDomains.get(1);
        int col2 = secondArrayDomains.get(0), row2 = secondArrayDomains.get(1);
        if (row2 != col1) {
            System.out.println("incompatible dimension");
            return null;
        }
        int[][] result = new int[row1][col2];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < row2; k++) {
                    result[i][j] += firstIntArray[i][k] * secondIntArray[k][j];
                }
            }
        }
        String resultString = Arrays.deepToString(result);
        String string2 = "";
        for (int i = 0; i < resultString.length(); i++) {
            if (resultString.charAt(i) != ' ') {
                string2 += resultString.charAt(i);
            }
        }
        return string2;
    }

    public static String multiplyFloatMatrices(String firstStringArray, String secondStringArray) {
        float[][] firstIntArray = FloatExecute.convertStringTo2DFloatArray(firstStringArray);
        float[][] secondIntArray = FloatExecute.convertStringTo2DFloatArray(secondStringArray);
        ArrayList<Integer> firstArrayDomains = Main.findArrayDimensionsDomain(firstStringArray, 2);
        ArrayList<Integer> secondArrayDomains = Main.findArrayDimensionsDomain(secondStringArray, 2);
        int col1 = firstArrayDomains.get(0), row1 = firstArrayDomains.get(1);
        int col2 = secondArrayDomains.get(0), row2 = secondArrayDomains.get(1);
        if (row2 != col1) {
            System.out.println("incompatible dimension");
            return null;
        }
        float[][] result = new float[row1][col2];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < row2; k++) {
                    result[i][j] += firstIntArray[i][k] * secondIntArray[k][j];
                }
            }
        }
        String resultString = Arrays.deepToString(result);
        String string2 = "";
        for (int i = 0; i < resultString.length(); i++) {
            if (resultString.charAt(i) != ' ') {
                string2 += resultString.charAt(i);
            }
        }
        return string2;
    }

    public static String[] splitMultiplyMatricesCommand(String command) {
        String[] splitedCommand = new String[4];
        String[] splitByEqualSign = command.split("\\s?=\\s?");
        String[] splitByElementWiseSign = new String[2];
        splitByElementWiseSign = splitByEqualSign[1].split("\\s?@\\s?");
        splitedCommand[3] = "@";
        splitedCommand[0] = splitByEqualSign[0];
        splitedCommand[1] = splitByElementWiseSign[0];
        splitedCommand[2] = splitByElementWiseSign[1];
        return splitedCommand;
    }
}
