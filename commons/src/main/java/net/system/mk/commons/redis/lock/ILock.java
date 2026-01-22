package net.system.mk.commons.redis.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author USER
 */
@AllArgsConstructor
@Getter
public class ILock implements AutoCloseable{

    private Object lock;

    private IDistributedLock distributedLock;

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(lock)){
            distributedLock.unLock(lock);
        }
    }
}
