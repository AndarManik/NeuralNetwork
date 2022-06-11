package NeuralNetwork;

import java.util.ArrayList;

public class TrainBooleanOperatorNetworkProof {

    static final int NUMBER_OF_TRIES_FOR_FAILURE = 1000;
    static final double MAX_ERROR_FOR_SUCCESS = 0.2;
    static final boolean PRINT_ERRORS = true;
    static final int MAGNITUDE_OF_EPOCHS = 5;
    static final double RATE = 0.1;
    static final ArrayList<double[]> INPUT_SPACE = binaryProductSpace(2);
    static final ArrayList<double[]> OUTPUT_SPACE = binaryProductSpace(4);
    static BiasManager globalPointerNeuralNetwork;
    static Activation hiddenActivation = new Tanh();
    static Activation outputActivation = new Tanh();
    public static void main(String[] args) {
        System.out.println(works());
    }
    /**
     * Trains a new initialization of a Bias Manager a number of times
     * @return      True if the total error of a single try is less than 0.5
     *              False if it does not return true after 'TRIES' times
     */
    private static boolean works() {
        for (int i = 0; i < NUMBER_OF_TRIES_FOR_FAILURE; i++)
            if (train(MAGNITUDE_OF_EPOCHS, RATE) < MAX_ERROR_FOR_SUCCESS)
                return true;
        return false;
    }

    /**
     * initialize a new bnn and train it epocMag times
     * return the final error of the bnn
     * @param epocMag   10^epocMag number of epoc iterations
     * @param rate      learning rate for the bnn
     * @return          final error of the bnn
     */
    private static double train(double epocMag, double rate) {
        globalPointerNeuralNetwork = new BiasManager(new int[]{2, 3, 1}, 16, hiddenActivation, outputActivation);
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++)
            singlePass(rate, epoc % 16);
        return proof();
    }

    /**
     * a single pass through an entire task space
     * @param rate  learning rate for the bnn
     * @param task  which task and biases to use on the bnn
     */
    private static void singlePass(double rate, int task) {
        globalPointerNeuralNetwork.setBias(task);
        double[] currentOp = OUTPUT_SPACE.get(task);
        for (int i = 0; i < INPUT_SPACE.size(); i++)
            globalPointerNeuralNetwork.back(INPUT_SPACE.get(i), new double[]{currentOp[i]});
        globalPointerNeuralNetwork.update(rate);
    }

    //Proof=============================================================
    //Proof=============================================================
    //Proof=============================================================

    /**
     * Sums of the errors of the network at its current state
     * @return  Sum of errors
     */
    private static double proof() {
        double sum = 0;
        for (double d : getScoreList())
            sum += d;
        if(PRINT_ERRORS)
            System.out.println(sum);
        return sum;
    }

    /**
     * Computes the error of the Bias Manager for every tasks
     * @return  Array storing the errors for every task
     */
    private static double[] getScoreList() {
        double[] scoreList = new double[16];
        for (int task = 0; task < 16; task++)
            scoreList[task] = getTaskError(task);
        return scoreList;
    }

    /**
     * Computes the error for a single task
     * @param task  Current task for the Bias Manager
     * @return      Error for the current task
     */
    private static double getTaskError(int task) {
        globalPointerNeuralNetwork.setBias(task);
        double[] currentOp = OUTPUT_SPACE.get(task);
        double error = 0;
        for (int i = 0; i < currentOp.length; i++)
            error += Math.abs((globalPointerNeuralNetwork.calc(INPUT_SPACE.get(i))[0] - currentOp[i]));
        return error;
    }


    //Data Generation====================================================
    //Data Generation====================================================
    //Data Generation====================================================


    /**
     * Computes an ArrayList which stores every binary number of a certain digit count
     * @param dimension Number of digits
     * @return          ArrayList of Double[] which contain the individual binary digits
     */
    public static ArrayList<double[]> binaryProductSpace(double dimension) {
        ArrayList<double[]> productSpace = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, dimension); i++)
            productSpace.add(getBinaryRepresentation(dimension, i));
        return productSpace;
    }

    /**
     * Convert number to binary and stores the digits in an array
     * @param dimension Number of digits
     * @param number    Current number to be converted
     * @return          Double[] which stores the individual digits
     */
    private static double[] getBinaryRepresentation(double dimension, int number) {
        double[] output = new double[(int) dimension];
        int remainder = number;
        for (int j = 0; j < dimension; j++, remainder /= 2)
            output[j] = (remainder % 2 - 0.5) * 2;
        return output;
    }
}