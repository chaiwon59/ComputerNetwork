import java.util.*;

class Solution {

    static int counter = 0;
    static Set<String> duplicate = new HashSet<>();

    // Add a message autentication code to the message using a specific key.
    public static String addMac(String message, String key, int messagelength) {
        counter++;
        String mac = Hash.hash(key + message + counter);
        String result = message + mac + counter;
        return result;
    }


    // Verify a message autentication code to the message given a specific key.
    public static boolean checkMac(String message, String key, int messagelength) {

        if(duplicate.contains(message)){
            return false;
        }

        String message2 = message.substring(0, messagelength);
        String dummyMac = Hash.hash(key + message2 + counter);
        int dummyMacL = dummyMac.length();

        if(message.length() < messagelength + dummyMacL){
            return false;
        }

        String counterA = message.substring(messagelength + dummyMacL);
        String mac2 = Hash.hash(key + message2 + counterA);
        String result = message2 + mac2 + counterA;

        if(message.equals(result)){
            duplicate.add(message);
            return true;
        }
        return false;

    }


    // Forge a MAC based on the timing differences between messages.
    public static String forgeMac(String message) {
        return "";
    }

}