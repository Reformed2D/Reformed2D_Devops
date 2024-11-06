package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UniversiteRestControllerTest {

    @Mock
    private UniversiteServiceImpl universiteService;

    @InjectMocks
    private UniversiteRestController universiteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllUniversites() {
        // Données fictives
        Universite universite1 = new Universite(1, "Universite1");
        Universite universite2 = new Universite(2, "Universite2");
        List<Universite> universiteList = Arrays.asList(universite1, universite2);

        // Simulation du comportement
        when(universiteService.retrieveAllUniversites()).thenReturn(universiteList);

        // Exécution du test
        List<Universite> result = universiteController.getUniversites();

        // Vérification des interactions et assertions
        verify(universiteService, times(1)).retrieveAllUniversites();
        assertEquals(2, result.size());
        assertEquals("Universite1", result.get(0).getNomUniv());
        assertEquals("Universite2", result.get(1).getNomUniv());
    }

    @Test
    void testGetUniversiteById() {
        // Données fictives
        Universite universite = new Universite(1, "Universite1");

        // Simulation du comportement
        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        // Exécution du test
        Universite result = universiteController.retrieveUniversite(1L);

        // Vérification des interactions et assertions
        verify(universiteService, times(1)).retrieveUniversite(1L);
        assertNotNull(result);
        assertEquals("Universite1", result.getNomUniv());
    }
}
