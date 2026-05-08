package com.nodeajva.maiv_java.domain;

public enum NpcState {
    IDLE("쉬고 있습니다"),
    TALKING("대화 중입니다"),
    WAITING("기다리고 있습니다");


    private final String beschreibung;


     NpcState(String beschreibung){
        this.beschreibung  = beschreibung ;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}
