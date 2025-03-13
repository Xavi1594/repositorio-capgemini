package com.example.domains.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.contracts.services.ActorsService;
import com.example.domains.entities.Actor;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service
public class ActorsServiceImpl implements ActorsService {

    private ActorRepository dao;

    public ActorsServiceImpl(ActorRepository dao){
        this.dao = dao;
    }

    @Override
    public List <Actor> getAll() {
        return dao.findAll();
    }

    @Override
    public Optional<Actor> getOne(Integer id) {
        return dao.findById(id);
    }

    @Override
	public Actor add(Actor item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("El actor no puede ser nulo");
		}
		if(item.getActorId() > 0 && dao.existsById(item.getActorId())) {
			throw new DuplicateKeyException("El actor ya existe");
		}
		return dao.save(item);
	}


    @Override
    public Actor modify(Actor item) throws NotFoundException, InvalidDataException {
        // TODO Auto-generated method stub
       return null;
    }

    @Override
    public void delete(Actor item) throws InvalidDataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void distributePrices() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'distributePrices'");
    }

}
