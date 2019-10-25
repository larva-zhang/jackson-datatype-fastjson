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
package com.zxl.jackson.datatype.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleWriteTest extends TestBase {
    public void testWriteObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());

        // Ok: let's create JSONObject from jsonString text
        String jsonString = "{\"a\":{\"b\":3}}";
        JSONObject jsonObject = JSON.parseObject(jsonString);
        assertEquals(jsonString, mapper.writeValueAsString(jsonObject));
    }

    public void testWriteArray() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FastJsonModule());

        // Ok: let's create JSONObject from jsonString text
        String jsonString = "[1,true,\"text\",[null,3],{\"a\":[1.25]}]";
        JSONArray jsonArray = JSON.parseArray(jsonString);
        assertEquals(jsonString, mapper.writeValueAsString(jsonArray));
    }
}
