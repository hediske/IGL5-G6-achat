package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FactureServiceImplIntegrationTest {

```
@Autowired
FactureServiceImpl factureService;

@Autowired
FactureRepository factureRepository;

@Test
void testAddAndRetrieveFacture() {
    Facture f = new Facture();
    f.setMontantFacture(500);
    f.setMontantRemise(50);
    f.setArchivee(false);

    Facture saved = factureService.addFacture(f);
    assertNotNull(saved.getIdFacture());

    Facture retrieved = factureService.retrieveFacture(saved.getIdFacture());
    assertEquals(500, retrieved.getMontantFacture());
}

@Test
void testRetrieveAllFactures() {
    List<Facture> all = factureService.retrieveAllFactures();
    assertNotNull(all);
}

@Test
void testCancelFacture() {
    Facture f = new Facture();
    f.setArchivee(false);
    f = factureRepository.save(f);

    factureService.cancelFacture(f.getIdFacture());
    Facture updated = factureRepository.findById(f.getIdFacture()).orElseThrow();

    assertTrue(updated.getArchivee());
}
```

}
