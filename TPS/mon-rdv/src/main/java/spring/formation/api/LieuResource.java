package spring.formation.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

import spring.formation.model.Lieu;
import spring.formation.model.Views;
import spring.formation.repository.ILieuRepository;

@RestController
@RequestMapping("/api/lieu")
public class LieuResource {

	private ILieuRepository lieuRepo;

	public LieuResource(ILieuRepository lieuRepo) {
		super();
		this.lieuRepo = lieuRepo;
	}

	@GetMapping("")
	@JsonView(Views.ViewLieu.class)
	public List<Lieu> getAll() {
		return this.lieuRepo.findAll();
	}

	@GetMapping("/{id}")
	@JsonView(Views.ViewLieu.class)
	public Lieu get(@PathVariable Long id) {
		return this.lieuRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("/{id}/detail")
	@JsonView(Views.ViewLieuDetail.class)
	public Lieu getDetail(@PathVariable Long id) {
		return this.lieuRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@PostMapping("")
	@JsonView(Views.ViewLieu.class)
	@PreAuthorize("hasRole('ADMIN')")
	public Lieu post(@RequestBody Lieu lieu) {
		lieu = lieuRepo.save(lieu);

		return lieu;
	}

	@PutMapping("/{id}")
	@JsonView(Views.ViewLieu.class)
	@PreAuthorize("hasRole('ADMIN')")
	public Lieu put(@RequestBody Lieu lieu, @PathVariable Long id) {
		if (id != lieu.getId() || !lieuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le lieu ne peut être mis à jour.");
		}

		return lieuRepo.save(lieu);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		if (!lieuRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le lieu ne peut être supprimé car inexistant.");
		}

		lieuRepo.deleteById(id);
	}
}
