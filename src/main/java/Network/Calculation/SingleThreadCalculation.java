package Network.Calculation;

import Network.FeedForwardNet;
import Network.Layer.Layer;
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
    private ArrayList<LayerResult> layerResults = new ArrayList<LayerResult>();
    public SingleThreadCalculation(FeedForwardNet net){
        this.setNetWork(net);
    }

    public ArrayList<LayerResult> getLayerResults(){
        return layerResults;
    }
    public void setNetWork(FeedForwardNet net) {
        this.net = net;
    }

    public FeedForwardNet getNetWork() {
        return this.net;
    }

    public FieldVector<Complex> calculate(FieldVector<Complex> input) throws SeviException{
        this.layerResults.clear();
        LayerResult result = new LayerResult();
        result.setInput(input);
        for(int i = 0; i < net.getLayers().size() -1; i++) {
            /*Layer l = net.getLayers().get(i);
            FieldMatrix<Complex> matrix = l.getMatrixtoNextLayer();
            FieldVector<Complex> netin = matrix.operate(result.getInput());
            result.setNetin(netin);
            //FieldVector<Complex> output = l.getActivationFunction().calc(netin);
            result.setOutput(output);
            result.setActivationFunction(l.getActivationFunction());
            this.layerResults.add(result);
            result = new LayerResult();
            result.setInput(output);*/
        }
        return result.getInput();
    }



}
