package spring.formation.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import spring.formation.model.Role;
import spring.formation.model.Utilisateur;
import spring.formation.repository.IUtilisateurRepository;
import spring.formation.web.request.UtilisateurRequest;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@Autowired
	private IUtilisateurRepository utilisateurRepo;

	@GetMapping({ "", "/list" }) // ETAPE 1 : Réception de la Request
	public String list(Model model) {
		List<Utilisateur> utilisateurs = utilisateurRepo.findAll(); // ETAPE 2 : Récupérer les données

		model.addAttribute("utilisateurs", utilisateurs); // ETAPE 3 : Renseigner le Model

		return "utilisateur/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("roles", Role.values());
		model.addAttribute("utilisateur", new Utilisateur());

		return "utilisateur/form";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam("idUtilisateur") Long id, Model model) {
		Optional<Utilisateur> optUtilisateur = utilisateurRepo.findById(id);

		if (optUtilisateur.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		model.addAttribute("roles", Role.values());
		model.addAttribute("utilisateur", optUtilisateur.get());

		return "utilisateur/form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute("utilisateur") @Valid UtilisateurRequest utilisateurRequest, BindingResult result, Model model) {
		if(!utilisateurRequest.getMotDePasse().contains("@")) {
			result.rejectValue("motDePasse", "motDePasse.confirmError", "Le mot de passe doit contenir un @");
		}
		
		if(result.hasErrors()) {
			model.addAttribute("roles", Role.values());
			return "utilisateur/form";
		}
		
		if (utilisateurRequest.getId() != null && !utilisateurRepo.existsById(utilisateurRequest.getId())) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404));
		}
		
		Utilisateur utilisateur = new Utilisateur();
		
		BeanUtils.copyProperties(utilisateurRequest, utilisateur);
		
		utilisateurRequest.getRoles().forEach(r -> {
			utilisateur.getRoles().add(Role.valueOf(r));
		});

		utilisateurRepo.save(utilisateur);

		return "redirect:list";
	}

	@GetMapping("/cancel")
	public String cancel() {
		return "forward:list";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("idUtilisateur") Long id) {
		if (!utilisateurRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatusCode.valueOf(404));
		}

		utilisateurRepo.deleteById(id);

		return "redirect:../list";
	}
	
}
