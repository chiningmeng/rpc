import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.serialize.Serializer;
import org.junit.Test;

import java.net.URL;
import java.util.Enumeration;

public class GetTypeTest {
    @Test
    public void test() {
        System.out.println(Serializer.class.getName());
    }

    @Test
    public void test1() {
        TimeLine timeLine = new ClientTimeLine();
        System.out.println(timeLine instanceof ClientTimeLine);
    }

}
