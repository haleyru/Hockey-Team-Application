package persistence;

import org.json.JSONObject;

// ** Uses parts of the JsonSerializationDemo program **
public interface Writable {

    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

