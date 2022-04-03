package Environment;
import NeuralNetwork.NeuralNetwork;

import java.util.*;

public class Environment {
    static NNHandler nnh;
    public static void main( String[] args ) {
        cycle();
    }

    static void cycle(){
        if(nnh == null)
            nnh = new NNHandler(NNLoader.load());
        NNHandler.handle();
        cycle();
    }
}
