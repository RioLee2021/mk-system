package net.system.mk.commons.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author USER
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GATime implements Serializable {

    private Long timeout;

    private TimeUnit unit;
}
