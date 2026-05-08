package com.nodeajva.maiv_java.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table()
public class Mesage {

        @Id
        @GeneratedValue
        private Long id;

        @ManyToOne
        @JoinColumn(name = "conversatrtion_id")
        private Conversation conversation;

        @Column
        private String spielerName;

        @Column
        private LocalDateTime begonnenAm;

        @Column
        private LocalDateTime beendetAm;
    }
}
