package test.algorithm;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

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

        String sql = "select tt.table_id,tt.shop_identy,t.areaId,"
                + "t.`status`,t.tableStatus,count(tt.uuid),sum(tt.table_people_count),"
                + "t.tablePersonCount,t.synFlag, t.modifyDateTime from trade_table tt "
                + "INNER JOIN `tables` t on t.tableID = tt.table_id "
                + "INNER JOIN trade tr on tr.id = tt.trade_id "
                + "INNER JOIN table_position tp on t.tableID=tp.table_id "
                + "where t.commercialID = ? AND t.commercialID = tt.shop_identy and (tr.trade_status=1 or tr.trade_status=3) and t.status=0 "
                + " and tr.status_flag=1 AND tt.status_flag=1 "
                + "AND tr.business_type = 2 AND tr.trade_type = 1 and (tt.self_table_status=1 or tt.self_table_status is null) "
                + "AND tp.is_delete=0 "
                + "group by tt.table_id ORDER BY tt.server_update_time desc";
        System.out.println(sql);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + -3);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();

        System.out.println(date.getTime());
    }
}
