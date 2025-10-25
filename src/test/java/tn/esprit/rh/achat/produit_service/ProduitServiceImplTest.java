    package tn.esprit.rh.achat.produit_service;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    import java.util.*;

    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.*;
    import tn.esprit.rh.achat.entities.Produit;
    import tn.esprit.rh.achat.entities.Stock;
    import tn.esprit.rh.achat.repositories.ProduitRepository;
    import tn.esprit.rh.achat.repositories.StockRepository;
    import tn.esprit.rh.achat.services.ProduitServiceImpl;
    import tn.esprit.rh.achat.repositories.CategorieProduitRepository;

    class ProduitServiceImplTest {

        @InjectMocks
        ProduitServiceImpl produitService;

        @Mock
        ProduitRepository produitRepository;

        @Mock
        StockRepository stockRepository;

        @Mock
        CategorieProduitRepository categorieProduitRepository;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testAddProduit() {
            Produit produit = new Produit();
            produit.setLibelleProduit("Produit Test");
            when(produitRepository.save(produit)).thenReturn(produit);

            Produit savedProduit = produitService.addProduit(produit);

            assertNotNull(savedProduit);
            assertEquals("Produit Test", savedProduit.getLibelleProduit());
            verify(produitRepository, times(1)).save(produit);
        }

        @Test
        void testRetrieveProduit() {
            Produit produit = new Produit();
            produit.setIdProduit(1L);
            produit.setLibelleProduit("Produit Test");
            when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));

            Produit retrieved = produitService.retrieveProduit(1L);

            assertNotNull(retrieved);
            assertEquals(1L, retrieved.getIdProduit());
            verify(produitRepository, times(1)).findById(1L);
        }

        @Test
        void testUpdateProduit() {
            Produit produit = new Produit();
            produit.setIdProduit(1L);
            produit.setLibelleProduit("Ancien Produit");

            when(produitRepository.save(produit)).thenReturn(produit);

            produit.setLibelleProduit("Produit Mis à Jour");
            Produit updated = produitService.updateProduit(produit);

            assertEquals("Produit Mis à Jour", updated.getLibelleProduit());
            verify(produitRepository, times(1)).save(produit);
        }

        @Test
        void testDeleteProduit() {
            Long idProduit = 1L;
            doNothing().when(produitRepository).deleteById(idProduit);

            produitService.deleteProduit(idProduit);

            verify(produitRepository, times(1)).deleteById(idProduit);
        }

        @Test
        void testAssignProduitToStock() {
            Produit produit = new Produit();
            produit.setIdProduit(1L);

            Stock stock = new Stock();
            stock.setIdStock(1L);

            when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
            when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
            when(produitRepository.save(produit)).thenReturn(produit);

            produitService.assignProduitToStock(1L, 1L);

            assertEquals(stock, produit.getStock());
            verify(produitRepository, times(1)).save(produit);
        }
    }
