package com.fuelcalculator.stouta.fuelcalculator;

import java.io.Serializable;


public class CarModel implements Serializable {
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
        public float combined;
        public float urban;
        public float motorway;
        public float fuelTankCapacity;
        public String make;
        public String model;
        public String fuelType;
    }
}
