package Network.Learning.Propagation;

import Network.Calculation.LayerResult;
import Network.FeedForwardNet;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public interface IPropagationStrategy {
    public void setNetWork(FeedForwardNet net);
    public FeedForwardNet getNetWork();
    public double getEta();
    public void setEta(double eta);
    public void propagate(FieldVector<Complex> error, ArrayList<LayerResult> results) throws SeviException;
  //  public void propagate(Complex[]  error);
}
