package com.example.finalProjectDesignPatterns.services;


import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.dto.ReturnType;
import com.example.finalProjectDesignPatterns.entity.Tables;
import com.example.finalProjectDesignPatterns.repository.TableRepository;
import com.example.finalProjectDesignPatterns.state.AvailableState;
import com.example.finalProjectDesignPatterns.state.OccupiedState;
import com.example.finalProjectDesignPatterns.state.ReservedState;
import com.example.finalProjectDesignPatterns.state.TableState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableService {

    private final Map<String, TableState> tableStateMap;

    private final TableRepository tableStatusRepository;


    @Autowired
    public TableService(TableRepository tableStatusRepository, AvailableState availableState, OccupiedState occupiedState, ReservedState reservedState) {
        this.tableStatusRepository = tableStatusRepository;
        tableStateMap = new HashMap<>();
        tableStateMap.put("Available", availableState);
        tableStateMap.put("Occupied", occupiedState);
        tableStateMap.put("Reserved", reservedState);

    }

    public TableState getState(String state){
        return tableStateMap.get(state);
    }

    public String getStateName(TableState state){
        return state.getName();
    }
    public ResponseEntity<Tables> createTable(int tableNumber, int capacity) {
        Tables table = new Tables();
        table.setTableNumber(tableNumber);
        table.setCapacity(capacity);
        Tables temp = tableStatusRepository.findByTableNumber(tableNumber);
        if (temp != null) {
            return new ResponseEntity<>("Table already exists", temp, ReturnType.FAILURE);
        } else {
            table.setState(tableStateMap.get("AvailableState"));
            table.setCurrentState("Available");
        }
        return tableStatusRepository.saveAndCatch(table);
    }

    public ResponseEntity<Tables> getTable(int tableNumber) {
        Tables table = tableStatusRepository.findByTableNumber(tableNumber);
        if (table == null) {
            return new ResponseEntity<>("Table does not exist", null, ReturnType.FAILURE);
        }
        table.setState(tableStateMap.get(table.getCurrentState()));
        return new ResponseEntity<>("Table found", table, ReturnType.SUCCESS);
    }

    public ResponseEntity<List<Tables>> getAll() {
        List<Tables> tables = tableStatusRepository.findAll();
        if (CollectionUtils.isEmpty(tables)) {
            return new ResponseEntity<>("No tables found", null, ReturnType.FAILURE);
        }
        return new ResponseEntity<>("Tables found", tables, ReturnType.SUCCESS);
    }

    public ResponseEntity<Tables> reserveTable(int tableNumber) {
        Tables table = tableStatusRepository.findByTableNumber(tableNumber);
        if (table == null) {
            return new ResponseEntity<>("Table does not exist", null, ReturnType.FAILURE);
        }
        table.setState(tableStateMap.get(table.getCurrentState()));
        return table.reserveTable();
    }

    public ResponseEntity<Tables> occupyTable(int tableNumber) {
        Tables table = tableStatusRepository.findByTableNumber(tableNumber);
        if (table == null) {
            return new ResponseEntity<>("Table does not exist", null, ReturnType.FAILURE);
        }
        table.setState(tableStateMap.get(table.getCurrentState()));
        return table.occupyTable();
    }

    public ResponseEntity<Tables> freeTable(int tableNumber) {
        Tables table = tableStatusRepository.findByTableNumber(tableNumber);
        if (table == null) {
            return new ResponseEntity<>("Table does not exist", null, ReturnType.FAILURE);
        }
        table.setState(tableStateMap.get(table.getCurrentState()));
        return table.freeTable();
    }

    public ResponseEntity<Tables> updateTable(int tableNumber, int capacity) {
        Tables table = tableStatusRepository.findByTableNumber(tableNumber);
        if (table == null) {
            return new ResponseEntity<>("Table does not exist", null, ReturnType.FAILURE);
        }
        table.setCapacity(capacity);
        return tableStatusRepository.saveAndCatch(table);
    }

    public ResponseEntity<Tables> save(Tables table) {
        return tableStatusRepository.saveAndCatch(table);
    }
}
