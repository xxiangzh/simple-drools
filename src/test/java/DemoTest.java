import com.xzh.drools.Application;
import com.xzh.drools.entity.DemoEntity;
import com.xzh.drools.utils.KieUtils;
import com.xzh.drools.utils.ResourceUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 向振华
 * @date 2022/07/25 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoTest {

    @Test
    public void test1() {
        DemoEntity entity = new DemoEntity("小明");
        String read = ResourceUtils.read("classpath:rules/demo.drl");
        KieUtils.fireAllRules(entity, null, read);
    }
}
