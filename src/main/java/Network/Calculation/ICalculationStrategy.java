package Network.Calculation;

import Network.FeedForwardNet;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

import javax.swing.text.FieldView;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public interface ICalculationStrategy {
    public void setNetWork(FeedForwardNet net);
    public FeedForwardNet getNetWork();
    public FieldVector<Complex> calculate(FieldVector<Complex> input)  throws SeviException;
    public ArrayList<LayerResult> getLayerResults();
}
