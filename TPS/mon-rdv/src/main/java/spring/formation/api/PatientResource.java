package spring.formation.api;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;
import spring.formation.api.request.PatientRequest;
import spring.formation.model.Adresse;
import spring.formation.model.Civilite;
import spring.formation.model.Patient;
import spring.formation.model.Role;
import spring.formation.model.Utilisateur;
import spring.formation.model.Views;
import spring.formation.repository.IIndividuRepository;
import spring.formation.repository.IUtilisateurRepository;

@RestController
@RequestMapping("/api/patient")
public class PatientResource {

	private IIndividuRepository individuRepo;

	private IUtilisateurRepository utilisateurRepo;

	public PatientResource(IIndividuRepository individuRepo, IUtilisateurRepository utilisateurRepo) {
		super();
		this.individuRepo = individuRepo;
		this.utilisateurRepo = utilisateurRepo;
	}

	@GetMapping("")
	@JsonView(Views.ViewPatient.class)
	public List<Patient> getAll() {
		return this.individuRepo.findAllPatient();
	}

	@GetMapping("/{id}")
	@JsonView(Views.ViewPatient.class)
	public Patient get(@PathVariable Long id) {
		return (Patient) this.individuRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@GetMapping("/{id}/detail")
	@JsonView(Views.ViewPatientDetail.class)
	public Patient getDetail(@PathVariable Long id) {
		return this.individuRepo.findPatientByIdWithConsultations(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping("")
	@JsonView(Views.ViewPatient.class)
	public Patient post(@RequestBody Patient patient) {
		patient = individuRepo.save(patient);

		return patient;
	}

	@PutMapping("/{id}")
	@JsonView(Views.ViewPatient.class)
	public Patient put(@RequestBody Patient patient, @PathVariable Long id) {
		if (id != patient.getId() || !individuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le patient ne peut être mis à jour.");
		}

		return individuRepo.save(patient);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!individuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le patient ne peut être supprimé car inexistant.");
		}

		individuRepo.deleteById(id);
	}

	@PostMapping("/inscription")
	@JsonView(Views.ViewPatient.class)
	public Patient inscription(@RequestBody @Valid PatientRequest patientRequest, BindingResult result) {
		if(result.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erreur !!!");
		}
		
		Adresse adresse = new Adresse();
		BeanUtils.copyProperties(patientRequest, adresse);

		Utilisateur utilisateur = new Utilisateur();
		BeanUtils.copyProperties(patientRequest, utilisateur);
		utilisateur.setActive(true);
		utilisateur.getRoles().add(Role.PATIENT);
		
		utilisateur = utilisateurRepo.save(utilisateur);

		Patient patient = new Patient();
		BeanUtils.copyProperties(patientRequest, patient);
		patient.setCivilite(Civilite.valueOf(patientRequest.getCivilite()));
		patient.setDtNaissance(LocalDate.parse(patientRequest.getDtNaissance()));
		
		patient.setAdresse(adresse);
		patient.setUtilisateur(utilisateur);

		return individuRepo.save(patient);
	}
}
