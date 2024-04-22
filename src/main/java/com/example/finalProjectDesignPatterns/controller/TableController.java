package com.example.finalProjectDesignPatterns.controller;



import com.example.finalProjectDesignPatterns.dto.CreateTableDto;
import com.example.finalProjectDesignPatterns.dto.ResponseEntity;
import com.example.finalProjectDesignPatterns.entity.Tables;
import com.example.finalProjectDesignPatterns.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class TableController {


    @Autowired
    private TableService tableService;

    @RequestMapping(value = "/create" ,method = RequestMethod.POST)
    public ResponseEntity<Tables> createTable(@RequestBody CreateTableDto createTableDto){
        return tableService.createTable(createTableDto.getTableNumber(), createTableDto.getCapacity());
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Tables>> getAllTables(){
        return tableService.getAll();
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<Tables> reserveTable(@RequestParam int tableNumber){
        return tableService.reserveTable(tableNumber);
    }

    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    public ResponseEntity<Tables> occupyTable(@RequestParam int tableNumber){
        return tableService.occupyTable(tableNumber);
    }

    @RequestMapping(value = "/free", method = RequestMethod.POST)
    public ResponseEntity<Tables> freeTable(@RequestParam int tableNumber){
        return tableService.freeTable(tableNumber);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Tables> getTable(@RequestParam int tableNumber){
        return tableService.getTable(tableNumber);
    }
}
