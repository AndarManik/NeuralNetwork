package NeuralNetwork;
import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
    public ArrayList<Layer> network = new ArrayList<>();

    public NeuralNetwork( int[] dim ) {
        for ( int layer = 1; layer < dim.length; layer++ )
            network.add( new Layer( dim[ layer ], dim[ layer - 1 ], new Tanh() ) );
    }

    public NeuralNetwork( int[] dim, Activation hiddenActivation, Activation outputActivation ) {
        for ( int layer = 1; layer < dim.length - 1; layer++ )
            network.add( new Layer( dim[ layer ], dim[ layer - 1 ], hiddenActivation) );
        network.add(new Layer(dim[dim.length - 1], dim[dim.length - 2], outputActivation));
    }

    public double[] calc( double[] input ) {
        for ( Layer layer : network )
            input = layer.calc( input );
        return input;
    }

    public double back( double[] input, double[] expected ) {
        double[] gradient = getOutputGradient( input, expected );
        backPassGradient( gradient );
        forwardPassWeightGradient( input );
        return getNetworkError( expected );
    }

    double[] getOutputGradient( double[] input, double[] expected ) {
        double[] outputGradient = calc( input );
        for ( int i = 0; i < outputGradient.length; i++ )
            outputGradient[ i ] -= expected[ i ];
        return outputGradient;
    }

    void backPassGradient( double[] gradient ) {
        for ( int layer = network.size() - 1; layer >= 0; layer-- )
            gradient = network.get( layer ).back( gradient );
    }

    void forwardPassWeightGradient( double[] input ) {
        for ( Layer value : network )
            input = value.weightGrad( input );
    }

    double getNetworkError( double[] expected ) {
        double error = 0;
        double[] output = network.get( network.size() - 1 ).val;
        for ( int i = 0; i < expected.length; i++ )
            error += Math.abs( output[ i ] - expected[ i ] );
        return error;
    }

    public void update( double rate ) {
        for ( Layer l : network )
            l.update( rate );
    }

    public void clear() {
        for ( Layer l : network ) {
            l.grad = new double[ l.grad.length ][ l.grad[ 0 ].length ];
            l.biasGrad = new double[ l.biasGrad.length ];
        }
    }

    public void setLayerActivation( int index, Activation activation ) {
        network.get( index ).activation = activation;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Weights").append("\n");
        for (Layer l : network) {
            for (double[] d : l.weight)
                sb.append(Arrays.toString(d)).append(" ").append(d[0] / d[1]).append(" ").append("\n");
            sb.append("\n");
        }
        sb.append("Biases").append("\n");
        for (Layer l : network) {
            double[] d = l.bias;
            sb.append(Arrays.toString(d)).append(" ").append(d[0] / d[1]).append(" ").append("\n");
        }
        return sb.toString();
    }

}