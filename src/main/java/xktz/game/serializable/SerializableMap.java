package xktz.game.serializable;

import java.io.Serializable;
import java.util.HashMap;

public class SerializableMap<K, V> extends HashMap<K, V> implements Serializable {
    public SerializableMap() {
        super();
    }
}
