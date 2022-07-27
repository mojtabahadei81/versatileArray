package ir.ac.kntu;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacing {
    public static void replacing(String replaceCommand, ArrayList<String> arrays) {
        String[] splitedReplaceCommand = splitReplaceCommand(replaceCommand);
        int indexOfArrayInArraylist = Main.searchIndexOfArrayInArraylist(arrays, splitedReplaceCommand[0]);
        if (indexOfArrayInArraylist == -1) {
            return;
        }
        String switchedArray = arrays.get(indexOfArrayInArraylist);
        String[] splitSwitchedArray = switchedArray.split("\\s?=\\s?");
        int numOfReplaceCommandDimensions = splitedReplaceCommand[1].length() / 3;
        int[] changeElement = new int[numOfReplaceCommandDimensions];
        int numOfArrayDimension = Main.countArrayDimension(splitSwitchedArray[1]);
        if (isInBounds(changeElement, splitedReplaceCommand[1], splitSwitchedArray[1])) {
            if (numOfArrayDimension == 3) {
                String string = replace3DArray(splitSwitchedArray, changeElement, splitedReplaceCommand[2]);
                Define.defineArray(string, arrays);
            } else if (numOfArrayDimension == 2) {
                String string = replacing2DArray(splitSwitchedArray, changeElement, splitedReplaceCommand[2]);
                Define.defineArray(string, arrays);
            } else if (numOfArrayDimension == 1) {
                String string = replacing1DArray(splitSwitchedArray, changeElement, splitedReplaceCommand[2]);
                Define.defineArray(string, arrays);
            } else {
                System.out.println("can not replace it");
            }
        }
    }

    public static String replace3DArray(String[] splitSwitchedArray, int[] changeElement, String alternativeElement) {
        String string = findSwitchedLastDimensionAndReplaceAlternateElement(changeElement, splitSwitchedArray[1], alternativeElement);
        String string2 = stick3DArrayPartsAndReplaceSwitchedDimension(changeElement, splitSwitchedArray[1], string);
        String string3 = splitSwitchedArray[0];
        string3 += "=";
        string3 += string2;
        return string3;
    }

    public static String findSwitchedLastDimensionAndReplaceAlternateElement(int[] changeElement, String array, String alternativeElement) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(array, 3);
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(array);
        int i = 0, j = 0, k = 0;
        String switchedLastDimension = "";
        while (matcher.find(i)) {
            if (j == dimensionDomains.get(1)) {
                k++;
                j = 0;
            }
            if (j == changeElement[1] && k == changeElement[0]) {
                switchedLastDimension = matcher.group();
                break;
            }
            j++;
            i = matcher.end();
        }
        String[] splitLastDimension = switchedLastDimension.split("(\\[)|(,)|(])");
        for (int t = 1; t < splitLastDimension.length; t++) {
            if ((t - 1) == changeElement[2]) {
                splitLastDimension[t] = alternativeElement;
            }
        }
        String string = stickArrayLastDimensionParts(splitLastDimension, 1, dimensionDomains.get(0));
        return string;
    }

    public static String stick3DArrayPartsAndReplaceSwitchedDimension(int[] changeElement, String array, String alternativeDimension) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(array, 3);
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(array);
        int i = 0, j = 0, k = 0;
        int f = 0;
        String string2 = "[[";
        while (matcher.find(i)) {
            if (j == dimensionDomains.get(1)) {
                string2 += "],[";
                f = 0;
                k++;
                j = 0;
            }
            if (j == changeElement[1] && k == changeElement[0]) {
                string2 += alternativeDimension;
            } else {
                string2 += matcher.group(0);
            }
            j++;
            i = matcher.end();
            if (matcher.find(i) && f == 0) {
                string2 += ",";
            }
            f = 1;
        }
        string2 += "]]";
        return string2;
    }

    public static String replacing2DArray(String[] splitSwitchedArray, int[] changeElement, String alternativeElement) {
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(splitSwitchedArray[1], 2);
        int end = dimensionDomains.get(0);
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(splitSwitchedArray[1]);
        int i = 0, j = 0;
        String replacedLastDimension;
        String string = "[";
        while (matcher.find(i)) {
            if (j == changeElement[0]) {
                replacedLastDimension = replaceLastDimension(matcher.group(), changeElement[1], alternativeElement, end);
                string += replacedLastDimension;
            } else {
                string += matcher.group();
            }
            i = matcher.end();
            if (matcher.find(i)) {
                string += ",";
            }
            j++;
        }
        string += "]";
        String string2 = splitSwitchedArray[0];
        string2 += "=";
        string2 += string;
        return string2;
    }

    public static String replacing1DArray(String[] splitSwitchedArray, int[] changeElement, String alternativeElement) {
        int end = Main.countCommas(splitSwitchedArray[1]) + 1;
        String replacedLastDimension = replaceLastDimension(splitSwitchedArray[1], changeElement[0], alternativeElement, end);
        String string = splitSwitchedArray[0];
        string += "=";
        string += replacedLastDimension;
        return string;
    }

    public static String replaceLastDimension(String lastDimension, int changeElement, String alternativeElement, int end) {
        String[] splitLastDimension = lastDimension.split("(\\[)|(,)|(])");
        for (int t = 1; t < splitLastDimension.length; t++) {
            if ((t - 1) == changeElement) {
                splitLastDimension[t] = alternativeElement;
            }
        }
        return stickArrayLastDimensionParts(splitLastDimension, 1, end);
    }

    public static String stickArrayLastDimensionParts(String[] splitedString, int start, int end) {
        String string = "[";
        for (int t = start; t < end + start; t++) {
            string += splitedString[t];
            if (t != end) {
                string += ",";
            }
        }
        string += "]";
        return string;
    }

    public static boolean isInBounds(int[] changeElement, String splitedReplaceCommand, String splitSwitchedArray) {
        for (int i = 1, j = 0; i < splitedReplaceCommand.length(); i += 3, j++) {
            changeElement[j] = (int) (splitedReplaceCommand.charAt(i) - '0');
        }
        int numOfArrayDimension = Main.countArrayDimension(splitSwitchedArray);
        ArrayList<Integer> dimensionDomains = Main.findArrayDimensionsDomain(splitSwitchedArray, numOfArrayDimension);
        if (dimensionDomains.size() != changeElement.length) {
            System.out.println("out of bounds");
            return false;
        }
        for (int i = 0, j = dimensionDomains.size() - 1; i < dimensionDomains.size(); i++, j--) {
            if (dimensionDomains.get(i) <= changeElement[j]) {
                System.out.println("out of bounds");
                return false;
            }
        }
        return true;
    }

    public static String[] splitReplaceCommand(String replaceCommand) {
        String[] splitedReplaceCommand = new String[3];
        Pattern pattern = Pattern.compile("\\s?[a-zA-Z][a-zA-Z0-9_]*\\s?");
        Matcher matcher = pattern.matcher(replaceCommand);
        if (matcher.find(0)) {
            splitedReplaceCommand[0] = matcher.group(0);
            splitedReplaceCommand[0] = splitedReplaceCommand[0].trim();
        }
        String[] splitor = replaceCommand.split("\\s?=\\s?");
        String[] splitor2 = splitor[0].split("\\s?[a-zA-Z][a-zA-Z0-9_]*\\s?");
        splitedReplaceCommand[2] = splitor[1];
        splitedReplaceCommand[1] = splitor2[1];
        return splitedReplaceCommand;
    }
}
