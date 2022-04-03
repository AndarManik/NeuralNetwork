package Environment;

import NeuralNetwork.*;

import java.util.Scanner;

public class NNLoader {
    static Scanner sc = new Scanner(System.in);
    public static NeuralNetwork load(){
        System.out.println("Enter Network Dimensions Space Separated");
        System.out.print("DIM: ");
        String stringDim = sc.nextLine();
        String[] str = stringDim.split(" ");
        int[] dim = new int[str.length];
        for(int i = 0; i < str.length; i++)
            dim[i] = Integer.parseInt(str[i]);
        return new NeuralNetwork(dim);
    }
}
