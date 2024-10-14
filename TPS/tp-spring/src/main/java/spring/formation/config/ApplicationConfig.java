package spring.formation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.formation.orchestre.Guitare;
import spring.formation.orchestre.Guitariste;
import spring.formation.orchestre.IInstrument;
import spring.formation.orchestre.IMusicien;
import spring.formation.orchestre.Pianiste;

@Configuration
public class ApplicationConfig {

	@Bean
	public IInstrument guitare() {
		return new Guitare();
	}
	
//	@Bean
//	public IMusicien guitariste(IInstrument instrument) {
//		Guitariste guitariste = new Guitariste();
//		guitariste.setInstrument(instrument);
//		guitariste.setMorceau("Vive le vent ...");
//		
//		return guitariste;
//	}
	
	@Bean
	public IMusicien guitariste(IInstrument instrument) {		
		return new Guitariste(instrument, "Vive le vent ...");
	}
	
	
}
