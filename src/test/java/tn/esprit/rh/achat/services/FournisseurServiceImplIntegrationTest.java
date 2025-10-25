package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.rh.achat.entities.Fournisseur;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FournisseurServiceImplIntegrationTest {

    @Autowired
    FournisseurServiceImpl fournisseurService;

    @Test
    void testAddAndRetrieveFournisseur() {
        Fournisseur f = new Fournisseur();
        f.setCode("F001");
        f.setLibelle("Fournisseur Test");

        Fournisseur saved = fournisseurService.addFournisseur(f);
        assertNotNull(saved.getIdFournisseur());
        assertNotNull(saved.getDetailFournisseur());

        Fournisseur retrieved = fournisseurService.retrieveFournisseur(saved.getIdFournisseur());
        assertEquals("F001", retrieved.getCode());
    }

    @Test
    void testRetrieveAllFournisseurs() {
        List<Fournisseur> all = fournisseurService.retrieveAllFournisseurs();
        assertNotNull(all);
    }

    @Test
    void testDeleteFournisseur() {
        Fournisseur f = new Fournisseur();
        f.setCode("DEL");
        f = fournisseurService.addFournisseur(f);

        fournisseurService.deleteFournisseur(f.getIdFournisseur());
        Fournisseur deleted = fournisseurService.retrieveFournisseur(f.getIdFournisseur());
        assertNull(deleted);
    }
}
