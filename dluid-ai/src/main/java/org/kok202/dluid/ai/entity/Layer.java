package org.kok202.dluid.ai.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.util.RandomUtil;

import java.util.HashSet;
import java.util.Set;

@Data
public class Layer {
    protected final String id;
    protected final LayerType type;
    protected LayerProperties properties;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static Set<String> ids = new HashSet<>();

    public Layer(LayerType type) {
        String id;
        do {
            id = type.getReadableName() + " " + String.format("%04d", RandomUtil.getInt(0,9999));
        } while(ids.contains(id));
        ids.add(id);

        this.type = type;
        this.id = id;
        this.properties = new LayerProperties(type);
    }

    public void delete() {
        ids.remove(id);
    }
}
