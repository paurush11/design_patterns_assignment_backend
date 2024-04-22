package com.example.finalProjectDesignPatterns.state;


import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.entity.Tables;
import com.example.finalProjectDesignPatterns.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservedState implements TableState{

    @Autowired
    private TableRepository repository;

    @Override
    public String getName() {
        return "Reserved";
    }

    @Override
    public ResponseEntity<Tables> reserveTable(Tables table) {
        return new ResponseEntity<>( "Table already reserved", table, ReturnType.FAILURE);
    }

    @Override
    public ResponseEntity<Tables> occupyTable(Tables table) {
        table.setState(new OccupiedState());
        table.setCurrentState("Occupied");
        repository.save(table);
        return new ResponseEntity<>( "Table Occupied", table, ReturnType.SUCCESS);
    }

    @Override
    public ResponseEntity<Tables> freeTable(Tables table) {
        table.setState(new AvailableState());
        table.setCurrentState("Available");
        repository.save(table);
        return new ResponseEntity<>( "Reservation expired, table available to occupy", table, ReturnType.SUCCESS);
    }
}
