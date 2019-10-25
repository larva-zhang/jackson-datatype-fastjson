/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.larva.zhang.jackson.datatype.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Tests to verify that we can also use JSONObject and JSONArray
 * with polymorphic type information.
 */
public class TypeInformationTest extends TestBase {
    static class ObjectWrapper {
        public Object value;

        public ObjectWrapper(Object v) {
            value = v;
        }

        public ObjectWrapper() {
        }
    }

    public void testWrappedArray() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());
        mapper.enableDefaultTyping();
        JSONArray jsonArray = JSON.parseArray("[13]");
        String json = mapper.writeValueAsString(new ObjectWrapper(jsonArray));
        assertEquals("{\"value\":[\"com.alibaba.fastjson.JSONArray\",[13]]}", json);

        ObjectWrapper result = mapper.readValue(json, ObjectWrapper.class);
        assertEquals(JSONArray.class, result.value.getClass());
        JSONArray resultArray = (JSONArray) result.value;
        assertEquals(1, resultArray.size());
        assertEquals(13, resultArray.getIntValue(0));
    }

    public void testWrappedObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());
        mapper.enableDefaultTyping();
        JSONObject jsonObject = JSON.parseObject("{\"a\":true}");
        String json = mapper.writeValueAsString(new ObjectWrapper(jsonObject));
        assertEquals("{\"value\":[\"com.alibaba.fastjson.JSONObject\",{\"a\":true}]}", json);

        ObjectWrapper result = mapper.readValue(json, ObjectWrapper.class);
        assertEquals(JSONObject.class, result.value.getClass());
        JSONObject resultOb = (JSONObject) result.value;
        assertEquals(1, resultOb.size());
        assertTrue(resultOb.getBoolean("a"));
    }
}
