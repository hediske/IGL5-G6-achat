
package tn.esprit.rh.achat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategorieProduitDTO {
    private Long idCategorieProduit;
    private String codeCategorie;
    private String libelleCategorie;
}