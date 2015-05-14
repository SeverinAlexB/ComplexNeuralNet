package Data;

import Data.Pairs.ComplexPair;
import Network.SeviException;

/**
 * Created by Severin on 24.04.2015.
 */
public class ComplexDataset extends AbstractDataset<ComplexPair> {


    public RealDataset toReal() {
        RealDataset rd = new  RealDataset();
        for( ComplexPair cp: pairs){
            try {
                rd.add(cp.toReal());
            } catch(SeviException ex){
                assert false;
            }
        }
        return rd;
    }
    public SectorDataset toSector(int sectorsCount) {
        SectorDataset sd = new  SectorDataset(sectorsCount);
        for( ComplexPair cp: pairs){
            try {
                sd.add(cp.toSector(sectorsCount));
            } catch(SeviException ex){
                assert false;
            }
        }
        return sd;
    }
    protected boolean checkArrayBounds(ComplexPair pair){
        int inputlength = this.getPairs().get(0).getInput().getDimension();
        int outputlength = this.getPairs().get(0).getInput().getDimension();
        boolean boundOk = pair.getInput().getDimension() == inputlength && pair.getTarget().getDimension() == outputlength;
        return boundOk;
    }
}
