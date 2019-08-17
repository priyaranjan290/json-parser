import java.util.Map;

import java.util.*;


enum ObjectType {
    STRING, NUMBER, CUSTOM_OBJECT
}

class CustomPair {
    public ObjectType objectType;

    // can be of type STRING, NUMBER, CUSTOM_OBJECT
    public Object object;

    public CustomPair(ObjectType objectType, Object object) {
        this.objectType = objectType;
        this.object = object;
    }
}


class CustomInt {
   public Integer integer;

   public CustomInt(Integer integer) {
       this.integer = integer;
   }
}

public class Main {

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */


    public static ObjectType getCustomJsonType(String string) {

            if (string.charAt(0) == '\'' && string.charAt(string.length() -1) == '\'') {
                return ObjectType.STRING;
            }


            if (string.charAt(0) == '{' && string.charAt(string.length() -1) == '}') {
                return ObjectType.CUSTOM_OBJECT;
            }

            return ObjectType.NUMBER;
        }

    public static CustomPair parseJson(String input) throws Exception {

            CustomInt currentIndex = new CustomInt(0);
            return getCustomPair(input, currentIndex, 0);

        }

    private static String findValue(String input, CustomInt currIndex, int level) {
        String retVal = "";

        int currLevel = level;
        while (currIndex.integer < input.length()) {
            Character c = input.charAt(currIndex.integer);

            if ('{' == c) {
                currLevel = currLevel + 1;
                retVal = retVal.concat(String.valueOf(c));
            }

            else if ('}' == c) {

                if (currLevel == level) {
                    break;
                }

                currLevel = currLevel - 1;
                retVal = retVal.concat(String.valueOf(c));

            } else if (',' == c) {

                if (currLevel == level) {
                    break;
                }

            }  else {
                retVal = retVal.concat(String.valueOf(c));
            }


            currIndex.integer = currIndex.integer + 1;

        }

        return retVal;
    }

    private static String findKey(String input, CustomInt currIndex, int level) {

        String retVal = "";
        int currLevel = level;

        while (currIndex.integer < input.length()) {
            Character c = input.charAt(currIndex.integer);

            if ('{' == c) {
                currLevel = currLevel + 1;
                retVal = retVal.concat(String.valueOf(c));
            }

            else if ('}' == c) {

                if (currLevel == level) {
                    break;
                }

                currLevel = currLevel - 1;
                retVal = retVal.concat(String.valueOf(c));

            } else if (':' == c) {

                if (currLevel == level) {
                    break;
                }

            } else {
                retVal = retVal.concat(String.valueOf(c));
            }


            currIndex.integer = currIndex.integer + 1;

        }

        return retVal;

    }

    private static CustomPair getCustomPair(String input, CustomInt currIndex, int level) throws Exception {

        String currKey = null;
        String currValue = null;
        Map<String, CustomPair> map  = new HashMap<>();


        while (currIndex.integer < input.length()) {

            Character c = input.charAt(currIndex.integer);


            if ('{' == c) {

                currIndex.integer = currIndex.integer + 1 ;
                currKey = findKey(input, currIndex, level);
                currIndex.integer = currIndex.integer - 1 ;

            }

            else if (':' == c) {
                currIndex.integer = currIndex.integer + 1 ;
                currValue = findValue(input, currIndex, level);
                currIndex.integer = currIndex.integer - 1 ;
            }

            else if (',' == c) {

                ObjectType customJsonType = getCustomJsonType(currValue);

                switch (customJsonType) {
                    case CUSTOM_OBJECT:
                        map.put(currKey, getCustomPair(currValue, new CustomInt(0), level + 1));
                        break;


                    default:
                        map.put(currKey, new CustomPair(customJsonType, currValue));
                        break;
                }

                currIndex.integer = currIndex.integer + 1 ;
                currKey = findKey(input, currIndex, level);
                currIndex.integer = currIndex.integer - 1 ;

            }

            else if ('}' == c) {

                ObjectType customJsonType = getCustomJsonType(currValue);

                switch (customJsonType) {
                    case CUSTOM_OBJECT:
                        map.put(currKey, getCustomPair(currValue, new CustomInt(0), level + 1));
                        break;


                    default:
                        map.put(currKey, new CustomPair(customJsonType, currValue));
                        break;
                }

                return new CustomPair(ObjectType.CUSTOM_OBJECT, map);


            }

            currIndex.integer = currIndex.integer + 1 ;

        }

        throw new Exception("error parsing!");

    }





    public static void main(String[] args) throws Exception {

            // list to hold the input arrays of string
            List<String> inputStringArray = new ArrayList<>();
            inputStringArray.add("{a:'b'}");
            inputStringArray.add("{a:b}");
            inputStringArray.add("{a:123,b:'123'}");
            inputStringArray.add("{a:{b:c}}");
            inputStringArray.add("{a:{b:c},r:{ab:{bg:123}}}");


            // result Map
            Map<String, CustomPair> outputMap = new HashMap<>();

            for (String inputString : inputStringArray) {
                if (!outputMap.containsKey(inputString)) {
                    outputMap.put(inputString, parseJson(inputString));
                }
            }

            System.out.println("ok here!");


        }

}
