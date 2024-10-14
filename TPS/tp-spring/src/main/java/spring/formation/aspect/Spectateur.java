package spring.formation.aspect;

public class Spectateur {

	// Before avant jouer()
	public void assoir() {
		System.out.println("Les spectateurs s'assoient");
	}
	
	// After quand tout va bien
	public void applaudir() {
		System.out.println("Les spectateurs applaudissent");
	}
	
	// After en cas d'erreur
	public void rembourser() {
		System.out.println("BOUH !!! Remboursez !!!");
	}
}
