package spring.formation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.formation.model.Motif;

public interface IMotifRepository extends JpaRepository<Motif, Long> {

}
