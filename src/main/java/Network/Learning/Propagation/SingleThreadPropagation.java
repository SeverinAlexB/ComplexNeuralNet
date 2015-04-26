package Network.Learning.Propagation;

import Network.Calculation.LayerResult;
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
    private double eta = 0.6;

    public SingleThreadPropagation(FeedForwardNet net){
        setNetWork(net);
    }
    public void setNetWork(FeedForwardNet net) {
        this.net = net;
    }
    public FeedForwardNet getNetWork() {
        return this.net;
    }

    public double getEta() {
        return eta;
    }
    public void setEta(double eta) {
        this.eta = eta;
    }


    public void propagate(FieldVector<Complex> error, ArrayList<LayerResult> results) throws SeviException{
        ArrayList<LayerPropagation> layerPropagations = calcLayerPropagation(error,results);

        for(LayerPropagation lp:layerPropagations){
            lp.ajustWeights(this.eta);
        }
    }
    protected ArrayList<LayerPropagation> calcLayerPropagation(FieldVector<Complex> error,
                                                            ArrayList<LayerResult> results) throws SeviException {
        ArrayList<LayerPropagation> layerPropagations = new ArrayList<LayerPropagation>();
        LayerPropagation lp = new LayerPropagation(error,net.getLayers().get(net.getLayers().size()-1), results.get(results.size()-1));
        layerPropagations.add(lp);

        for(int i = results.size() -2; i >= 0 ; i--) {
            LayerResult result = results.get(i);
            lp = lp.getNext(result);
            layerPropagations.add(lp);
        }
        return layerPropagations;
    }

}
