
package tn.esprit.rh.achat.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockDTO {
    private Long idStock;
    private Integer qteStock;
    private Integer qteMin;
}