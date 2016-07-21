package test.sync;

import java.util.Date;

/**
 * @author yangqf
 * @version 1.0 2016/5/19
 */
public class Xxx{
    public static void main(String[] args){
        Date date = new Date(1960, 3, 1);
        System.out.println(date.getTime());
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT  ")
                .append("mdc.id as id,")
                .append("mdc.plan_id as plan_id,")
                .append("mdc.condition_type as condition_type,")
                .append("mdc.condition_value as condition_value,")
                .append("mdc.creator_id as creator_id,")
                .append("mdc.create_time as create_time,")
                .append("mdc.updater_id as updater_id,")
                .append("mpcs.server_update_time as update_time,")
                .append("mdc.is_delete as is_delete ")
                .append("FROM market_dynamic_condition mdc ")
                .append("RIGHT JOIN market_plan_commercial_sync mpcs ON mpcs.plan_id=mdc.plan_id")
                .append(" WHERE mpcs.specialTempalteType=13 AND mpcs.plan_status >= 3 AND mpcs.brand_identy=? AND mpcs.commercial_id=? AND (mpcs.server_update_time>? or (mpcs.server_update_time=? and mdc.id>?))");

        if (true) {
            sqlBuilder.append(" AND (mdc.is_delete=0 AND mpcs.status_flag=1) ");
        }
        sqlBuilder.append("  ORDER BY mpcs.server_update_time,mdc.id ASC LIMIT ?");
        System.out.println(sqlBuilder);
    }
}
