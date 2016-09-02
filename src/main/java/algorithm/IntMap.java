package algorithm;

/**
 * @author yangqf
 * @version 1.0 2016/4/15
 */
public class IntMap{
    int pkv[] = new int[100];
    int nkv[] = new int[100];

    /**
     * k可以为负数
     * @param k
     * @param v
     */
    public void put(int k, int v){
            //整数直接对应其索引, 负数取
        if(k >=0){
            pkv[k]=v;
        }else {
            nkv[-k]=v;
        }
    }

    public int get(int k){
        if(k >= 0) {
            return pkv[k];
        }else {
            return nkv[-k];
        }
    }
}
