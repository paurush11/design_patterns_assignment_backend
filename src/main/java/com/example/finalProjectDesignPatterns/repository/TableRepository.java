package com.example.finalProjectDesignPatterns.repository;


import com.example.finalProjectDesignPatterns.entity.Tables;

public interface TableRepository extends BaseRepository<Tables>{

    Tables findByTableNumber(int tableNumber);

}
