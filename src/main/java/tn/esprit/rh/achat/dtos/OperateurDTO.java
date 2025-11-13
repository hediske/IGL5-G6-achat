
package tn.esprit.rh.achat.dtos;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OperateurDTO {
    private Long idOperateur;
    private String nom;
    private String prenom;
    private String password;
}