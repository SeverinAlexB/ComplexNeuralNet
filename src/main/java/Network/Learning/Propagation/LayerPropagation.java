package Network.Learning.Propagation;

import Network.Calculation.LayerResult;
import Network.Layer.Layer;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class LayerPropagation {
    private FieldVector<Complex> error;
    private FieldVector<Complex> delta;
    private FieldVector<Complex> lastdelta;
    private Layer layer;
    private LayerResult result;



    private FieldMatrix<Complex> weightDiff;

    public LayerPropagation(FieldVector<Complex> error, Layer layer, LayerResult result) throws SeviException{
        this.error = error;
        this.result = result;
        this.layer = layer;
        this.calcDeltas();
    }
    public LayerPropagation(Layer layer, LayerResult result,FieldVector<Complex> lastdelta) throws SeviException{
        this.lastdelta = lastdelta;
        this.result = result;
        this.layer = layer;
        this.calcDeltas();
    }
    protected LayerPropagation(){}

    public FieldVector<Complex> getError() {
        return error;
    }
    public void setError(FieldVector<Complex> error) {
        this.error = error;
    }
    public FieldVector<Complex> getDelta() {
        return delta;
    }
    public void setDelta(FieldVector<Complex> delta) {
        this.delta = delta;
    }
    public FieldVector<Complex> getLastdelta() {
        return lastdelta;
    }
    public void setLastdelta(FieldVector<Complex> lastdelta) {
        this.lastdelta = lastdelta;
    }
    public Layer getLayer() {
        return layer;
    }
    public void setLayer(Layer layer) {
        this.layer = layer;
    }
    public LayerResult getResult() {
        return result;
    }
    public void setResult(LayerResult result) {
        this.result = result;
    }
    public FieldMatrix<Complex> getWeightDiff() {
        return weightDiff;
    }
    public void setWeightDiff(FieldMatrix<Complex> weightDiff) {
        this.weightDiff = weightDiff;
    }

    public void calcDeltas() throws SeviException {
        if(layer.getNext() != null){
            this.error = calcError(conjugate(layer.getMatrixtoNextLayer()), lastdelta);
            //this.error = calcError(layer.getMatrixtoNextLayer(), lastdelta);
        }
        this.delta = calcDelta(error,conjugate(this.result.getOutput()));
        //this.delta = calcDelta(error,this.result.getOutput());
    }
    public void ajustWeights( double eta) throws SeviException{
        this.weightDiff = calcWeightDelta(this.delta,result.getInput(),eta);
        FieldMatrix<Complex> newWeights = layer.getBefore().getMatrixtoNextLayer().add(weightDiff);
        layer.getBefore().setMatrixtoNextLayer(newWeights);
    }


    protected FieldMatrix<Complex> calcWeightDelta(FieldVector<Complex> delta, FieldVector<Complex> input, double eta) {
       return  delta.outerProduct(input).scalarMultiply(new Complex(eta));
    }

    public LayerPropagation getNext(LayerResult result) throws SeviException {
        return new LayerPropagation(this.layer.getBefore(), result,this.delta);
    }

    protected FieldVector<Complex> calcError(FieldMatrix<Complex> weights, FieldVector<Complex> lastdelta){
        FieldMatrix<Complex> transposed = weights.transpose();
        transposed = conjugate(transposed);
        return transposed.operate(lastdelta);
    }
    protected FieldVector<Complex> calcDelta(FieldVector<Complex> error, FieldVector<Complex> tempOutput){
        //FieldVector<Complex> out = tempOutput;
       FieldVector<Complex> out = conjugate(tempOutput);
        FieldVector<Complex> delta = OneMinus(out);
        delta = out.ebeMultiply(delta);
        delta = error.ebeMultiply(delta);
        return delta;
    }

    private final static Complex one = new Complex(1);
    protected FieldVector<Complex> OneMinus(FieldVector<Complex> vector1){
        FieldVector<Complex> res = vector1.copy();
        for(int i = 0; i < vector1.getDimension(); i ++) {
            res.setEntry(i, one.subtract(vector1.getEntry(i)));
        }
        return res;
    }

    private FieldVector<Complex> conjugate(FieldVector<Complex> vec){
        FieldVector<Complex> ret = vec.copy();
        for(int i=0; i < vec.getDimension(); i++){
            ret.setEntry(i,vec.getEntry(i).conjugate());
        }
        return ret;
    }
    private FieldMatrix<Complex> conjugate(FieldMatrix<Complex> vec){
        FieldMatrix<Complex> ret = vec.copy();
        for(int i=0; i < vec.getRowDimension(); i++){
            for(int y=0; y < vec.getColumnDimension(); y++){
                ret.setEntry(i,y,vec.getEntry(i,y).conjugate());
            }
        }
        return ret;
    }
}
