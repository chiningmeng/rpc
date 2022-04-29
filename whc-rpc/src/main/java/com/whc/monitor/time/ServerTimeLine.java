package com.whc.monitor.time;

import com.whc.enums.WhereEnum;
import lombok.Data;

@Data
public class ServerTimeLine extends TimeLine {

    private int handleTime;

    public ServerTimeLine(String uid, WhereEnum messageType) {
        super(uid, messageType);
    }
    @Override
    public void phaseEnd(Phase phase) {
        long phaseEndTimeStamp = System.currentTimeMillis();
        int consumeTime = (int) (phaseEndTimeStamp - phaseStarTimeStamp);
        switch (phase) {
            case DESERIALIZE:
                this.deserializeTime = consumeTime;
                break;
            case HANDLE:
                handleTime = consumeTime;
                break;
            case SERIALIZE:
                this.serializeTime = consumeTime;
                break;
        }
        if (Phase.SERIALIZE == phase) {
           setEndTimeStamp(phaseEndTimeStamp);
        }
    }

}
