package org.kok202.deepblock.ai.entity;

import lombok.Data;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;

import java.util.Random;

@Data
public class Layer {
    protected final long id;
    protected final LayerType type;
    protected LayerProperties properties;

    public Layer(LayerType type) {
        this.type = type;
        this.id = new Random().nextLong();
        this.properties = new LayerProperties(type);
    }
}
