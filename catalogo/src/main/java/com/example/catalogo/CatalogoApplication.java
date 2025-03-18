package com.example.catalogo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.catalogo.domains.contracts.repositories.LanguageRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication(scanBasePackages = "com.example.catalogo")
public class CatalogoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("Aplicación arrancada");
		prueba();
	}

	@Autowired
	private LanguageRepository dao;

	void prueba() {
		dao.findAll().forEach(System.out::println);
	}
}
