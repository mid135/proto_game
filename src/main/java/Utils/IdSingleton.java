package Utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mid-s on 07.08.16.
 */
public class IdSingleton {
    private IdSingleton(){}
    private static AtomicInteger id = new AtomicInteger(0);
    public static Integer getNewId() {
        return id.incrementAndGet();
    }
}
