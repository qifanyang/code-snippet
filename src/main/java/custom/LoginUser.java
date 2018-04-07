package custom;

import java.io.Serializable;

/**
 * Created by yangqifan on 2018/4/7.
 */
public class LoginUser implements Serializable{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
