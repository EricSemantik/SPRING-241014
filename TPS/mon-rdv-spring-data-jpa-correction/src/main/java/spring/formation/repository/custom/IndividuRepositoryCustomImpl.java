package spring.formation.repository.custom;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import spring.formation.model.Praticien;

public class IndividuRepositoryCustomImpl implements IndividuRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Praticien> search(String nom, String prenom) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Praticien> query = cb.createQuery(Praticien.class); // Query<Praticien>
        Root<Praticien> p = query.from(Praticien.class); // from Praticien p

        Path<String> nomPath = p.get("nom"); // p.nom
        Path<String> prenomPath = p.get("prenom"); // p.prenom

        List<Predicate> predicates = new ArrayList<>();

        if(!nom.isEmpty()) {
        	predicates.add(cb.like(nomPath, nom+"%"));
        }
        
        if(!prenom.isEmpty()) {
        	predicates.add(cb.like(prenomPath, prenom+"%"));
        }
        
        query.select(p)
            .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));  // where p.nom like :nom (%) and p.prenom = :prenom (%)
        
        return entityManager.createQuery(query)
            .getResultList();
	}

}
