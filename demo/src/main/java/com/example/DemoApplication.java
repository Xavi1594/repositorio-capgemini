package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.Configuration;
import com.example.ioc.Service;
import com.example.ioc.ServiceImpl;
import com.example.util.Calculadora;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private final ServiceImpl serviceImpl;

    private final Configuration configuration;

    DemoApplication(Configuration configuration, ServiceImpl serviceImpl) {
        this.configuration = configuration;
        this.serviceImpl = serviceImpl;
    }

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	Service srv;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("arrancado");
		srv.save();
		dataExample();

	}
	@Autowired
	private ActorRepository dao;
		private void dataExample(){
			var actor = new Actor(0,"El Bicho", "Pichichi");
			dao.save(actor);
			var item = dao.findById(22);
			if (item.isPresent()){
				 actor = item.get();
				actor.setFirstName("El Bicho");
				actor.setLastName(actor.getLastName().toUpperCase());
				dao.save(actor);
			}
			else {
				System.out.println("No hay actor bro");
			}
		dao.findAll().forEach(System.err::println);
		dao.deleteById(22);
	}

}
