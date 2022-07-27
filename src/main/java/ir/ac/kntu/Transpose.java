package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Transpose {
    public static void transpose(String command, ArrayList<String> arrays) {
        String[] splitedCommand = splitTransposeCommand(command);
        int indexOfElement = Main.searchIndexOfArrayInArraylist(arrays, splitedCommand[1]);
        if (indexOfElement == -1) {
            return;
        }
        String element = arrays.get(indexOfElement);
        String elementArray = element.split("\\s?=\\s?")[1];
        String elementType = IntExecute.findArrayType(elementArray);
        if (Main.countArrayDimension(elementArray) != 2) {
            System.out.println("wrong dimension");
            return;
        }
        if (elementType.equals("int")) {
            String transposeAnswer = transposeIntMatrix(elementArray);
            String result = splitedCommand[0] + "=" + transposeAnswer;
            Define.defineArray(result, arrays);
        } else if (elementType.equals("float")) {
            String transposeAnswer = transposeFloatMatrix(elementArray);
            String result = splitedCommand[0] + "=" + transposeAnswer;
            Define.defineArray(result, arrays);
        } else {
            System.out.println("wrong type");
        }
    }

    public static String transposeFloatMatrix(String stringArray) {
        float[][] intArray = FloatExecute.convertStringTo2DFloatArray(stringArray);
        ArrayList<Integer> arrayDimensionsDomain = Main.findArrayDimensionsDomain(stringArray, 2);
        int transposeCol = arrayDimensionsDomain.get(1), transposeRow = arrayDimensionsDomain.get(0);
        float[][] transposeArray = new float[transposeRow][transposeCol];
        for (int i = 0; i < arrayDimensionsDomain.get(1); i++) {
            for (int j = 0; j < arrayDimensionsDomain.get(0); j++) {
                transposeArray[j][i] = intArray[i][j];
            }
        }
        String resultString = Arrays.deepToString(transposeArray);
        String string2 = "";
        for (int i = 0; i < resultString.length(); i++) {
            if (resultString.charAt(i) != ' ') {
                string2 += resultString.charAt(i);
            }
        }
        return string2;
    }

    public static String transposeIntMatrix(String stringArray) {
        int[][] intArray = IntExecute.convertStringTo2DIntArray(stringArray);
        ArrayList<Integer> arrayDimensionsDomain = Main.findArrayDimensionsDomain(stringArray, 2);
        int transposeCol = arrayDimensionsDomain.get(1), transposeRow = arrayDimensionsDomain.get(0);
        int[][] transposeArray = new int[transposeRow][transposeCol];
        for (int i = 0; i < arrayDimensionsDomain.get(1); i++) {
            for (int j = 0; j < arrayDimensionsDomain.get(0); j++) {
                transposeArray[j][i] = intArray[i][j];
            }
        }
        String resultString = Arrays.deepToString(transposeArray);
        String string2 = "";
        for (int i = 0; i < resultString.length(); i++) {
            if (resultString.charAt(i) != ' ') {
                string2 += resultString.charAt(i);
            }
        }
        return string2;
    }

    public static String[] splitTransposeCommand(String command) {
        String[] splitedCommand = new String[3];
        String[] splitByEqualSign = command.split("\\s?=\\s?");
        Pattern pattern = Pattern.compile("[a-zA-Z]+[0-9]*");
        Matcher matcher = pattern.matcher(splitByEqualSign[1]);
        if (matcher.find()) {
            splitedCommand[1] = matcher.group();
        }
        splitedCommand[2] = "&";
        splitedCommand[0] = splitByEqualSign[0];
        return splitedCommand;
    }
}
