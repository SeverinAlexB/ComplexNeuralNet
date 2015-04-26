package Network.Learning;

import Data.ComplexDataset;
import Data.Helper;
import Data.Pairs.ComplexPair;
import Network.Calculation.ICalculationStrategy;
import Network.FeedForwardNet;
import Network.Learning.Propagation.IPropagationStrategy;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

import java.util.Random;

/**
 * Created by Severin on 25.04.2015.
 */
public class CPULearning {
    private FeedForwardNet net;
    private ICalculationStrategy calculation;
    private IPropagationStrategy propagation;
    private ComplexDataset dataset;
    private double sqrerror = 1;

    public CPULearning(FeedForwardNet net, ICalculationStrategy calculation, IPropagationStrategy propagation) {
        this.net = net;
        this.setCalculation(calculation);
        this.setPropagation(propagation);
    }



    public FeedForwardNet getNet() {
        return net;
    }
    public void setNet(FeedForwardNet net) {
        this.net = net;
    }
    public ICalculationStrategy getCalculation() {
        return calculation;
    }
    public void setCalculation(ICalculationStrategy calculation) {
        this.calculation = calculation;
        this.calculation.setNetWork(net);
    }
    public IPropagationStrategy getPropagation() {
        return propagation;
    }
    public void setPropagation(IPropagationStrategy propagation) {
        this.propagation = propagation;
        this.propagation.setNetWork(net);
    }
    public ComplexDataset getDataset() {
        return dataset;
    }
    public void setDataset(ComplexDataset dataset) {
        this.dataset = dataset;
    }
    public double getError(){
        return this.sqrerror;
    }
    private Random random = new Random();
    public void iteration(int count) throws SeviException{
        int id;
        FieldVector<Complex> out, error;
        for(int i = 0; i < count; i++) {
            id = random.nextInt(dataset.getPairs().size());

                ComplexPair pair = dataset.getPairs().get(id);
                FieldVector<Complex> verrauscht = addRauschen(pair.getInput());
                out = calculation.calculate(verrauscht);
                error = pair.getOutput().subtract(out);
                this.setError(error);
                propagation.propagate(error, calculation.getLayerResults());

        }
    }
    private void setError(FieldVector<Complex> error){
        double sum = 0.0;
        for(int i = 0; i < error.getDimension(); i++) {
            double temp = Helper.transform(error.getEntry(i));
            sum += temp*temp;
        }
        this.sqrerror = sum;
    }
    private FieldVector<Complex> addRauschen(FieldVector<Complex> vector){
        FieldVector<Complex> ret = vector.copy();
        for(int i = 0; i < vector.getDimension(); i++){
            Complex temp = ret.getEntry(i).multiply(getRandom());
            ret.setEntry(i,ret.getEntry(i).add(temp));
        }
        return ret;
    }
    private Complex getRandom(){
        double sigma = 0.125;
        double real = 2*sigma* random.nextDouble() - sigma;
        double img = 2*sigma* random.nextDouble() - sigma;
        return new Complex(real,img);
    }
}
