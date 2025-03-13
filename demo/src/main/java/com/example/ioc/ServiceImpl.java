package com.example.ioc;
@org.springframework.stereotype.Service
public class ServiceImpl implements Service {
    private MyRepository repository;
    public ServiceImpl(MyRepository repository){
        this.repository = repository;
    }
    @Override
    public void save(){
        repository.save();
    }
}
