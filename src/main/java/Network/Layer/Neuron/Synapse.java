package Network.Layer.Neuron;

import Data.Helper;
import org.apache.commons.math3.complex.Complex;

import java.util.Random;

/**
 * Created by Severin on 24.04.2015.
 */
public class Synapse {
    private Complex weight;
    private Neuron input;
    private Neuron output;

    public Synapse(Neuron input, Neuron output){
        this.input = input;
        this.output = output;
        weight = getRandomWeight();
    }
    public Complex getWeight(){
        return weight;
    }
    public void setWeight(Complex weight){
        this.weight = weight;
    }

    public Neuron getInput() {
        return input;
    }
    public void setInput(Neuron input) {
        this.input = input;
    }

    public Neuron getOutput() {
        return output;
    }
    public void setOutput(Neuron output) {
        this.output = output;
    }

    private Complex getRandomWeight() {
        double min = 0; double max = 0.9;
        double real = getRandom(min,max);
        double img = getRandom(min,max);
        //return new Complex(real,img);
        return Helper.transform(real);
    }

    private Random random = new Random();
    private double getRandom(double min, double max){
        return min +(max - min)* random.nextDouble();

    }
}
