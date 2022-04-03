package HandWrittenData;
import NeuralNetwork.*;
import java.util.ArrayList;

public class HandwrittenProof {

    static int[] labels = HandWrittenData.labelData();
    static ArrayList<double[]> images = HandWrittenData.imageData(0, 1);
    static NeuralNetwork nn = new NeuralNetwork(new int[]{images.get(0).length, 50, 20, 10});

    public static void main(String[] args) {
        train(6, .001, 10);

        System.out.println(proof());
    }

    public static void train(double epocMag, double rate, int batchSize) {
        double error = 0;
        for (int epoc = 0; epoc < Math.pow(10, epocMag); epoc++) {
            int mod = (int) (Math.random() * labels.length);
            double[] input = images.get(mod);
            double[] expected = new double[10];
            for (int i = 0; i < expected.length; i++)
                expected[i] = -1;
            expected[labels[mod]] = 1;

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
            numIncorrect += (isEqual(expected, output)) ? 0 : 1;
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


}
//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

/*
    public static void printScores(ArrayList<double[]> scoreList) {
        for (int i = 0; i < 16; i++)
            System.out.println((int) scoreList.get(i)[0] + " " + scoreList.get(i)[1]);
    }

    public static ArrayList<double[]> binaryProductSpace(double dimension) {
        ArrayList<double[]> productSpace = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, dimension); i++) {

            double[] output = new double[(int) dimension];
            int remainder = i;
            for (int j = 0; j < dimension; j++) {
                output[j] = (remainder % 2 - 0.5) * 2;
                remainder /= 2;
            }

            productSpace.add(output);
        }
        return productSpace;
    }
}



    //General BON training environment
    public static int trainMethod()
    {
        System.out.println("1: train, 2: score, 3: bias train, 4: randomize bias, 5: reset, 6: script");
        try { switch(sc.nextInt()){
            case 1: trainCommand();
                break;
            case 2: scoreCommand();
                break;
            case 3: biasTrainCommand();
                break;
            case 4: randomizeBiasCommand();
                break;
            case 5: resetCommand();
                break;
            case 6: scriptCommand();
        }}
        catch(Exception e)
        {
            System.out.println("Oops");
        }

        return trainMethod();
    }

    public static void scriptCommand()
    {
        System.out.println("Write Macro");
        sc.nextLine();
        String macro = sc.nextLine();
        System.out.println("loop count");
        int count = sc.nextInt();
        sc = new Scanner(new Script(macro));
        for(; count > 0; count--, trainMethod());
        sc = new Scanner(System.in);
    }

    public static void resetCommand()
    {
        nnc.bm = new BiasManager(new int[]{2, 3, 1}, 0.5, 16);
    }

    public static void scoreCommand() {
        nnc.getScoreList().sort(1);
        for(int i = 0; i < 16; i++)
            System.out.println((int) nnc.score.get(i)[0] + " " + nnc.score.get(i)[1]);
    }

    private static void trainCommand() {
        System.out.println("Int epocMag, Double rate");
        nnc.train(sc.nextInt(), sc.nextDouble());
    }

    private static void biasTrainCommand(){
        System.out.println("Int epocMag, Double rate");
        nnc.trainBias(sc.nextInt(), sc.nextDouble());
    }

    private static void randomizeBiasCommand(){
        nnc.randomizeBias();
    }

    private static class Script extends InputStream {
        String script;
        int index = 0;
        public Script(String inputScript) {
            script = inputScript;
        }
        @Override
        public int read() throws IOException {
            char c = script.charAt(index);
            index = (index + 1) % script.length();
            return c;
        }
    }
}
*/
