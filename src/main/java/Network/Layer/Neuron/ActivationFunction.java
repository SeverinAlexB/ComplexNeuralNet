package Network.Layer.Neuron;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public enum ActivationFunction {
    Sigmoid;
    public Complex calc(Complex c){
        if(this == Sigmoid) return sigmoid(c);
        else return new Complex(0);
    }
    public FieldVector<Complex> calc(FieldVector<Complex> vec){
        if(this == Sigmoid){
            FieldVector<Complex> ret = vec.copy();
            for(int i = 0; i < ret.getDimension(); i++){
                ret.setEntry(i,calc(ret.getEntry(i)));
            }
            return ret;
        }
        return null;
    }
    private Complex sigmoid(Complex c) {
        c = c.negate();
        c = c.exp();
        c = c.add(Complex.ONE);
        c = c.reciprocal();
        return c;
    }
}
