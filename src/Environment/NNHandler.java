package Environment;

import NeuralNetwork.NeuralNetwork;

import java.util.Scanner;


public class NNHandler {
    static NeuralNetwork neuralNetwork;
    static DataLoader dataLoader;
    static Scanner sc = new Scanner( System.in );

    public NNHandler( NeuralNetwork neuralNetwork ) {
        neuralNetwork = neuralNetwork;
        dataLoader = new DataLoader();
    }

    public static void handle() {
        System.out.println( "test or train" );
        String choice = sc.nextLine();

        switch ( choice ) {
            case "test":
                test();
                break;
            case "train":
                train();
                break;
        }
    }

    static void test() {
        int numUnderThresh = 0;
        for( InputOutputPair iop : dataLoader ) {
            double[] computedOutput = neuralNetwork.calc(iop.input);
            double distance = distance(computedOutput, iop.output);
            if(distance < dataLoader.threshold)
                numUnderThresh++;
        }
        System.out.println(numUnderThresh + " " + dataLoader.size());
    }

    static double distance( double[] computedOutput, double[] output ) {
        double distance = 0;
        for ( int i = 0; i < output.length; i++ )
            distance += (computedOutput[i] - output[i]) * (computedOutput[i] - output[i]);
        return distance;
    }

    static void train() {
        double error = 0;
        int traingingExamples;
        for ( InputOutputPair iop : dataLoader ) {
            error += neuralNetwork.back(iop.input, iop.output);
            if()
        }

    }
}
