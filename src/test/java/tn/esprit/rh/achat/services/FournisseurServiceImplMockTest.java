package tn.esprit.rh.achat.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FournisseurServiceImplMockTest {

    @InjectMocks
    FournisseurServiceImpl fournisseurService;

    @Mock
    FournisseurRepository fournisseurRepository;
    @Mock
    DetailFournisseurRepository detailFournisseurRepository;
    @Mock
    SecteurActiviteRepository secteurActiviteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllFournisseurs() {
        Fournisseur f1 = new Fournisseur();
        f1.setIdFournisseur(1L);
        Fournisseur f2 = new Fournisseur();
        f2.setIdFournisseur(2L);

        when(fournisseurRepository.findAll()).thenReturn(Arrays.asList(f1, f2));

        List<Fournisseur> result = fournisseurService.retrieveAllFournisseurs();
        assertEquals(2, result.size());
        verify(fournisseurRepository).findAll();
    }

    @Test
    void testAddFournisseur() {
        Fournisseur f = new Fournisseur();
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(f);

        Fournisseur saved = fournisseurService.addFournisseur(f);

        assertNotNull(saved.getDetailFournisseur());
        verify(fournisseurRepository).save(f);
    }

    @Test
    void testRetrieveFournisseur() {
        Fournisseur f = new Fournisseur();
        f.setIdFournisseur(5L);
        when(fournisseurRepository.findById(5L)).thenReturn(Optional.of(f));

        Fournisseur result = fournisseurService.retrieveFournisseur(5L);
        assertNotNull(result);
        assertEquals(5L, result.getIdFournisseur());
    }

    @Test
    void testAssignSecteurActiviteToFournisseur() {
        Fournisseur f = new Fournisseur();
        f.setSecteurActivites(new HashSet<>());

        SecteurActivite sa = new SecteurActivite();

        when(fournisseurRepository.findById(1L)).thenReturn(Optional.of(f));
        when(secteurActiviteRepository.findById(2L)).thenReturn(Optional.of(sa));

        fournisseurService.assignSecteurActiviteToFournisseur(2L, 1L);

        assertTrue(f.getSecteurActivites().contains(sa));
        verify(fournisseurRepository).save(f);
    }

    @Test
    void testDeleteFournisseur() {
        fournisseurService.deleteFournisseur(1L);
        verify(fournisseurRepository).deleteById(1L);
    }
}
