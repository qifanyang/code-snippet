package test.algorithm;

import java.util.Arrays;

/**
 * @author yangqf
 * @version 1.0 2016/4/15
 */
public class twosun{
        public int[] twoSum(int[] nums, int target) {
            IntMap map = new IntMap();
            for(int i = 0; i < nums.length; i++){
                int v = target - nums[i];
                if(map.get(v) != 0){
                    return new int[]{map.get(v) - 1, i };
                }
                map.put(nums[i], i+1);
            }
            return null;
        }

    static class IntMap{
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
                if(k > pkv.length){
                    pkv = Arrays.copyOf(pkv, pkv.length * 2);
                }
                pkv[k]=v;
            }else {
                if(-k > nkv.length){
                    nkv = Arrays.copyOf(nkv, nkv.length * 2);
                }
                nkv[-k]=v;
            }
        }

        public int get(int k){
            if(k >= 0) {
                if(k > pkv.length){
                    pkv = Arrays.copyOf(pkv, pkv.length * 2);
                }
                return pkv[k];
            }else {
                if(-k > nkv.length){
                    nkv = Arrays.copyOf(nkv, nkv.length * 2);
                }
                return nkv[-k];
            }
        }
    }

    public static void main(String[] args){
        twosun twosun = new twosun();
//        Sort.printArrayValue(twosun.twoSum(new int[]{0,4,3,0}, 0));
        Sort.printArrayValue(twosun.twoSum(new int[]{150,24,79,50,88,345,3}, 200));
    }
}
