package Data.Pairs;

import Data.Helper;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;
import org.apache.commons.math3.linear.MatrixUtils;

/**
 * Created by Severin on 24.04.2015.
 */
public class RealPair {
    private double[] input;
    private double[] output;
    public RealPair(double[] input, double[] output){
        setInput(input);
        setOutput(output);
    }
    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getOutput() {
        return output;
    }

    public void setOutput(double[] output) {
        this.output = output;
    }

    public ComplexPair transform() {
        FieldVector<Complex> input = Helper.transform(this.input);
        FieldVector<Complex> output = Helper.transform(this.output);
        return new ComplexPair(input,output);
    }

}
