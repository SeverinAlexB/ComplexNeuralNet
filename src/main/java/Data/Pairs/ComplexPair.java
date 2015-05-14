package Data.Pairs;

import Data.Pairs.Helper.ComplexValue;
import Data.Pairs.Helper.RealValue;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

/**
 * Created by Severin on 24.04.2015.
 */
public class ComplexPair extends AbstractPair<FieldVector<Complex>> {
    public ComplexPair(FieldVector<Complex> input, FieldVector<Complex> target){
        setInput(input);
        setTarget(target);
    }

    public RealPair toReal() {
        Double[] input = ComplexValue.toReal(this.getInput());
        Double[] output = ComplexValue.toReal(this.getTarget());
        try{
            return new RealPair(input,output);
        } catch(SeviException ex) {
            assert false;
            return null;
        }
    }
    public SectorPair toSector(int sectorsCount) {
        FieldVector<Complex> input = ComplexValue.toSector(this.getInput(), sectorsCount);
        FieldVector<Complex> output = ComplexValue.toSector(this.getTarget(), sectorsCount);
        try{
            return new SectorPair(input,output,sectorsCount);
        } catch(SeviException ex){
            assert false;
            return null;
        }
    }
    public SectorPair transformToSector(int sectorCount) throws SeviException {
        if(sectorCount < 1) throw new SeviException("SectorCount has to be at least 1");
        FieldVector<Complex> sectorInput = ComplexValue.toSector(this.getInput(),sectorCount);
        FieldVector<Complex> sectorTarget = ComplexValue.toSector(this.getTarget(), sectorCount);
        return new SectorPair(sectorInput,sectorTarget,sectorCount);
    }


}
