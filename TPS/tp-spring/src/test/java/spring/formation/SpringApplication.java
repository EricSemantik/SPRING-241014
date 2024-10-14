package spring.formation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import spring.formation.config.ApplicationConfig;
import spring.formation.orchestre.IMusicien;

public class SpringApplication {

	public static void main(String[] args) {
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		IMusicien guitariste = context.getBean("guitariste", IMusicien.class);
		guitariste.jouer();
		
		IMusicien pianiste = context.getBean("pianiste", IMusicien.class);
		pianiste.jouer();
		
		
		context.close();
	}

}
