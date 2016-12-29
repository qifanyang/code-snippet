package json;

import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author yangqf
 * @version 1.0 2016/12/23
 */
public class JSONObjectTest{
    @Test
    public void t() throws JSONException{
        Date date = new Date();
//        format(DateTimeFormatter.ofPattern("yyMMddHHmmssSSS"))
//                LocalDate.of(date.getYear())
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(date);
        System.out.println(format);
        BigDecimal bigDecimal = new BigDecimal("0.0");
        System.out.println(bigDecimal);
        //构建嵌套list的json
        JSONObject jo = new JSONObject();
        jo.put("name", "hello");
        jo.put("time", "2016-12-12");
        jo.put("result", true);
        ArrayList<String> list = new ArrayList<>();
        list.add("yang");
        list.add("li");
        list.add("hua");
        jo.put("first_name", list);//JSONArray
        Map<String, Object> map = new HashMap<>();
        map.put("age", 1);
        map.put("sex", "男");
        List<Map<String, Object>> mapInList = new ArrayList<>();
        mapInList.add(map);
        jo.put("info", mapInList);
        String json = jo.toString();
        System.out.println(json);

        jo = new JSONObject(json);
        Object result = jo.get("result");
        Object time = jo.get("time");
        LocalDate parse = LocalDate.parse(time.toString());
        long millisecondsOneDay = 1000*60*60*24;
        long l = parse.toEpochDay()*millisecondsOneDay;
        Timestamp timestamp = new Timestamp(l);
        System.out.println(timestamp);
        System.out.println(jo.get("name"));
        Object first_name = jo.get("first_name");
        JSONArray info = (JSONArray) jo.get("info");
        int length = info.length();
        for(int i=0; i < length; i++){
            JSONObject o = (JSONObject) info.get(i);
            System.out.println(o);
        }
        System.out.println(info);



    }
}

