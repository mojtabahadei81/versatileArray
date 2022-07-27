package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Define {
    public static void defineArray(String command, ArrayList<String> arrays) {
        if (isACorrectArray(command)) {
            if (command.matches(".*\".*\".*")) {
                arrays.add(command);
                printTheArray(command, "\"");
            } else if (command.matches(".*'.'.*")) {
                arrays.add(command);
                printTheArray(command, "'");
            } else if (command.matches("[^.]*[0-9]*[^.]*")) {
                arrays.add(command);
                printTheArray(command, "");
            } else if (command.matches(".*([0-9]*?\\.[0-9]+).*|.*([0-9]+\\.[0-9]*?).*")) {
                command = convertFloatToCorrectFloat(command);
                arrays.add(command);
                printTheArray(command, "");
            }
        }
    }

    public static String convertFloatToCorrectFloat(String array) {
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(array);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; matcher.find(i); ) {
            if (matcher.find(i)) {
                strings.add(matcher.group(0));
            }
            i = matcher.end();
        }
        String[] strings2 = addZeroToEndOrStartOfElements(strings);
        String[] strings3 = array.split("\\[[^\\[&^\\]]*]");
        String printArray = "";
        int j = 0;
        for (j = 0; j < strings2.length; j++) {
            printArray += strings3[j];
            printArray += strings2[j];
            if (j == strings2.length - 1 && j > 0) {
                printArray += strings3[j + 1];
            }
        }
        return printArray;
    }

    public static String[] addZeroToEndOrStartOfElements(ArrayList<String> strings) {
        String[] strings2 = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            String[] splitSubString = strings.get(i).split("(\\[)|(,)|(])");
            strings2[i] = "[";
            for (int j = 0; j < splitSubString.length; j++) {
                if (splitSubString[j].matches("\\.[0-9]+")) {
                    splitSubString[j] = "0" + splitSubString[j];
                } else if (splitSubString[j].matches("[0-9]+\\.")) {
                    splitSubString[j] = splitSubString[j] + "0";
                }
                strings2[i] += splitSubString[j];
                if (j != splitSubString.length - 1 && j != 0) {
                    strings2[i] += ",";
                }
            }
            strings2[i] += "]";
        }
        return strings2;
    }

    public static void printTheArray(String array, String regex) {
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(array);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; matcher.find(i); ) {
            if (matcher.find(i)) {
                strings.add(matcher.group(0));
            }
            i = matcher.end();
        }
        String[] strings2 = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            strings2[i] = strings.get(i).replaceAll(",", " ");
        }
        String[] strings3 = array.split("\\[[^\\[&^\\]]*]");
        String printArray = "";
        int j = 0;
        for (j = 0; j < strings2.length; j++) {
            printArray += strings3[j];
            printArray += strings2[j];
            if (j == strings2.length - 1 && j > 0) {
                printArray += strings3[j + 1];
            }
        }
        printArray = printArray.replaceAll(",", "");
        printArray = printArray.replaceAll(regex, "");
        String[] printArray2 = printArray.split("\\s?=\\s?");
        System.out.println(printArray2[1]);
    }

    public static boolean isACorrectArray(String command) {
        if (areTypesUnknown(command)) {
            System.out.println("unknown type");
            return false;
        } else if (areTypesInequal(command)) {
            System.out.println("inequality of types");
            return false;
        } else if (areLensOrShapesDifferent(command)) {
            System.out.println("different lengths or shapes");
            return false;
        } else {
            return true;
        }
    }

    public static boolean areTypesUnknown(String command) {
        String[] string = command.split("\\s?=\\s?");
        String subString = string[1].replaceAll("\\[|]", ",");
        String[] splitedPartsOfCommand = subString.split(",");
        ArrayList<String> substringPartsWithoutSpace = new ArrayList<>();
        for (int i = 0; i < splitedPartsOfCommand.length; i++) {
            if (!splitedPartsOfCommand[i].equals("")) {
                substringPartsWithoutSpace.add(splitedPartsOfCommand[i]);
            }
        }
        for (int i = 0; i < substringPartsWithoutSpace.size(); i++) {
            String checkString = substringPartsWithoutSpace.get(i);
            if (!isStringInRangeOfCorrectType(checkString)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringInRangeOfCorrectType(String checkString) {
        if (checkString.matches("\".*\"")) {
            return true;
        } else if (checkString.matches("'.'")) {
            return true;
        } else if (checkString.matches("-?[0-9]*")) {
            return true;
        } else if (checkString.matches("-?([0-9]*?\\.[0-9]+)|([0-9]+\\.[0-9]*?)")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean areTypesInequal(String command) {
        String[] string = command.split("\\s?=\\s?");
        String string2 = string[1].replaceAll("\\[|]", ",");
        String[] splitedPartsOfCommand = string2.split(",");
        ArrayList<String> splitedPartsOfCommandWithoutSpace = new ArrayList<>();
        for (int i = 0; i < splitedPartsOfCommand.length; i++) {
            if (!splitedPartsOfCommand[i].equals("")) {
                splitedPartsOfCommandWithoutSpace.add(splitedPartsOfCommand[i]);
            }
        }
        if (splitedPartsOfCommandWithoutSpace.get(0).matches("\".*\"")) {
            return checkInequalTypes(splitedPartsOfCommandWithoutSpace, "\".*\"");
        } else if (splitedPartsOfCommandWithoutSpace.get(0).matches("'.'")) {
            return checkInequalTypes(splitedPartsOfCommandWithoutSpace, "'.'");
        } else if (splitedPartsOfCommandWithoutSpace.get(0).matches("-?([0-9]*?\\.[0-9]+)|([0-9]+\\.[0-9]*?)")) {
            String regex = "-?([0-9]*?\\.[0-9]+)|([0-9]+\\.[0-9]*?)";
            return checkInequalTypes(splitedPartsOfCommandWithoutSpace, regex);
        } else if (splitedPartsOfCommandWithoutSpace.get(0).matches("-?[0-9]*")) {
            return checkInequalTypes(splitedPartsOfCommandWithoutSpace, "-?[0-9]*");
        }
        return false;
    }

    public static boolean checkInequalTypes(ArrayList<String> splitedPartsOfCommand, String regex) {
        for (int i = 1; i < splitedPartsOfCommand.size(); i++) {
            if (!splitedPartsOfCommand.get(i).matches(regex)) {
                return true;
            }
        }
        return false;
    }

    public static boolean areLensOrShapesDifferent(String command) {
        String string = command;
        Pattern pattern = Pattern.compile("\\[[^\\[&^\\]]*]");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find(0)) {
            string = deleteLastDimension(matcher, string);
            if (string == null) {
                return true;
            }
            if (areInequalShapes(string)) {
                return true;
            }
            matcher = pattern.matcher(string);
        }
        return false;
    }

    public static boolean areInequalShapes(String string) {
        String[] splitedString = string.split("(\\[)|(,)|(])");
        ArrayList<String> splitedStringWithoutWhiteSpace = new ArrayList<>();
        for (int i = 1; i < splitedString.length; i++) {
            if (!splitedString[i].equals("")) {
                splitedStringWithoutWhiteSpace.add(splitedString[i]);
            }
        }
        String[] splitedStringWithoutWhiteSpace2 = new String[splitedStringWithoutWhiteSpace.size()];
        for (int i = 0; i < splitedStringWithoutWhiteSpace.size(); i++) {
            splitedStringWithoutWhiteSpace2[i] = splitedStringWithoutWhiteSpace.get(i);
        }
        for (int i = 0; i < splitedStringWithoutWhiteSpace2.length; i++) {
            if (!splitedStringWithoutWhiteSpace2[i].equals("a")) {
                return true;
            }
        }
        return false;
    }

    public static String deleteLastDimension(Matcher matcher, String string) {
        String string2;
        int numOfCommas = countCommas(matcher.group(0));
        for (int i = 0; matcher.find(i); ) {
            if (matcher.find(i)) {
                string2 = matcher.group(0);
                if (countCommas(string2) != numOfCommas) {
                    return null;
                }
            }
            i = matcher.end();
        }
        string2 = string.replaceAll("\\[[^\\[&^\\]]*]", "a");
        return string2;
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
}
