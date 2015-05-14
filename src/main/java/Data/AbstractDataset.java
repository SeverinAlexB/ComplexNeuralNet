package Data;

import Data.Pairs.RealPair;
import Network.SeviException;

import java.util.ArrayList;

/**
 * Created by Severin on 14.05.2015.
 */
public abstract class AbstractDataset<T> {
    protected ArrayList<T> pairs = new ArrayList<T>();
    public ArrayList<T> getPairs() {
        return pairs;
    }
    public void add(T pair) throws SeviException{
        if(pairs.size() > 0){
           boolean boundOk = checkArrayBounds(pair);
            if(!boundOk) throw new SeviException("Input or outputlength unequal the existing pairs");
        }
        pairs.add(pair);
    }
    protected abstract boolean checkArrayBounds(T pair);
}
