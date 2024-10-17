package spring.formation.repository.custom;

import java.util.List;

import spring.formation.model.Praticien;

public interface IndividuRepositoryCustom {
	List<Praticien> search(String nom, String prenom);
}
