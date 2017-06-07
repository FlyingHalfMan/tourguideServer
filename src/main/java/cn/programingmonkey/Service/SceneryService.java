package cn.programingmonkey.Service;

import cn.programingmonkey.Dao.SceneryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cai on 2017/5/15.
 */

@Service
public class SceneryService {

    @Autowired
    private SceneryDao sceneryDao;
}
