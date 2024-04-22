package com.example.finalProjectDesignPatterns.state;


import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.entity.Tables;

public interface TableState {

    ResponseEntity<Tables> reserveTable(Tables table);

    ResponseEntity<Tables> occupyTable(Tables table);

    ResponseEntity<Tables> freeTable(Tables table);

    String getName();
}
