package test.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import test.json.JsonBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangqf
 * @version 1.0 2016/8/18
 */
public class JsonBeanListTest{
    public static void main(String[] args){
        List<JsonBean> list = new ArrayList<>();
        JsonBean jsonBean = new JsonBean();
        jsonBean.setAge(1);
        jsonBean.setName("foo");
        list.add(jsonBean);
        jsonBean = new JsonBean();
        jsonBean.setAge(2);
        jsonBean.setName("bar");
        list.add(jsonBean);

        String s = JSON.toJSONString(list);
        System.out.println(s);
        List list1 = JSON.parseObject(s, new TypeReference<List<JsonBean>>() {});
        JSONArray objects = JSON.parseArray(s);
        System.out.println(list1);
    }
}
