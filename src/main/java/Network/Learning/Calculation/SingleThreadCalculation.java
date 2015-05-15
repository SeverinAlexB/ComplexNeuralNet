package Network.Learning.Calculation;

import Network.FeedForwardNet;
import Network.Layer.Layer;
import Network.SectorHelper;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadCalculation implements ICalculationStrategy {
    private FeedForwardNet net;
    private LayerResult first;
    private LayerResult last;

    public SingleThreadCalculation(FeedForwardNet net){
        this.setNetWork(net);
    }


    public void setNetWork(FeedForwardNet net) {
        this.net = net;
    }
    public FeedForwardNet getNetWork() {
        return this.net;
    }

    public FieldVector<Complex> calculate(FieldVector<Complex> input) throws SeviException{
        this.first = new LayerResult(input);
        LayerResult current = first;
        for(int i = 0; i < net.getLayers().size() -1; i++) {
            Layer l = net.getLayers().get(i);
            FieldMatrix<Complex> matrix = l.getMatrixtoNextLayer();
            FieldVector<Complex> netin = matrix.operate(current.getNetin());
            Complex resizer = new Complex(1/(double)(current.getNetin().getDimension() +1));
            netin =  netin.mapMultiply(resizer);
            current = current.createNext(netin);
        }
        this.last = current;
        return SectorHelper.moduloSector(this.last.getNetin(),net.getSectorsCount());
    }

    public LayerResult getFirst() {
        return first;
    }
    public void setFirst(LayerResult first) {
        this.first = first;
    }
    public LayerResult getLast() {
        return last;
    }
    public void setLast(LayerResult last) {
        this.last = last;
    }

}
