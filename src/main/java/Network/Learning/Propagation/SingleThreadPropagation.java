package Network.Learning.Propagation;

import Network.Layer.Layer;
import Network.Learning.Calculation.LayerResult;
import Network.FeedForwardNet;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class SingleThreadPropagation implements IPropagationStrategy {
    private FeedForwardNet net;

    public SingleThreadPropagation(FeedForwardNet net){
        setNetWork(net);
    }
    public void setNetWork(FeedForwardNet net) {
        this.net = net;
    }
    public FeedForwardNet getNetWork() {
        return this.net;
    }



    public void propagate(FieldVector<Complex> error, LayerResult lastResult) throws SeviException{
        ArrayList<WeightPropagation> weightPropagations = calcLayerPropagation(error,lastResult);

        for(WeightPropagation lp: weightPropagations){
            lp.ajustWeights();
        }
    }
    protected ArrayList<WeightPropagation> calcLayerPropagation(FieldVector<Complex> error, LayerResult lastResult) {
        ArrayList<WeightPropagation> weightPropagations = new ArrayList<WeightPropagation>();
        Layer currentLayer = net.getLayers().get(net.getLayers().size()-2);

        WeightPropagation lp = new WeightPropagation(currentLayer, lastResult,error);
        weightPropagations.add(lp);

        int layersCount = 1;
        while(currentLayer.getBefore() != null){
            currentLayer = currentLayer.getBefore();
            lp = lp.getNext();
            weightPropagations.add(lp);

            layersCount++;
            assert layersCount < 10000;
        }

        return weightPropagations;
    }

}
