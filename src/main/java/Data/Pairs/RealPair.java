package Data.Pairs;

import Data.Pairs.Helper.RealValue;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public class RealPair extends AbstractPair<Double[]> {

    public RealPair(Double[] input, Double[] target) throws SeviException{
        checkValueRange(input,target);
        setInput(input);
        setTarget(target);
    }

    public ComplexPair toComplex() {
        FieldVector<Complex> input = RealValue.toComplex(this.getInput());
        FieldVector<Complex> output = RealValue.toComplex(this.getTarget());
        return new ComplexPair(input,output);
    }
    public SectorPair toSector(int sectorsCount) {
        FieldVector<Complex> input = RealValue.toSector(this.getInput(), sectorsCount);
        FieldVector<Complex> output = RealValue.toSector(this.getTarget(), sectorsCount);
        try{
            return new SectorPair(input,output,sectorsCount);
        } catch(SeviException ex){
            assert false;
            return null;
        }
    }
    private void checkValueRange(Double[] input, Double[] target) throws SeviException{
        for(double d: input){
            if(d >= 1 || d < 0) throw new SeviException("Values have to be normalized (0 <= range < 1)");
        }
        for(double d: target){
            if(d >= 1 || d < 0) throw new SeviException("Values have to be normalized (0 <= range < 1)");
        }
    }

}
