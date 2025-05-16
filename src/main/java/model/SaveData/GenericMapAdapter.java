package model.SaveData;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GenericMapAdapter<K, V> extends TypeAdapter<Map<K, V>> {

    private final Class<K> keyClass;
    private final TypeAdapter<K> keyAdapter;
    private final TypeAdapter<V> valueAdapter;

    public GenericMapAdapter(Gson gson, Class<K> keyClass, TypeToken<V> valueType) {
        this.keyClass = keyClass;
        this.keyAdapter = gson.getAdapter(TypeToken.get(keyClass));
        this.valueAdapter = gson.getAdapter(valueType);
    }

    @Override
    public void write(JsonWriter out, Map<K, V> map) throws IOException {
        out.beginArray();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            out.beginObject();
            out.name("key");
            keyAdapter.write(out, entry.getKey());
            out.name("value");
            valueAdapter.write(out, entry.getValue());
            out.endObject();
        }
        out.endArray();
    }

    @Override
    public Map<K, V> read(JsonReader in) throws IOException {
        Map<K, V> map = new HashMap<>();
        in.beginArray();
        while (in.hasNext()) {
            in.beginObject();
            K key = null;
            V value = null;
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "key" -> key = keyAdapter.read(in);
                    case "value" -> value = valueAdapter.read(in);
                }
            }
            if (key != null) map.put(key, value);
            in.endObject();
        }
        in.endArray();
        return map;
    }
}