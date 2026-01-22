package net.system.mk.commons.conf;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.system.mk.commons.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author USER
 * @date 2025-03-2025/3/19/0019 20:40
 */
public class MyLocalDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value!=null){
            gen.writeNumber(DateTimeUtils.toEpochMilli(value));
        } else {
            gen.writeNull();
        }
    }
}
