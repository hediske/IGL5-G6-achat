package tn.esprit.rh.achat.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.services.IProduitService;
import tn.esprit.rh.achat.dtos.ProduitDTO;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@Api(tags = "Gestion des produits")
@RequestMapping("/produit")
public class ProduitRestController {

	@Autowired
	IProduitService produitService;

	// http://localhost:8089/SpringMVC/produit/retrieve-all-produits
	@GetMapping("/retrieve-all-produits")
	@ResponseBody
	public List<ProduitDTO> getProduits() {
		return produitService.retrieveAllProduits().stream().map(this::toDto).collect(Collectors.toList());
	}

	// http://localhost:8089/SpringMVC/produit/retrieve-produit/8
	@GetMapping("/retrieve-produit/{produit-id}")
	@ResponseBody
	public ProduitDTO retrieveRayon(@PathVariable("produit-id") Long produitId) {
		return toDto(produitService.retrieveProduit(produitId));
	}

	/* Ajouter en produit tout en lui affectant la catégorie produit et le stock associés */
	// http://localhost:8089/SpringMVC/produit/add-produit/{idCategorieProduit}/{idStock}
	@PostMapping("/add-produit")
	@ResponseBody
	public ProduitDTO addProduit(@RequestBody ProduitDTO p) {
		return toDto(produitService.addProduit(toEntity(p)));
	}

	// http://localhost:8089/SpringMVC/produit/remove-produit/{produit-id}
	@DeleteMapping("/remove-produit/{produit-id}")
	@ResponseBody
	public void removeProduit(@PathVariable("produit-id") Long produitId) {
		produitService.deleteProduit(produitId);
	}

	// http://localhost:8089/SpringMVC/produit/modify-produit/{idCategorieProduit}/{idStock}
	@PutMapping("/modify-produit")
	@ResponseBody
	public ProduitDTO modifyProduit(@RequestBody ProduitDTO p) {
		return toDto(produitService.updateProduit(toEntity(p)));
	}

	/*
	 * Si le responsable magasin souhaite modifier le stock du produit il peut
	 * le faire en l'affectant au stock en question
	 */
	// http://localhost:8089/SpringMVC/produit/assignProduitToStock/1/5
	@PutMapping(value = "/assignProduitToStock/{idProduit}/{idStock}")
	public void assignProduitToStock(@PathVariable("idProduit") Long idProduit, @PathVariable("idStock") Long idStock) {
		produitService.assignProduitToStock(idProduit, idStock);
	}

	/*
	 * Revenu Brut d'un produit (qte * prix unitaire de toutes les lignes du
	 * detailFacture du produit envoyé en paramètre )
	 */
	// http://localhost:8089/SpringMVC/produit/getRevenuBrutProduit/1/{startDate}/{endDate}
/*	@GetMapping(value = "/getRevenuBrutProduit/{idProduit}/{startDate}/{endDate}")
	public float getRevenuBrutProduit(@PathVariable("idProduit") Long idProduit,
			@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
			@PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

		return produitService.getRevenuBrutProduit(idProduit, startDate, endDate);
	}*/

	private ProduitDTO toDto(Produit e) {
		if (e == null) return null;
		ProduitDTO d = new ProduitDTO();
		d.setIdProduit(e.getIdProduit());
		d.setCode(e.getCodeProduit());
		d.setLibelle(e.getLibelleProduit());
		d.setPrixUnitaire(e.getPrix());
		d.setCategorieProduitId(e.getCategorieProduit() != null ? e.getCategorieProduit().getIdCategorieProduit() : null);
		d.setStockId(e.getStock() != null ? e.getStock().getIdStock() : null);
		return d;
	}
	private Produit toEntity(ProduitDTO d) {
		if (d == null) return null;
		Produit e = new Produit();
		e.setIdProduit(d.getIdProduit());
		e.setCodeProduit(d.getCode());
		e.setLibelleProduit(d.getLibelle());
		e.setPrix(d.getPrixUnitaire());
		// set refs via service layer if needed (ids carried in dto)
		return e;
	}

}
