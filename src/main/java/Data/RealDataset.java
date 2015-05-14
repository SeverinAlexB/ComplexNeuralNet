package Data;

import Data.Pairs.RealPair;
import Network.SeviException;

/**
 * Created by Severin on 24.04.2015.
 */
public class RealDataset extends  AbstractDataset<RealPair> {
    public ComplexDataset toComplex() throws SeviException{
        ComplexDataset cd = new ComplexDataset();
        for(RealPair rp: getPairs()){
            cd.add(rp.toComplex());
        }
        return cd;
    }
    protected boolean checkArrayBounds(RealPair pair){
       int inputlength = this.getPairs().get(0).getInput().length;
        int outputlength = this.getPairs().get(0).getInput().length;
        boolean boundOk = pair.getInput().length == inputlength && pair.getTarget().length == outputlength;
        return boundOk;
    }
}
