class Solution {

    /**
     * Calculates the CRC check value, or -1 if it cannot be calculated.
     *
     * @param bitSequence The input bit sequence
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence
     * @return The CRC check value
     */
    public static long calculateCRC(long bitSequence, int inputLength, long generatorSequence) {

        long generatorLength = calculateLength(generatorSequence);

        if(inputLength <= 0) {
            return -1;
        }

        if (inputLength < generatorLength) {
            return -1;
        }

        long numOfZerosAtBit = generatorLength - 1;

        bitSequence = bitSequence << numOfZerosAtBit;

        long numOfZerosAtGen = inputLength - generatorLength + numOfZerosAtBit;

        generatorSequence = generatorSequence << numOfZerosAtGen;


        long remainder;
        remainder = bitSequence;
        int remainderLength = calculateLength(remainder);
        long generatorLength2 = calculateLength(generatorSequence);

        while(remainderLength > numOfZerosAtBit) {

            if(generatorLength2 > remainderLength){
                generatorSequence = generatorSequence >> 1;
                generatorLength2 = calculateLength(generatorSequence);
                remainderLength = calculateLength(remainder);
            }

            remainder = remainder ^ generatorSequence;
            generatorLength2 = calculateLength(generatorSequence);
            remainderLength = calculateLength(remainder);

        }
        return remainder;
    }

    public static int calculateLength(long bit) {
        int length = (int) Math.floor(Math.log(bit) / Math.log(2)) + 1;
        return length;
    }


    /**
     * Checks the correctness of the bit sequence.
     *
     * @param bitSequence The CRC bit sequence including the CRC check value
     * @param inputLength The length of the input bit sequence (including possible leading zeros)
     * @param generatorSequence The generator bit sequence used
     * @param checkSequence The CRC check value to check against
     * @return true if the sequence is correct, false otherwise
     */
    public static boolean checkCRC(long bitSequence, int inputLength, long generatorSequence, long checkSequence) {

        long remainder = calculateCRC(bitSequence, inputLength, generatorSequence);
        return remainder == checkSequence;

    }
}
