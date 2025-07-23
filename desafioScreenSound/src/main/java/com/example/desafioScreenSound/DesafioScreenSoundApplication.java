package com.example.desafioScreenSound;

import com.example.desafioScreenSound.Principal.Principal;
import com.example.desafioScreenSound.Repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioScreenSoundApplication implements CommandLineRunner {
	@Autowired
	private ArtistaRepository repositorio;


	public static void main(String[] args) {
		SpringApplication.run(DesafioScreenSoundApplication.class, args);


	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}
