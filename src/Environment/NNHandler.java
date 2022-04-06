package Environment;

import NeuralNetwork.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class NNHandler extends EnvironmentObject {
    int[] dim;
    NeuralNetwork neuralNetwork;

    public NNHandler( Scanner userInput ) {
        super( "Neural Network", userInput );
        constructorLoop();
    }

    void create() {
        System.out.println( "Enter Neural Network Name" );
        name = userInput.nextLine();
        System.out.println( "Enter Neural Network Dimensions" );
        dim = getUserInputIntArr();
        neuralNetwork = new NeuralNetwork( dim );
    }

      void load() {
        System.out.println( "Enter NeuralNetwork Name" );
        name = userInput.nextLine();
        File file = new File( neuralNetworkPath + "\\" + name + ".txt" );
        try {
            Scanner fileScanner = new Scanner( file );
            String[] inString = fileScanner.nextLine().replace( "[", "" ).replace( "]", "" ).split( ", " );
            int[] dim = new int[ inString.length ];
            for ( int i = 0; i < inString.length; i++ )
                dim[ i ] = Integer.parseInt( inString[ i ] );
            neuralNetwork = new NeuralNetwork(dim);
            for ( Layer layer : neuralNetwork.network )
                for ( int i = 0; i < layer.weight.length; i++ ) {
                    inString = fileScanner.nextLine().replace( "[", "" ).replace( "]", "" ).split( ", " );
                    double[] weight = new double[ inString.length ];
                    for ( int j = 0; j < inString.length; j++ )
                        weight[ j ] = Double.parseDouble( inString[ j ] );

                    layer.weight[i] = weight;
                }

        } catch ( Exception e ) {
            System.out.println(e);
            load();
        }
    }

    @Override
    public String display() {
        return "Neural Network " + Arrays.toString( dim );
    }

    @Override
    public void save() {
        try {
            File file = new File( name );
            file.createNewFile();
            FileWriter fileWriter = new FileWriter( neuralNetworkPath + "\\" + name + ".txt" );
            fileWriter.write( Arrays.toString( dim ) + "\n" );
            for ( Layer layer : neuralNetwork.network )
                for ( double[] weights : layer.weight )
                    fileWriter.write(Arrays.toString(weights) + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
