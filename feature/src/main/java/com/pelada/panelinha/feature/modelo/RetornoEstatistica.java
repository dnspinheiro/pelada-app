package com.pelada.panelinha.feature.modelo;

import java.util.ArrayList;

public class RetornoEstatistica {

    private String type;
    private Integer status;
    private String message;
    private ArrayList<Estatistica> result;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Estatistica> getResult() {
        return result;
    }

    public void setResult(ArrayList<Estatistica> result) {
        this.result = result;
    }
}
