package Data;

import Data.Pairs.ComplexPair;
import Data.Pairs.RealPair;
import Data.Pairs.SectorPair;
import Network.SeviException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.linear.FieldVector;

import javax.activation.DataSource;

/**
 * Created by Severin on 14.05.2015.
 */
public class SectorDataset extends AbstractDataset<SectorPair> {
    private int sectorCount = 1;
    public SectorDataset(int sectorCount){
        this.sectorCount = sectorCount;
    }
    public int getSectorCount() {
        return sectorCount;
    }
    protected boolean checkArrayBounds(SectorPair pair){
        int inputlength = this.getPairs().get(0).getInput().getDimension();
        int outputlength = this.getPairs().get(0).getTarget().getDimension();
        boolean boundOk = pair.getInput().getDimension() == inputlength && pair.getTarget().getDimension() == outputlength;
        return boundOk;
    }
    public ComplexDataset toComplex() {
        ComplexDataset cd = new ComplexDataset();
        for(SectorPair rp: getPairs()){
            try{
                cd.add(rp.toComplex());
            } catch(SeviException ex){
                assert false;
                return null;
            }

        }
        return cd;
    }
    public RealDataset toReal() {
        RealDataset rd = new  RealDataset();
        for( SectorPair cp: pairs){
            try {
                rd.add(cp.toReal());
            } catch(SeviException ex){
                assert false;
            }
        }
        return rd;
    }
    public SectorDataset copy() {
        SectorDataset ret = new SectorDataset(this.sectorCount);
        try{
            for(SectorPair pair:this.getPairs()){
                ret.add(pair.copy());
            }
        } catch(SeviException ex){
            System.out.println(ex);
        }
        return ret;
    }
    public void addBiasToInputs() {
        for(SectorPair pair:this.getPairs()){
            pair.addBiasToInput();
        }
    }

}
