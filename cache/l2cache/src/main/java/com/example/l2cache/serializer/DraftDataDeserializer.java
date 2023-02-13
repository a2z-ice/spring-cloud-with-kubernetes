package com.example.l2cache.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DraftDataDeserializer extends JsonDeserializer<DraftData> {
    @Override
    public DraftData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String type = node.get("type").asText();

        if (type.equals("concrete_type_1")) {
                return jsonParser.getCodec().treeToValue(node, ConcreteDraft.class);
        } else {
            DraftMap draftMap = jsonParser.getCodec().treeToValue(node, DraftMap.class);
            return draftMap;
        }
    }
}
