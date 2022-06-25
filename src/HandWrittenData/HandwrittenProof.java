package HandWrittenData;
import NeuralNetwork.*;
import java.util.ArrayList;
import java.util.Arrays;

public class HandwrittenProof {

    static int[] labels = HandWrittenData.labelData();
    static ArrayList<double[]> images = HandWrittenData.imageData(0, 1);
    static NeuralNetwork nn = new NeuralNetwork(new int[]{images.get(0).length, 50, 20, 10});

    static double epocMag = 6.5;
    static double rate = .001;
    static int batchSize = 10;

    public static void main(String[] args) {
        train(epocMag, rate, batchSize);

        System.out.println("proof() = " + proof());
        System.out.println("HandWrittenData.testLabelData().length = " + HandWrittenData.testLabelData().length);
    }

    public static void train(double epocMag, double rate, int batchSize) {
        double error = 0;
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            int imageIndex = (int) (Math.random() * labels.length);
            double[] input = images.get(imageIndex);
            double[] expected = new double[10];
            Arrays.fill(expected, -1);
            expected[labels[imageIndex]] = 1;

            error += nn.back(input, expected);

            if (epoc % batchSize == batchSize - 1) {
                nn.update(rate);
                System.out.println(rate + " " + error);
                error = 0;
            }
        }
        nn.clear();
    }

    public static int proof() {
        int[] labels = HandWrittenData.testLabelData();
        ArrayList<double[]> images = HandWrittenData.testImages(0,1);
        int numIncorrect = 0;

        for (int index = 0; index < labels.length; index++) {
            double[] image = images.get(index);
            double[] expected = oneHot(labels[index]);
            double[] output = oneHot(nn.calc(image));
            printImage(image);
            boolean isCorrect = isEqual(expected, output);
            System.out.println(inverseOneHot(output) + " "  + isCorrect + "\n");
            numIncorrect += (isCorrect) ? 0 : 1;
        }
        return numIncorrect;
    }

    private static boolean isEqual(double[] expected, double[] output) {
        for (int i = 0; i < Math.min(expected.length, output.length); i++)
            if(expected[i] != output[i])
                return false;
        return true;
    }

    private static void printImage(double[] image) {
        String greyScale =  " +*#%@";
        for (int i = 0; i < image.length; i++) {
            System.out.print(greyScale.charAt((int) (image[i] * greyScale.length())) + " ");
            if(i % 28 == 0)
                System.out.println();
        }
        System.out.println();
    }

    private static double[] oneHot(double[] input) {
        int indexMax = 0;
        for (int i = 0; i < input.length; i++)
            if(input[indexMax] < input[i])
                indexMax = i;
        return oneHot(indexMax);
    }

    private static double[] oneHot(int label) {
        double[] oneHot = new double[10];
        for (int i = 0; i < 10; i++)
            oneHot[i] = -1;
        oneHot[label] = 1;
        return oneHot;
    }

    private static int inverseOneHot(double[] input){
        for (int i = 0; i < input.length; i++) {
            if(input[i] == 1)
                return i;
        }
        return -1;
    }
}
