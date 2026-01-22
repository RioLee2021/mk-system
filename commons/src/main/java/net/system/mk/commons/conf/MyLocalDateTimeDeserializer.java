package net.system.mk.commons.conf;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import net.system.mk.commons.utils.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author USER
 */
public class MyLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        long timestamp = jsonParser.getValueAsLong();
        return timestamp < 0?null: DateTimeUtils.toLocalDateTime(timestamp);
    }
}
