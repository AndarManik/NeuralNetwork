package HandWrittenData;

import java.util.ArrayList;
import java.util.List;

public class HandWrittenData {
    public static int[] labelData() {
        return MnistReader.getLabels("C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\HandWrittenData\\train-labels.idx1-ubyte");//insert location of this file here
    }

    public static ArrayList<double[]> imageData(double min, double max) {
        List<int[][]> imageinput = MnistReader.getImages("C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\HandWrittenData\\train-images.idx3-ubyte");//insert location of this file here
        return convert3dto2d(imageinput, min, max);
    }

    public static int[] testLabelData() {
        return MnistReader.getLabels("C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\HandWrittenData\\t10k-labels.idx1-ubyte");//insert location of this file here
    }

    public static ArrayList<double[]> testImages(double min, double max) {
        ArrayList<int[][]> imageinput = (ArrayList<int[][]>) MnistReader.getImages("C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\HandWrittenData\\t10k-images.idx3-ubyte");//insert location of this file here
        return convert3dto2d(imageinput, min, max);
    }

    public static ArrayList<double[]> convert3dto2d(List<int[][]> input, double min, double max)
    {
        ArrayList<double[]> output = new ArrayList<>();
        for(int[][] image: input)
            output.add(flattenNorm(image, min, max));
        return output;
    }

    //[0,256] -> [-1, 1]
    private static double[] flattenNorm(int[][] image, double min, double max) {
        double[] cur = new double[image.length * image[0].length];
        for(int j = 0; j < image.length; j++)
            for(int k = 0; k < image[0].length; k++) {
                double intervalValue = image[j][k] / 256.0;
                double minRat = 1 - intervalValue;
                double maxRat = intervalValue;
                cur[j * image[0].length + k] = min * minRat + max * maxRat;
            }
        return cur;
    }
}
