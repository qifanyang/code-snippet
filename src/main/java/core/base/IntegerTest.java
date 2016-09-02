package core.base;

import java.sql.Timestamp;

/**
 * @author yangqf
 * @version 1.0 2016/4/18
 */
public class IntegerTest{
    public static void main(String[] args){
//        Integer i = Integer.valueOf(3);
//        Integer j = Integer.valueOf(3);
        Integer big = Integer.valueOf(1000);
//        System.out.println(i == j);
//        System.out.println(i == 3);
        System.out.println(big == 1000);//都是转换为基本数据类型来比较,不会装箱来比较
        System.out.println(1000 == big);

            StringBuffer sqlBuilder = new StringBuffer();
            sqlBuilder.append("SELECT ")
                    .append("mp.id as id,")
                    .append("mp.plan_name as plan_name,")
                    .append("mp.pan_type_id as pan_type_id,")
                    .append("mp.brand_id as brand_id,")
                    .append("mp.plan_desc as plan_desc,")
                    .append("mp.is_share as is_share,")
                    .append("mp.plan_start_day as plan_start_day,")
                    .append("mp.plan_end_day as plan_end_day,")
                    .append("mp.plan_type as plan_type,")
                    .append("mp.plan_status as plan_status,")
                    .append("mp.coupon_sent_amount as coupon_sent_amount,")
                    .append("mp.forecast_person_amount as forecast_person_amount,")
                    .append("mp.creator_id as mp_creator_id,")
                    .append("mp.create_time as mp_create_time,")
                    .append("mp.updater_id as mp_updater_id,")
                    .append("mp.update_time as mp_update_time,")
                    .append("mp.is_delete as mp_is_delete,")
                    .append("mp.is_hand_send as is_hand_send,")
                    .append("mp.market_template_type as market_template_type,")
                    .append("mp.special_market_type as special_market_type,")
                    .append("mp.lottery_amount as lottery_amount,")
                    .append("mp.creator_name as mp_creator_name,")
                    .append("mp.updater_name as mp_updater_name,")

                    .append("mpcr.commercial_id as commercial_id,")
                    .append("mpcr.commercial_type as commercial_type,")
                    .append("mpcr.status_flag as mpcr_status_flag,")
                    .append("mpcr.creator_id as mpcr_creator_id,")
                    .append("mpcr.creator_name as mpcr_creator_name,")
                    .append("mpcr.updator_id as mpcr_updator_id,")
                    .append("mpcr.updator_name as mpcr_updator_name,")
                    .append("mpcr.server_create_time as mpcr_server_create_time,")
                    .append("mpcr.server_update_time as mpcr_server_update_time,")

                    .append("mdc.condition_type as condition_type,")
                    .append("mdc.condition_value as condition_value,")
                    .append("mdc.creator_id as mdc_creator_id,")
                    .append("mdc.create_time as mdc_create_time,")
                    .append("mdc.updater_id as mdc_updater_id,")
                    .append("mdc.update_time as mdc_update_time,")
                    .append("mdc.is_delete as mdc_is_delete ")

                    .append("FROM market_plan mp ")
                    .append("LEFT JOIN market_plan_commercial_rel mpcr ON mpcr.plan_id=mp.id ")
                    .append("LEFT JOIN market_dynamic_condition mdc ON mdc.plan_id=mp.id ")
                    .append(" WHERE mp.brand_id=3268 AND mp.special_market_type=13 AND mp.plan_status >= 3 AND (mp.update_time>0 or (mp.update_time=0 and mp.id>0))");


//        if (context.isInit) {
            sqlBuilder.append(" AND mp.is_delete=0");
//        }
        sqlBuilder.append("  ORDER BY mp.update_time,mp.id ASC LIMIT 10");

        System.out.println(sqlBuilder);

            System.out.println(new Timestamp((0L)).getTime());
    }
}
