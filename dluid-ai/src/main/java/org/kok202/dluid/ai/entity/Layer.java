package org.kok202.dluid.ai.entity;

import lombok.Data;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

import java.util.Random;

@Data
public class Layer {
    protected final long id;
    protected final LayerType type;
    protected LayerProperties properties;
    protected Object extra;

    public Layer(LayerType type) {
        this.type = type;
        this.id = new Random().nextLong();
        this.properties = new LayerProperties(type);
    }
}
