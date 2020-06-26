package xktz.game.serializable;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableList<T> extends ArrayList<T> implements Serializable {
    public SerializableList() {
        super();
    }

    public SerializableList(int len) {
        super(len);
    }
}
