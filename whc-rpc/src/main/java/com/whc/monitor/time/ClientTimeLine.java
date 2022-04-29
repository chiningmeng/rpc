package com.whc.monitor.time;

import com.whc.enums.WhereEnum;
import lombok.Data;

@Data
public class ClientTimeLine extends TimeLine {

    private int getConnectTime;
    private int loadBalanceTime;
    private int waitForResponseTime;
    private int getServerListTime;

    public ClientTimeLine() {
        super();
    }
    public ClientTimeLine(String uid, WhereEnum messageType) {
        super(uid, messageType);
    }

    @Override
    public void phaseEnd(Phase phase) {
        long phaseEndTimeStamp = System.currentTimeMillis();
        int consumeTime = (int) (phaseEndTimeStamp - phaseStarTimeStamp);
        switch (phase) {
            case SERIALIZE:
                this.serializeTime = consumeTime;
                break;
            case DESERIALIZE:
                this.deserializeTime = consumeTime;
                break;
            case GET_CONNECT:
                this.getConnectTime = consumeTime;
                break;
            case WAIT_FOR_RESPONSE:
                this.waitForResponseTime = consumeTime;
                break;
            case GET_SERVER_LIST:
                this.getServerListTime = consumeTime;
                break;
            case LOAD_BALANCE:
                this.loadBalanceTime = consumeTime;
                break;
        }

    }

}
