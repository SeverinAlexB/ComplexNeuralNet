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

    public void connectTo(Neuron n){
        Synapse synapse = new Synapse(this,n);
        this.outputs.add(synapse);
        n.getInputs().add(synapse);
    }
    public void unconnectTo(Neuron n){
        for(Synapse s: outputs){
            if(s.getOutput() == n){
                outputs.remove(s);
                n.getInputs().remove(s);
                break;
            }
        }
    }

}
