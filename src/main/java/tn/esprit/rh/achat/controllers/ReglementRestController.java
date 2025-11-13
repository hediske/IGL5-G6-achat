package tn.esprit.rh.achat.controllers;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.services.IReglementService;
import tn.esprit.rh.achat.dtos.ReglementDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Gestion des reglements")
@RequestMapping("/reglement")
@CrossOrigin("*")
public class ReglementRestController {

    @Autowired
    IReglementService reglementService;


    // http://localhost:8089/SpringMVC/reglement/add-reglement
    @PostMapping("/add-reglement")
    @ResponseBody
    public ReglementDTO addReglement(@RequestBody ReglementDTO r) {
        return toDto(reglementService.addReglement(toEntity(r)));
    }
    @GetMapping("/retrieve-all-reglements")
    @ResponseBody
    public List<ReglementDTO> getReglement() {
        return reglementService.retrieveAllReglements().stream().map(this::toDto).collect(Collectors.toList());
    }

    // http://localhost:8089/SpringMVC/reglement/retrieve-reglement/8
    @GetMapping("/retrieve-reglement/{reglement-id}")
    @ResponseBody
    public ReglementDTO retrieveReglement(@PathVariable("reglement-id") Long reglementId) {
        return toDto(reglementService.retrieveReglement(reglementId));
    }

    // http://localhost:8089/SpringMVC/reglement/retrieveReglementByFacture/8
    @GetMapping("/retrieveReglementByFacture/{facture-id}")
    @ResponseBody
    public List<ReglementDTO> retrieveReglementByFacture(@PathVariable("facture-id") Long factureId) {
        return reglementService.retrieveReglementByFacture(factureId).stream().map(this::toDto).collect(Collectors.toList());
    }

    // http://localhost:8089/SpringMVC/reglement/getChiffreAffaireEntreDeuxDate/{startDate}/{endDate}
    @GetMapping(value = "/getChiffreAffaireEntreDeuxDate/{startDate}/{endDate}")
    public float getChiffreAffaireEntreDeuxDate(
            @PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {
            return reglementService.getChiffreAffaireEntreDeuxDate(startDate, endDate);
        } catch (Exception e) {
            return 0;
        }
    }

    private ReglementDTO toDto(Reglement e) {
        if (e == null) return null;
        ReglementDTO d = new ReglementDTO();
        d.setIdReglement(e.getIdReglement());
        d.setMontantPaye(e.getMontantPaye());
        d.setMontantRestant(e.getMontantRestant());
        d.setDateReglement(e.getDateReglement());
        d.setFactureId(e.getFacture() != null ? e.getFacture().getIdFacture() : null);
        return d;
    }

    private Reglement toEntity(ReglementDTO d) {
        if (d == null) return null;
        Reglement e = new Reglement();
        e.setIdReglement(d.getIdReglement());
        e.setMontantPaye(d.getMontantPaye());
        e.setMontantRestant(d.getMontantRestant());
        e.setDateReglement(d.getDateReglement());
        return e;
    }
}
