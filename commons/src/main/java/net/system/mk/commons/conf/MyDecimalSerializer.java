package net.system.mk.commons.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author USER
 * @date 2025-03-2025/3/15/0015 11:11
 */
public class MyDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (bigDecimal!=null){
            jsonGenerator.writeString(bigDecimal.stripTrailingZeros().toPlainString());
        }
    }
}
