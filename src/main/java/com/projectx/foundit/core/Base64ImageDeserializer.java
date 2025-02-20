package com.projectx.foundit.core;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Base64;

public class Base64ImageDeserializer extends JsonDeserializer<byte[]> {
    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();

        // Check if it's a Data URL
        if (value.startsWith("data:")) {
            // Extract the base64 part (after the comma)
            int commaIndex = value.indexOf(',');
            if (commaIndex > 0) {
                String base64Data = value.substring(commaIndex + 1);
                return Base64.getDecoder().decode(base64Data);
            }
        }

        // If it's not a Data URL, try to decode directly
        try {
            return Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(p, "Invalid base64 content: " + value, e);
        }
    }
}