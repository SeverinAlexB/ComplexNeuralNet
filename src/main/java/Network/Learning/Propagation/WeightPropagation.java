package Network.Learning.Propagation;

import Network.Learning.Calculation.LayerResult;
import Network.Layer.Layer;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldMatrix;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 25.04.2015.
 */
public class WeightPropagation {
    private FieldVector<Complex> lastError;
    private FieldVector<Complex> myError;
    private Layer bottomLayer;
    private LayerResult result;
    public WeightPropagation(Layer bottomLayer, LayerResult result,FieldVector<Complex> lastError){
        this.lastError = lastError;
        this.result = result;
        this.bottomLayer = bottomLayer;

        calcError(bottomLayer, lastError);
    }

    private void calcError(Layer bottomLayer, FieldVector<Complex> error) {
        boolean isTopMatrix = bottomLayer.getNext().getNext() == null;
        if(isTopMatrix) {
            this.myError = error;
        } else{
            FieldMatrix<Complex> transposed = conjugateTranspose(bottomLayer.getNext().getMatrixtoNextLayer());
            this.myError = transposed.operate(error);
        }
    }

    protected WeightPropagation(){}
    public Layer getBottomLayer() {
        return bottomLayer;
    }
    public LayerResult getResult() {
        return result;
    }

    public void ajustWeights(){
        FieldVector<Complex> conjugated = conjugate(result.getBefore().getNetin());
        FieldMatrix<Complex> weightDiff = this.myError.outerProduct(conjugated);
        FieldMatrix<Complex> newWeights = bottomLayer.getMatrixtoNextLayer().add(weightDiff);
        try {
            bottomLayer.setMatrixtoNextLayer(newWeights);
        } catch(SeviException ex){
            System.out.println(ex.toString());
            assert false;
        }
    }
    public WeightPropagation getNext() {
        return new WeightPropagation(this.bottomLayer.getBefore(),this.result.getBefore(),this.myError);
    }

    private FieldVector<Complex> conjugate(FieldVector<Complex> vec){
        FieldVector<Complex> ret = vec.copy();
            for(int y=0; y < vec.getDimension(); y++){
                ret.setEntry(y,vec.getEntry(y).conjugate());
            }
        return ret;
    }


    private FieldMatrix<Complex> conjugateTranspose(FieldMatrix<Complex> vec){
        FieldMatrix<Complex> ret = vec.copy();
        for(int i=0; i < vec.getRowDimension(); i++){
            for(int y=0; y < vec.getColumnDimension(); y++){
                ret.setEntry(i,y,vec.getEntry(i,y).conjugate());
            }
        }
        return ret.transpose();
    }
}
