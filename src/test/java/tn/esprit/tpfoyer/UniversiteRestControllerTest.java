package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteRestControllerTest {

    @Mock
    private UniversiteServiceImpl universiteService;

    @InjectMocks
    private UniversiteRestController universiteController;

    private Universite universite1;
    private Universite universite2;

    @BeforeEach
    void setUp() {
        universite1 = new Universite(1, "Universite1");
        universite2 = new Universite(2, "Universite2");

        // Setup basic mocks
        when(universiteService.retrieveAllUniversites())
                .thenReturn(Arrays.asList(universite1, universite2));
        when(universiteService.retrieveUniversite(1L))
                .thenReturn(universite1);
    }

    @Test
    void testGetAllUniversites() {
        List<Universite> result = universiteController.getUniversites();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(universite1.getNomUniv(), result.get(0).getNomUniv());
    }

    @Test
    void testGetUniversiteById() {
        Universite result = universiteController.retrieveUniversite(1L);

        assertNotNull(result);
        assertEquals(universite1.getNomUniv(), result.getNomUniv());
    }
}