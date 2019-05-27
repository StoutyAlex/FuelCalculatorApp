package com.fuelcalculator.stouta.fuelcalculator;

import java.io.Serializable;

public class FuelCostModel implements Serializable {
    public String success;
    public ClientData data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ClientData getClientData() {
        return data;
    }

    public void setClientData(ClientData clientData) {
        this.data = clientData;
    }

    public class ClientData implements Serializable {
        public float duration;
        public float distance;
        public int fuelTankCapacity;
        public float tanksUsed;
        public float litresUSed;
        public String start;
        public String end;
        public float averageSpeed;
        public float journeyCost;
        public float mpg;
    }
}