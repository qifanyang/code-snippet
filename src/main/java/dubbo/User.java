package dubbo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/3.
 */
@Data
public class User implements Serializable{
    private String name;
    private int age;
}
