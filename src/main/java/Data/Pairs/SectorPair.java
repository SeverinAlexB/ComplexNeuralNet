package Data.Pairs;

import Data.Pairs.Helper.ComplexValue;
import Data.Pairs.Helper.RealValue;
import Data.Pairs.Helper.SectorValue;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorPair extends AbstractPair<FieldVector<Complex>> {


    private int sectorsCount = 1;
    public SectorPair(FieldVector<Complex> input, FieldVector<Complex> target, int sectorsCount) throws SeviException{
        checkValues(input,target,sectorsCount);
        setInput(input);
        setTarget(target);
        this.sectorsCount = sectorsCount;
    }
    public int getSectorsCount() {
        return sectorsCount;
    }
    private void checkValues(FieldVector<Complex> input, FieldVector<Complex> target, int sectorsCount) throws SeviException{
        double possibleArgRange = 2 * Math.PI / sectorsCount;
        for(int i = 0; i < input.getDimension(); i++) {
            double arg = input.getEntry(i).getArgument();
            if(arg < 0 || arg > possibleArgRange){
                throw new SeviException("Argument of the values are not in the sector argument range");
            }
        }
        for(int i = 0; i < target.getDimension(); i++) {
            double arg = target.getEntry(i).getArgument();
            if(arg < 0 || arg > possibleArgRange){
                throw new SeviException("Argument of the values are not in the sector argument range");
            }
        }
    }
    public ComplexPair toComplex() {
        FieldVector<Complex> input = SectorValue.toComplex(this.getInput(),sectorsCount);
        FieldVector<Complex> output = SectorValue.toComplex(this.getTarget(),sectorsCount);
        return new ComplexPair(input,output);
    }
    public RealPair toReal() {
        Double[] input = SectorValue.toReal(this.getInput(),sectorsCount);
        Double[] output = SectorValue.toReal(this.getTarget(),sectorsCount);
        try{
            return new RealPair(input,output);
        } catch(SeviException ex) {
            assert false;
            return null;
        }
    }
}
