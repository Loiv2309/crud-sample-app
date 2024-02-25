package hr.comping.crud.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

import java.io.IOException;


/**
 * Configuration class for jackson
 */
@Configuration
public class JacksonConfig {


    /**
     * Configuration for JsonView to work with Page<> objects
     */
    @Bean
    public Module springDataPageModule() {
        return new SimpleModule().addSerializer(Page.class, new JsonSerializer<>() {
            @Override
            public void serialize(Page value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeStartObject();
                gen.writeNumberField("totalElements", value.getTotalElements());
                gen.writeNumberField("totalPages", value.getTotalPages());
                gen.writeNumberField("number", value.getNumber());
                gen.writeNumberField("size", value.getSize());
                gen.writeBooleanField("first", value.isFirst());
                gen.writeBooleanField("last", value.isLast());
                gen.writeObjectField("sort", value.getSort());
                gen.writeFieldName("content");
                serializers.defaultSerializeValue(value.getContent(), gen);
                gen.writeEndObject();
            }
        });
    }
}
