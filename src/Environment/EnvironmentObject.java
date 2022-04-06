package Environment;

import NeuralNetwork.NeuralNetwork;

import java.util.Scanner;

public abstract class EnvironmentObject {
    String type;
    String name;
    Scanner userInput;

    String dataLoaderPath = "C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\Environment\\DataLoaders";
    String neuralNetworkPath = "C:\\Users\\Agi\\IdeaProjects\\NeuralNetwork\\src\\Environment\\NeuralNetworks";


    public EnvironmentObject(String type, Scanner userInput) {
        this.type = type;
        this.userInput = userInput;
    }

    public int getUserInputInt() {
        return Integer.parseInt(userInput.nextLine());
    }

    public int[] getUserInputIntArr(){
        int[] toReturn;
        try{
            String[] toParse = userInput.nextLine().split( " " );
            toReturn = new int[toParse.length];
            for ( int i = 0; i < toParse.length; i++ )
                toReturn[ i ] = Integer.parseInt(toParse[ i ] );
        }
        catch(Exception e){
            System.out.println("Invalid Input");
            return getUserInputIntArr();
        }

        return toReturn;
    }

    public double[] getUserInputDoubleArr(){
        double[] toReturn;
        try{
            String[] toParse = userInput.nextLine().split( " " );
            toReturn = new double[toParse.length];
            for ( int i = 0; i < toParse.length; i++ )
                toReturn[ i ] = Double.parseDouble(toParse[ i ] );
        }
        catch(Exception e){
            System.out.println("Invalid Input");
            return getUserInputDoubleArr();
        }

        return toReturn;
    }
    public void constructorLoop(){
        System.out.println( "[create] or [load] " + type);
        switch ( userInput.nextLine() ) {
            case "create":
                create();
                break;
            case "load":
                load();
                break;
            default:
                System.out.println( "Invalid Input" );
                constructorLoop();
                break;
        }
    }

    abstract void create();

    abstract void load();

    public abstract String display();

    public abstract void save();
}
