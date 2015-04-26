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
    private ArrayList<Layer> layers = new ArrayList<Layer>();
    public List<Layer> getLayers(){
        return Collections.unmodifiableList(layers);
    }
    public Layer addLayer(int neuronCount, ActivationFunction activationFunction) throws SeviException{
        Layer layer = new Layer(neuronCount,activationFunction);
        layers.add(layer);
        if(layers.size() > 1) {
            layers.get(layers.size() -2).connectTo(layer);
        }
        return layer;
    }

}
