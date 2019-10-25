# jackson-datatype-fastjson
Project to build [Jackson 2.x](https://github.com/FasterXML/jackson) extension module(jar) to support datatypes of the "[com.alibaba.fastjson](https://github.com/alibaba/fastjson)" JSON library, similar to [FasterXML/jackson-datatype-json-org](https://github.com/FasterXML/jackson-datatype-json-org) and [swquinn/jackson-datatype-json-lib](https://github.com/swquinn/jackson-datatype-json-lib).

This project largely was created to fill a need in another project where the [Fastjson](https://github.com/alibaba/fastjson) library was being used, and it wasn't feasible to convert to Jackson for everything. The package namespaces were kept as `com.zxl.jackson.datatype.fastjson` because it's not a official module.

## Usage
### Maven dependency
To use module (version 2.x) on Maven-based projects, use following dependency:
```xml
<dependency>
    <groupId>com.zxl.jackson.datatype</groupId>
    <artifactId>jackson-datatype-fastjson</artifactId>
    <version>2.9.9.3</version>
</dependency>
```
(or whatever version is most up-to-date at the moment)

### Registering module

To use the the Module in Jackson, simply register it with the ObjectMapper instance:

```java
// import com.zxl.jackson.datatype.fastjson.FastJsonModule;

ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new FastJsonModule());
```

This will ensure that basic datatype of `com.alibaba.fastjson` package can be read and written using Jackson data-binding functionality.

### Data conversions

After registering the module, you can read and write JSON to/from `com.alibaba.fastjson.JSONObject` similar to handling custom POJOs or standard JDK types:

```java
JSONObject ob = mapper.readValue(json, JSONObject.class); // read from a source
String json = mapper.writeValue(ob); // output as String
```

As well as do conversion to/from POJOs:

```java
MyValue value = mapper.convertValue(jsonObject, MyValue.class);
JSONObject jsonObject = mapper.convertValue(value, JSONObject.class);
```

or to/from Tree Model:

```java
JsonNode root = mapper.valueToTree(jsonObject);
jsonObject = mapper.treeToValue(root, JSONObject.class);
```

Similarly, you can read/write/convert-to/convert-from `com.alibaba.fastjson.JSONArray` instead of `com.alibaba.fastjson.JSONObject`.
