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
    private boolean hasBias = false;
    private Layer next;
    private Layer before;
    private FieldMatrix<Complex> weightMatrix;
    public Layer(int neuronCount) throws SeviException{
        if(neuronCount < 1) throw new SeviException("neuronCount has to be at least 1");
        generateNeurons(neuronCount);
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
    public void setBias(boolean hasBias){
        boolean hasToAdd = this.hasBias==false && hasBias==true;
        boolean hasToDelete = this.hasBias==true && hasBias==false;
        this.hasBias = hasBias;
        if(hasToAdd){
            addBias();
        }
        if(hasToDelete){
            removeBias();
        }
    }
    private void generateNeurons(int neuronCount){
        for(int i=0; i < neuronCount; i++) {
            neurons.add(new Neuron());
        }
    }
    public void connectTo(Layer nextLayer){
        for(Neuron myNeuron:neurons){
            for(Neuron nextNeuron:nextLayer.getNeurons()){
                myNeuron.connectTo(nextNeuron);
            }
        }
        this.next = nextLayer;
        nextLayer.before = this;
    }
    private void addBias() {
        Neuron bias = new Neuron();
        this.before.getNeurons().add(bias);
        for(Neuron neuron: this.getNeurons()){
            bias.connectTo(neuron);
        }
    }
    private void removeBias() {
        Neuron bias = this.before.getNeurons().get(this.before.getNeurons().size()-1);
        for(Neuron neuron: this.getNeurons()){
            bias.unconnectTo(neuron);
        }
    }
    public FieldMatrix<Complex> getMatrixtoNextLayer() {
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
