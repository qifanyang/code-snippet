package spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/11.
 */
@Service
public class NameService {
    @Autowired
    private NameDao nameDao;

    public void chageNameById(long id, String name){
        nameDao.updateNameById(id, name);
    }

    public String selectNameById(long id){
        return nameDao.selectNameById(id);
    }


}
