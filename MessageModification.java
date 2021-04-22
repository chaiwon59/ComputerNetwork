import java.math.BigInteger;
class Solution {

    // Modify the message to contain your own account in the encrypted message.
    public static String modifyMessage(String message, String targetAccount, String yourAccount) {

        BigInteger target = new BigInteger(targetAccount, 16);
        BigInteger yours = new BigInteger(yourAccount, 16);
        BigInteger messageInt = new BigInteger(message, 16);

        BigInteger result = target.xor(yours);
        String resultStr = result.toString(16);

        String withZeros = resultStr + "00000000";

        BigInteger zeros = new BigInteger(withZeros, 16);

        BigInteger result2 = messageInt.xor(zeros);

        String result22 = result2.toString(16);

        return result22;



    }


}