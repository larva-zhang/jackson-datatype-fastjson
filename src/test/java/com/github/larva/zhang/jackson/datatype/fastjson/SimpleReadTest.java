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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleReadTest extends TestBase {
    public void testReadObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());

        JSONObject ob = mapper.readValue("{\"a\":{\"b\":3}, \"c\":[9, -4], \"d\":null, \"e\":true}",
                JSONObject.class);
        assertEquals(4, ob.size());
        JSONObject ob2 = ob.getJSONObject("a");
        assertEquals(1, ob2.size());
        assertEquals(3, ob2.getIntValue("b"));
        JSONArray array = ob.getJSONArray("c");
        assertEquals(2, array.size());
        assertEquals(9, array.getIntValue(0));
        assertEquals(-4, array.getIntValue(1));
        assertNull(ob.get("d"));
        assertTrue(ob.getBoolean("e"));
    }

    public void testReadArray() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());
        JSONArray jacksonParsedArray = mapper.readValue("[null, 13, false, 1.25, \"abc\", {\"a\":13}, [ ] ]",
                JSONArray.class);
        assertEquals(7, jacksonParsedArray.size());
        assertNull(jacksonParsedArray.get(0));
        assertEquals(13, jacksonParsedArray.getIntValue(1));
        assertFalse(jacksonParsedArray.getBoolean(2));
        assertEquals(Double.valueOf(1.25), jacksonParsedArray.getDouble(3));
        assertEquals("abc", jacksonParsedArray.getString(4));
        JSONObject ob = jacksonParsedArray.getJSONObject(5);
        assertEquals(1, ob.size());
        assertEquals(13, ob.getIntValue("a"));
        JSONArray array2 = jacksonParsedArray.getJSONArray(6);
        assertEquals(0, array2.size());
    }
}
