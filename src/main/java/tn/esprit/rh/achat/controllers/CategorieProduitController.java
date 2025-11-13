package tn.esprit.rh.achat.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.dtos.CategorieProduitDTO;
import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.services.ICategorieProduitService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Gestion des categories Produit")
@RequestMapping("/categorieProduit")
public class CategorieProduitController {

	@Autowired
	ICategorieProduitService categorieProduitService;
	
	// http://localhost:8089/SpringMVC/categorieProduit/retrieve-all-categorieProduit
	@GetMapping("/retrieve-all-categorieProduit")
	@ResponseBody
	public List<CategorieProduitDTO> getCategorieProduit() {
		return categorieProduitService.retrieveAllCategorieProduits()
				.stream().map(this::toDto).collect(Collectors.toList());
	}

	// http://localhost:8089/SpringMVC/categorieProduit/retrieve-categorieProduit/8
	@GetMapping("/retrieve-categorieProduit/{categorieProduit-id}")
	@ResponseBody
	public CategorieProduitDTO retrieveCategorieProduit(@PathVariable("categorieProduit-id") Long categorieProduitId) {
		return toDto(categorieProduitService.retrieveCategorieProduit(categorieProduitId));
	}

	// http://localhost:8089/SpringMVC/categorieProduit/add-categorieProduit
	@PostMapping("/add-categorieProduit")
	@ResponseBody
	public CategorieProduitDTO addCategorieProduit(@RequestBody CategorieProduitDTO cp) {
		return toDto(categorieProduitService.addCategorieProduit(toEntity(cp)));
	}

	// http://localhost:8089/SpringMVC/categorieProduit/remove-categorieProduit/{categorieProduit-id}
	@DeleteMapping("/remove-categorieProduit/{categorieProduit-id}")
	@ResponseBody
	public void removeCategorieProduit(@PathVariable("categorieProduit-id") Long categorieProduitId) {
		categorieProduitService.deleteCategorieProduit(categorieProduitId);
	}

	// http://localhost:8089/SpringMVC/categorieProduit/modify-categorieProduit
	@PutMapping("/modify-categorieProduit")
	@ResponseBody
	public CategorieProduitDTO modifyCategorieProduit(@RequestBody CategorieProduitDTO categorieProduit) {
		return toDto(categorieProduitService.updateCategorieProduit(toEntity(categorieProduit)));
	}

	private CategorieProduitDTO toDto(CategorieProduit e) {
		if (e == null) return null;
		return new CategorieProduitDTO(e.getIdCategorieProduit(), e.getCodeCategorie(), e.getLibelleCategorie());
	}

	private CategorieProduit toEntity(CategorieProduitDTO d) {
		if (d == null) return null;
		CategorieProduit e = new CategorieProduit();
		e.setIdCategorieProduit(d.getIdCategorieProduit());
		e.setCodeCategorie(d.getCodeCategorie());
		e.setLibelleCategorie(d.getLibelleCategorie());
		return e;
	}
	
}
