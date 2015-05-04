package Data.Pairs;

import Data.Helper;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public class RealPair {
    private double[] input;
    private double[] target;
    public RealPair(double[] input, double[] target){
        setInput(input);
        setTarget(target);
    }
    public double[] getInput() {
        return input;
    }

    public void setInput(double[] input) {
        this.input = input;
    }

    public double[] getTarget() {
        return target;
    }

    public void setTarget(double[] target) {
        this.target = target;
    }

    public ComplexPair transform() {
        FieldVector<Complex> input = Helper.transformE(this.input);
        FieldVector<Complex> output = Helper.transformE(this.target);
        return new ComplexPair(input,output);
    }

}
