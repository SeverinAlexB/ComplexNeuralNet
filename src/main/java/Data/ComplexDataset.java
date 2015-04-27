package Data;

import Data.Pairs.ComplexPair;
import Data.Pairs.RealPair;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class ComplexDataset {
    private ArrayList<ComplexPair> pairs = new ArrayList<ComplexPair>();
    public ArrayList<ComplexPair> getPairs() {
        return pairs;
    }
    public void add(ComplexPair pair) throws SeviException{
        if(pairs.size() > 0){
                if(pair.getInput().getDimension() != getInputLength() || pair.getOutput().getDimension() != getOutputLength()) {
                    throw new SeviException("Input or output length isn't equal to the dataset");
                }
        }
        pairs.add(pair);
    }
    public RealDataset transform() {
        RealDataset cd = new  RealDataset();
        for( ComplexPair cp: pairs){
            cd.add(cp.transform());
        }
        return cd;
    }
    public int getInputLength() throws SeviException {
        if(pairs.size() == 0) throw new SeviException("Dataset is empty");
        return pairs.get(0).getInput().getDimension();
    }
    public int getOutputLength() throws SeviException {
        if(pairs.size() == 0) throw new SeviException("Dataset is empty");
        return pairs.get(0).getOutput().getDimension();
    }
}
