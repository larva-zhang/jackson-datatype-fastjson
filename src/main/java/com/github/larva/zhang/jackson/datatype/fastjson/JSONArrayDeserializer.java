package com.github.larva.zhang.jackson.datatype.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.util.ClassUtil;

import java.io.IOException;

/**
 * Deserializer for com.alibaba.fastjson.JSONArray
 *
 * @author zhanghan
 */
@SuppressWarnings("WeakerAccess")
public class JSONArrayDeserializer extends StdDeserializer<JSONArray> {
    private static final long serialVersionUID = 1L;

    public final static JSONArrayDeserializer INSTANCE = new JSONArrayDeserializer();

    public JSONArrayDeserializer() {
        super(JSONArray.class);
    }

    @Override
    public JSONArray deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        // 07-Jan-2019, tatu: As per [datatype-json-org#15], need to verify it's an Array
        if (!p.isExpectedStartArrayToken()) {
            final JsonToken t = p.currentToken();
            return (JSONArray) ctxt.handleUnexpectedToken(handledType(), t, p,
                    "Unexpected token (%s), expected START_ARRAY for %s value",
                    t, ClassUtil.nameOf(handledType()));
        }

        JSONArray array = new JSONArray();
        JsonToken t;
        while ((t = p.nextToken()) != JsonToken.END_ARRAY) {
            switch (t) {
                case START_ARRAY:
                    array.add(deserialize(p, ctxt));
                    continue;
                case START_OBJECT:
                    array.add(JSONObjectDeserializer.INSTANCE.deserialize(p, ctxt));
                    continue;
                case VALUE_STRING:
                    array.add(p.getText());
                    continue;
                case VALUE_NULL:
                    array.add(null);
                    continue;
                case VALUE_TRUE:
                    array.add(Boolean.TRUE);
                    continue;
                case VALUE_FALSE:
                    array.add(Boolean.FALSE);
                    continue;
                case VALUE_NUMBER_INT:
                    array.add(p.getNumberValue());
                    continue;
                case VALUE_NUMBER_FLOAT:
                    array.add(p.getNumberValue());
                    continue;
                case VALUE_EMBEDDED_OBJECT:
                    array.add(p.getEmbeddedObject());
                    continue;
                default:
                    return (JSONArray) ctxt.handleUnexpectedToken(handledType(), p);
            }
        }
        return array;
    }
}
