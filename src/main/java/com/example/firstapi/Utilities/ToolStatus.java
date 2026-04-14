package com.example.firstapi.Utilities;

public enum ToolStatus {
    ACTIVE("active"),
    ARCHIVED("archived");

    private final String Status;

    ToolStatus(String value){
        this.Status = value;
    }

    public String Status(){
        return this.Status;
    }

    public static ToolStatus fromDb(String value){
        for (ToolStatus s : values()){
            if (s.Status().equals(value)) return s;
        }
        return null;
    }

}
