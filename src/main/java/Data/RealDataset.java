package Data;

import Data.Pairs.ComplexPair;
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
    public SectorDataset toSector(int sectorsCount) {
        SectorDataset sd = new  SectorDataset(sectorsCount);
        for( RealPair cp: pairs){
            try {
                sd.add(cp.toSector(sectorsCount));
            } catch(SeviException ex){
                System.out.println(ex);
                assert false;
            }
        }
        return sd;
    }
    protected boolean checkArrayBounds(RealPair pair){
       int inputlength = this.getPairs().get(0).getInput().length;
        int outputlength = this.getPairs().get(0).getTarget().length;
        boolean boundOk = pair.getInput().length == inputlength && pair.getTarget().length == outputlength;
        return boundOk;
    }

}
