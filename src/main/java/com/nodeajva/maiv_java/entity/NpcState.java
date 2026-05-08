package com.nodeajva.maiv_java.entity;

public enum NpcZustand {
    RUHIG("쉬고 있습니다"),
    GESPRAECH("대화 중입니다"),
    WARTEN("기다리고 있습니다");


    private final String beschreibung;


     NpcZustand(String beschreibung){
        this.beschreibung  = beschreibung ;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
