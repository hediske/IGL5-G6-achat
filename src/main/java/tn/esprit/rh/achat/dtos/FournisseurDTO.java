
package tn.esprit.rh.achat.dtos;

import lombok.*;
import tn.esprit.rh.achat.entities.CategorieFournisseur;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FournisseurDTO {
    private Long idFournisseur;
    private String code;
    private String libelle;
    private CategorieFournisseur categorieFournisseur;
}