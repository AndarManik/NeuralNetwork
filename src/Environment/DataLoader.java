package Environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class DataLoader implements Iterable<InputOutputPair>{
    static Scanner sc = new Scanner( System.in );
    public double threshold;
    ArrayList<InputOutputPair> inputOutputPairs;
    int inputSize, outputSize;
    public DataLoader() {
        System.out.println("HandWrittenDigits");
        String choice = sc.nextLine();
    }

    @Override
    public Iterator iterator() {
        return new DataLoaderIterator();
    }

    public int size() {
        return inputOutputPairs.size();
    }

    public class DataLoaderIterator implements Iterator<InputOutputPair> {
        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public InputOutputPair next() {
            return inputOutputPairs.get( index++ );
        }
    }
}
