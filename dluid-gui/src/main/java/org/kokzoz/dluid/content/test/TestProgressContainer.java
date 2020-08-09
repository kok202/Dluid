package org.kokzoz.dluid.content.test;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class TestProgressContainer {
    private String message;
    private double progress;

    public TestProgressContainer() {
        message = null;
        progress = -1;
    }

    public TestProgressContainer(String message) {
        this();
        this.message = message;
    }

    public TestProgressContainer(double progress) {
        this();
        this.progress = progress;
    }

    public boolean isExistMessage() {
        return StringUtils.isNotEmpty(message);
    }

    public boolean isExistProgress() {
        return progress != -1;
    }
}