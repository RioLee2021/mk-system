package net.system.mk.commons.conf;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import net.system.mk.commons.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author USER
 */
public class MyLocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        if (value!=null){
            gen.writeNumber(DateTimeUtils.toEpochMilli(value));
        } else {
            gen.writeNull();
        }
    }

}
