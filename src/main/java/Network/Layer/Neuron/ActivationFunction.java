package Network.Layer.Neuron;

import org.apache.commons.math3.complex.Complex;

/**
 * Created by Severin on 24.04.2015.
 */
public enum ActivationFunction {
    Sigmoid;
    public String parse(){
        if(this == Sigmoid) return "Sigmoid";
        else return "not implemented";
    }
    public Complex calc(Complex c){
        if(this == Sigmoid) return sigmoid(c);
        else return new Complex(0);
    }
    public Complex calc(Complex c, int derivativ){
        Complex ret = sigmoid(c);
        for(int i = 0; i < derivativ; i++) {
            ret = ret.multiply((new Complex(1).subtract(ret)));
        }
        return ret;
    }
    private static final Complex one = new Complex(1);
    private Complex sigmoid(Complex c) {
        c = c.negate();
        c = c.exp();
        c = c.add(one);
        c = c.reciprocal();
        return c;
    }
}
