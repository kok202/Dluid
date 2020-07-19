package org.kok202.dluid.domain.action;

import lombok.Data;

@Data
public class Action {
    private ActionType type;
    private Object payload;

    public Action(ActionType type) {
        this.type = type;
    }
}
