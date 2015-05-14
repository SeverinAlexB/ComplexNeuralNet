package Network.Layer;
import Network.Layer.Neuron.ActivationFunction;
import Network.Layer.Neuron.Neuron;
import Network.Layer.Neuron.Synapse;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.util.MathUtils;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class Layer {
    private ArrayList<Neuron> neurons = new ArrayList<Neuron>();
    private boolean hasBias = true;
    private Layer next;
    private Layer before;
    private FieldMatrix<Complex> weightMatrix;
    public Layer(int neuronCount, ActivationFunction activationFunction, boolean hasBias) throws SeviException{
        if(neuronCount < 1) throw new SeviException("neuronCount has to be at least 1");
        generateNeurons(neuronCount);
        this.hasBias = hasBias;
    }
    public Layer getNext() {
        return next;
    }
    public Layer getBefore() {
        return before;
    }
    public ArrayList<Neuron> getNeurons(){
        return neurons;
    }
    public boolean hasBias() {
        return hasBias;
    }
    private void generateNeurons(int neuronCount){
        for(int i=0; i < neuronCount; i++) {
            neurons.add(new Neuron());
        }
    }
    public void connectTo(Layer nextLayer){
        Synapse synapse;
        for(Neuron myNeuron:neurons){
            for(Neuron nextNeuron:nextLayer.getNeurons()){
                synapse = new Synapse(myNeuron,nextNeuron);
                myNeuron.getOutputs().add(synapse);
                nextNeuron.getInputs().add(synapse);
            }
        }
        this.next = nextLayer;
        nextLayer.before = this;
    }
    public FieldMatrix<Complex> getMatrixtoNextLayer() throws SeviException {
        if(this.next == null) throw new SeviException("Connect to a layer first");
        if(this.weightMatrix != null) return this.weightMatrix;
        Complex[][] matrix = new Complex[this.next.getNeurons().size()][this.getNeurons().size()];
        for(int next = 0; next < this.next.getNeurons().size(); next++){
            for(int me = 0; me < this.getNeurons().size(); me++){
                matrix[next][me] = this.getNeurons().get(me).getOutputs().get(next).getWeight();
            }
        }
        this.weightMatrix = MatrixUtils.createFieldMatrix(matrix);
        return weightMatrix;
    }
    public void setMatrixtoNextLayer(FieldMatrix<Complex> fieldMatrix) throws SeviException {
        this.weightMatrix = fieldMatrix;
        Complex[][] matrix = fieldMatrix.getData();
        if(matrix[0].length != this.getNeurons().size()) throw new SeviException("matrix length y is unequals neuronCount of the this layer");
        if(matrix.length != this.next.getNeurons().size()) throw new SeviException("matrix length x is unequals neuronCount of the next layer");

        for(int next = 0; next < this.next.getNeurons().size(); next++){
            for(int me = 0; me < this.getNeurons().size(); me++){
                this.getNeurons().get(me).getOutputs().get(next).setWeight(matrix[next][me]);
            }
        }

    }
}
