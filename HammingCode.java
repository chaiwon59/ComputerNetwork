class Solution {

    /**
     * Calculates the hamming code of the given bit sequence.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The Hamming code sequence
     */
    public static long calcHamming(long bitSequence, int inputLength, boolean isEvenParity) {

        int numOfParity = 0;
        int totalLength;
        long resultBit = 0;
        int actualCounter = (inputLength - 1);
        long bitCounter = 0;
        long parityBit = 0;

        while ((inputLength + numOfParity + 1) > Math.pow(2, numOfParity)) {
            numOfParity++;
        }


        totalLength = inputLength + numOfParity;


        while (actualCounter >= 0) {
            for (int i = 1; i <= totalLength; i++) {
                if (isPowerOfTwo(i) == true) {
                    resultBit = resultBit << 1;
                } else {
                    if (getBit(shiftBit(bitSequence, actualCounter), 0) == true) {
                        resultBit = resultBit << 1;
                        resultBit |= 1L;
                        actualCounter--;
                    } else {
                        resultBit = resultBit << 1;
                        actualCounter--;
                    }
                }
            }
        }

        long coverageBit = resultBit;
        long countBit = resultBit;
        int count = 0;
        int counter = (int)Math.pow(2,count);

        while(counter <= totalLength) {
            long positions = 0;
            for (int j = counter; j <= totalLength; j += 2 * counter) {
                for (int k = j; k < j + counter; k++) {
                    if(k > totalLength){
                        continue;
                    }
                    positions |= (1L << (totalLength - k));
                }
            }

            countBit = coverageBit & positions;

            bitCounter = Long.bitCount(countBit);

            if (isEvenParity) {
                if ((bitCounter % 2) != 0) {
                    parityBit = 1L;
                }else{
                    parityBit = 0;
                }
            }
            if (isEvenParity == false) {
                if ((bitCounter % 2) == 0) {
                    parityBit = 1L;
                }else{
                    parityBit = 0;
                }
            }
            resultBit |= parityBit << (totalLength-counter);

            count++;
            counter = (int)Math.pow(2, count);

        }
        return resultBit;
    }


    public static boolean getBit(long n, long i){
        return ((n & (1 << i)) != 0);
    }

    public static boolean isPowerOfTwo(int n){
        return ((n & (n-1)) == 0 || n == 1);
    }

    public static long shiftBit(long n, int p){
        return n >> p;
    }



    /**
     * Returns the corrected (if needed) hamming code of the given bit sequence.
     *
     * @param bitSequence The Hamming code bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param isEvenParity Boolean indicating if the hamming algorithm should use even parity or not
     * @return The correct Hamming code sequence
     */
    public static long checkHamming(long bitSequence, int inputLength, boolean isEvenParity) {

        long coverageBit = bitSequence;
        long countBit = bitSequence;
        int count = 0;
        int counter = (int)Math.pow(2,count);
        int bitCounter;
        long fixingBit = 0;
        long numOfParity = 0;

        while ((inputLength + numOfParity + 1) > Math.pow(2, numOfParity) - 1) {
            numOfParity++;
        }


        System.out.println("no. parity before " + numOfParity);

        while(counter <= inputLength && numOfParity >= 0) {

            long positions = 0;
            for (int j = counter; j <= inputLength; j += 2 * counter) {
                for (int k = j; k < j + counter; k++) {
                    if(k > inputLength){
                        continue;
                    }
                    positions |= (1L << (inputLength - k));
                }
            }

            countBit = coverageBit & positions;

            bitCounter = Long.bitCount(countBit);


            if (isEvenParity) {
                if ((bitCounter % 2) != 0){
                    fixingBit = fixingBit + counter;
                }
            }
            if (isEvenParity == false) {
                if ((bitCounter % 2) == 0){
                    fixingBit = fixingBit + counter;
                }
            }

            numOfParity--;
            count++;
            counter = (int) Math.pow(2, count);

        }


        if(fixingBit == 0){
            return bitSequence;
        }


        long fixed = bitSequence ^ 1L << (inputLength - fixingBit);

        return fixed;

    }
}

