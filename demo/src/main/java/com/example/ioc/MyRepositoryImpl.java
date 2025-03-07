package com.example.ioc;


import org.springframework.stereotype.Repository;

@Repository
public class MyRepositoryImpl implements MyRepository {
    public MyRepositoryImpl(Configuration config){

    }
    @Override
    public void save(){
        System.out.println("Guaardando");
    }

}
