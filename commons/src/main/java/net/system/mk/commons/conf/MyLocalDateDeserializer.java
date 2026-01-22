package net.system.mk.commons.conf;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.system.mk.commons.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDate;

/**
 * @author USER
 * @date 2025-03-2025/3/19/0019 20:51
 */
public class MyLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        long timestamp = jsonParser.getValueAsLong();
        return timestamp < 0?null: DateTimeUtils.toLocalDate(timestamp);
    }
}
