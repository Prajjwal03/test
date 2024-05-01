package com.smbc.epix.transaction.services.configuration;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.smbc.epix.transaction.services.utils.NGLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomSqlDateSerializer extends JsonSerializer<Date> {

    @Autowired
    private NGLogger ngLogger;

    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider serializers) throws IOException {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateFormat.format(date);
            generator.writeString(dateStr);
        } catch (DateTimeParseException e) {
            ngLogger.errorLog("Exception in CustomSqlDateSerializer : " + e);
            generator.writeString("");
        }
    }
}
