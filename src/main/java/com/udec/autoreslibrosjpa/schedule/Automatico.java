package com.udec.autoreslibrosjpa.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Automatico {
	
	@Scheduled(fixedRate = 5000)
	public void ejecutar() {
		System.out.println("Hola mundo!");		
	}	

}
