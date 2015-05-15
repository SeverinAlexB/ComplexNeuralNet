package Network;

import Network.Layer.Layer;
import Network.Layer.Neuron.ActivationFunction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Severin on 24.04.2015.
 */
public class FeedForwardNet {
    private int sectorsCount = 1;
    private ArrayList<Layer> layers = new ArrayList<Layer>();
    public FeedForwardNet(int sectorsCount){
        this.sectorsCount = sectorsCount;
    }
    public List<Layer> getLayers(){
        return Collections.unmodifiableList(layers);
    }
    public Layer addLayer(int neuronCount, boolean hasBias) throws SeviException{
        if(layers.isEmpty() && hasBias) throw new SeviException("Bias is not allowed in the first layer");
        Layer layer = new Layer(neuronCount);
        layers.add(layer);
        if(layers.size() > 1) {
            layers.get(layers.size() -2).connectTo(layer);
        }
        if(hasBias) layer.setBias(true);
        return layer;
    }
    public int getSectorsCount() {
        return sectorsCount;
    }

}
