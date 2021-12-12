package cc;

import com.cc.dao.StartUrlDao;
import com.cc.dao.impl.StartUrlDaoImpl;
import com.cc.doamin.StartUrl ;
import org.junit.Test;

public class StartUrlDaoTest {

    @Test
    public void testGetStartUrlById() throws Exception {
        StartUrlDao startUrlDao = new StartUrlDaoImpl();
        StartUrl user = startUrlDao.getStartUrlById("001");
        System.out.println(user);
    }

    @Test
    public void testGetStartUrlBy() throws Exception {
    }

    @Test
    public void testInsertStartUrl() throws Exception {
        StartUrlDao startUrlDao = new StartUrlDaoImpl();
        for (int i = 10; i <= 10; i++) {
            StartUrl startUrl = new StartUrl("00"+i, "http://"+i+".com", "2");
            startUrlDao.insertStartUrl(startUrl);
        }
    }

}
