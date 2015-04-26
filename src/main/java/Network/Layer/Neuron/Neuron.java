package Network.Layer.Neuron;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class Neuron {
    private ArrayList<Synapse> outputs = new ArrayList<Synapse>();
    private ArrayList<Synapse> inputs = new ArrayList<Synapse>();

    public ArrayList<Synapse> getOutputs() {
        return outputs;
    }
    public ArrayList<Synapse> getInputs() {
        return inputs;
    }

}
