package org.kok202.dluid.ai.entity;

import lombok.Data;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.util.RandomUtil;

@Data
public class Layer {
    protected final long id;
    protected final LayerType type;
    protected LayerProperties properties;

    public Layer(LayerType type) {
        this.type = type;
        this.id = RandomUtil.getPositiveLong();
        this.properties = new LayerProperties(type);
    }
}
