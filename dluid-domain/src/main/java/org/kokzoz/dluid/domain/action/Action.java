package org.kokzoz.dluid.domain.action;

import lombok.Data;

@Data
public class Action {
    private ActionType type;
    private Object payload;

    public Action(ActionType type) {
        this.type = type;
    }

    public Action(ActionType type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
}
