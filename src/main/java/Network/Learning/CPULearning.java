package Network.Learning;

import Data.ComplexDataset;
import Data.Pairs.Helper.ComplexValue;
import Data.Pairs.ComplexPair;
import Data.Pairs.SectorPair;
import Data.SectorDataset;
import Network.Learning.Calculation.ICalculationStrategy;
import Network.FeedForwardNet;
import Network.Learning.Calculation.SingleThreadCalculation;
import Network.Learning.Propagation.IPropagationStrategy;
import Network.Learning.Propagation.SingleThreadPropagation;
import Network.SectorHelper;
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
    private SectorDataset dataset;
    private double sqrerror = 1;

    public CPULearning(FeedForwardNet net, ICalculationStrategy calculation, IPropagationStrategy propagation) {
        this.net = net;
        this.calculation = calculation;
        this.propagation = propagation;
    }
    public CPULearning(FeedForwardNet net) {
        this.net = net;
        this.calculation = new SingleThreadCalculation(net);
        this.propagation = new SingleThreadPropagation(net);
    }

    public FeedForwardNet getNet() {
        return net;
    }
    public ICalculationStrategy getCalculation() {
        return calculation;
    }
    public IPropagationStrategy getPropagation() {
        return propagation;
    }
    public SectorDataset getDataset() {
        return dataset;
    }
    public void setDataset(SectorDataset dataset) {
        this.dataset = dataset.copy();
        if(net.getLayers().get(1).hasBias()){
            this.dataset.addBiasToInputs();
        }
    }
    public double getError(){
        return this.sqrerror;
    }
    private void setError(FieldVector<Complex> error){
        double sum = 0.0;
        for(int i = 0; i < error.getDimension(); i++) {
            double temp = new ComplexValue(error.getEntry(i)).getReal();
            sum += temp*temp;
        }
        this.sqrerror = sum;
    }


    private Random random = new Random();
    public void iteration(int count) throws SeviException{
        int id;
        FieldVector<Complex> out, error, round, should;
        for(int i = 0; i < count; i++) {
            id = random.nextInt(dataset.getPairs().size());
            SectorPair pair = dataset.getPairs().get(id);

            calculation.calculate(pair.getInput());
            out = calculation.getLast().getNetin();
            round = SectorHelper.roundDownToSector(out, net.getSectorsCount());
            should = pair.getTarget().ebeMultiply(round);
            error = should.subtract(out);

            this.setError(error);
            propagation.propagate(error, calculation.getLast());
        }
    }


}
