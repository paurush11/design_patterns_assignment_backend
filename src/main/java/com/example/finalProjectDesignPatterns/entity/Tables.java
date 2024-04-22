package com.example.finalProjectDesignPatterns.entity;


import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.state.AvailableState;
import com.example.finalProjectDesignPatterns.state.OccupiedState;
import com.example.finalProjectDesignPatterns.state.ReservedState;
import com.example.finalProjectDesignPatterns.state.TableState;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tables")
@Data
public class Tables {



    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "table_number", nullable = false)
    private int tableNumber;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "current_state")
    private String currentState;

    @Transient
    private TableState state;

    public ResponseEntity<Tables> reserveTable() {
        if(state == null)
            state = fetchState();
        if(state == null)
            return new ResponseEntity<>("Failed to fetch the current table status", this, ReturnType.FAILURE);
        return state.reserveTable(this);
    }

    public ResponseEntity<Tables> occupyTable() {
        if(state == null)
            state = fetchState();
        if(state == null)
            return new ResponseEntity<>("Failed to fetch the current table status", this, ReturnType.FAILURE);
        return state.occupyTable(this);
    }

    public ResponseEntity<Tables> freeTable() {
        if(state == null)
            state = fetchState();
        if(state == null)
            return new ResponseEntity<>("Failed to fetch the current table status", this, ReturnType.FAILURE);
        return state.freeTable(this);
    }

    private TableState fetchState(){
        if(currentState == null){
            return null;
        }else if(currentState.equals("Available")) {
            return new AvailableState();
        }else if(currentState.equals("Occupied")){
                return new OccupiedState();
        }else if(currentState.equals("Reserved")){
            return new ReservedState();
        }
        return null;
    }

}
