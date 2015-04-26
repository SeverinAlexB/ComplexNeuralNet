package Data;

import Data.Pairs.RealPair;
import Network.SeviException;

import java.util.ArrayList;

/**
 * Created by Severin on 24.04.2015.
 */
public class RealDataset {
    private ArrayList<RealPair> pairs = new ArrayList<RealPair>();
    public ArrayList<RealPair> getPairs() {
        return pairs;
    }
    public void add(RealPair pair){
        pairs.add(pair);
    }
    public ComplexDataset transform() throws SeviException{
        ComplexDataset cd = new ComplexDataset();
        for(RealPair rp: pairs){
            cd.add(rp.transform());
        }
        return cd;
    }
}
