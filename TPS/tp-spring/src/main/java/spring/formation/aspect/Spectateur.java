package spring.formation.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Spectateur {

	// Before avant jouer()
	@Before("execution(* spring.formation.orchestre.*.jouer())")
	public void assoir() {
		System.out.println("Les spectateurs s'assoient");
	}
	
	// After quand tout va bien
	@AfterReturning("execution(* spring.formation.orchestre.*.jouer())")
	public void applaudir() {
		System.out.println("Les spectateurs applaudissent");
	}
	
	// After en cas d'erreur
	@AfterThrowing("execution(* spring.formation.orchestre.*.jouer())")
	public void rembourser() {
		System.out.println("BOUH !!! Remboursez !!!");
	}
}
