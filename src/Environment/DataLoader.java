package Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DataLoader extends EnvironmentObject {
    ArrayList<DataPair> data = new ArrayList<>();

    public DataLoader( Scanner userInput ) {
        super( "Data Loader", userInput );
        constructorLoop();
    }

    void create() {
        System.out.println( "Enter Data Loader Name" );
        name = userInput.nextLine();
        System.out.println( "Enter number of entries" );
        int entries = getUserInputInt();
        for ( int i = 0; i < entries; i++ ) {
            System.out.println( "Enter Input" );
            double[] inputData = getUserInputDoubleArr();
            System.out.println( "Enter output" );
            double[] outputData = getUserInputDoubleArr();
            data.add( new DataPair( inputData, outputData ) );
        }
    }

    void load() {
        System.out.println( "Enter Data Loader Name" );
        name = userInput.nextLine();
        File file = new File( dataLoaderPath + "\\" + name + ".txt" );
        try {
            Scanner fileScanner = new Scanner( file );
            while (fileScanner.hasNext()) {

                String[] inString = fileScanner.nextLine().replace( "[", "" ).replace( "]", "" ).split( ", " );
                double[] input = new double[ inString.length ];
                for ( int i = 0; i < inString.length; i++ )
                    input[ i ] = Double.parseDouble( inString[ i ] );

                inString = fileScanner.nextLine().replace( "[", "" ).replace( "]", "" ).split( ", " );
                double[] output = new double[ inString.length ];
                for ( int i = 0; i < inString.length; i++ )
                    output[ i ] = Double.parseDouble( inString[ i ] );

                data.add( new DataPair( input, output ) );
            }
        } catch ( Exception e ) {
            System.out.println(e);
            load();
        }

    }

    @Override
    public void save() {
        try {
            File file = new File( name );
            file.createNewFile();
            FileWriter fileWriter = new FileWriter( dataLoaderPath + "\\" + name + ".txt" );
            for ( DataPair d : data )
                fileWriter.write( Arrays.toString( d.input ) + "\n" + Arrays.toString( d.output ) + "\n" );
            fileWriter.flush();
            fileWriter.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public String display() {
        return "Data Loader " + name;
    }
}
