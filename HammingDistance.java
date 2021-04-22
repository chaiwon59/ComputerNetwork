import java.util.List;

class Solution {

    /**
     * Calculates the hamming distance of the given code, or returns -1 if it cannot be calculated.
     *
     * @param code The code
     * @return The hamming distance of the given code , or -1 if it cannot be calculated
     */
    public static long calculate(List<Long> code) {

        long result;
        long min;
        long hammingDistance = 100;

        if (code.size() == 0 || code.size() == 1 || code == null) {
            return -1;
        }

        for (int i = 0; i < code.size(); i++) {
            for (int j = i + 1; j < code.size(); j++) {
                result = code.get(i) ^ code.get(j);
                min = Long.bitCount(result);
                if (min < hammingDistance) {
                    hammingDistance = min;
                }
            }
        }
        return hammingDistance;
    }
}
