package Environment;

import java.util.ArrayList;
import java.util.Scanner;

public class Environment {
    Scanner userInput;
    ArrayList<EnvironmentObject> environmentObjects;

    public Environment() {
        userInput = new Scanner( System.in );
        environmentObjects = new ArrayList<>();
        System.out.println( "Environment Initializer" );
        environmentObjects.add(new NNHandler(userInput));
        environmentObjects.add(new DataLoader(userInput));
    }

    /**
     * IO loop for Environment.
     */
    public void cycle() {
        displayObjects();
        promptUser();
        cycle();
    }

    private void promptUser() {
        System.out.println("[train] [load] [save]");
        switch(userInput.nextLine()) {
            case "train":
                train();
                break;
            case "load":
                load();
                break;
            case "save":
                save();
                break;
        }
    }

    private void train() {
        System.out.println("Enter numbers for Neural Network and Data Loader");
        String[] inString = userInput.nextLine().split(" ");
        EnvironmentObject neuralNetwork = environmentObjects.get(Integer.parseInt(inString[0]) - 1);
        EnvironmentObject dataLoader = environmentObjects.get(Integer.parseInt(inString[1]) - 1);

        if(neuralNetwork.type != "nn" || dataLoader.type != "dl"){
            System.out.println("Invalid Input");
            train();
            return;
        }

        

    }
    private void load() {
        System.out.println("Environment load is not implemented");
    }
    private void save() {
        System.out.println("Enter number for save");
        int objectSelection = Integer.parseInt(userInput.nextLine()) - 1;
        environmentObjects.get(objectSelection).save();
    }

    private void displayObjects() {
        int counter = 1;
        for ( EnvironmentObject environmentObject : environmentObjects )
            System.out.println(counter++ + " " + environmentObject.display() );
    }
}
