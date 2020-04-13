package testvideo;

import com.baizhi.wyj.util.AliyunShortMessage;
import org.junit.Test;

public class ALi {
    @Test
    public void name() {
        String message = AliyunShortMessage.getMessage("17612996910");
        System.out.println("message = " + message);
    }
}
