
package tn.esprit.rh.achat.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProduitDTO {
    private Long idProduit;
    private String code;
    private String libelle;
    private Float prixUnitaire;
    private Long categorieProduitId;
    private Long stockId;
}