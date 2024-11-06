package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteRestControllerTest {

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteController;

    private Universite universite1;
    private Universite universite2;

    @BeforeEach
    void setUp() {
        universite1 = new Universite(1, "Universite1");
        universite1.setNomUniversite("Universite1");

        universite2 = new Universite(2, "Universite2");
        universite2.setNomUniversite("Universite2");

        // Apply lenient stubbing to avoid UnnecessaryStubbingException
        lenient().when(universiteService.retrieveAllUniversites())
                .thenReturn(Arrays.asList(universite1, universite2));
        lenient().when(universiteService.retrieveUniversite(1L))
                .thenReturn(universite1);
        lenient().when(universiteService.addUniversite(any(Universite.class)))
                .thenReturn(universite1);
        lenient().when(universiteService.modifyUniversite(any(Universite.class)))
                .thenReturn(universite1);
    }

    @Test
    void testGetAllUniversites() {
        List<Universite> result = universiteController.getUniversites();

        assertNotNull(result, "List should not be null");
        assertEquals(2, result.size(), "Should return two universities");
        assertEquals("Universite1", result.get(0).getNomUniv(), "First university name should match");
        verify(universiteService).retrieveAllUniversites();
    }

    @Test
    void testRetrieveUniversiteById() {
        Universite result = universiteController.retrieveUniversite(1L);

        assertNotNull(result, "Retrieved university should not be null");
        assertEquals("Universite1", result.getNomUniv(), "University name should match");
        verify(universiteService).retrieveUniversite(1L);
    }

    @Test
    void testAddUniversite() {
        Universite newUniversite = new Universite(3, "Universite3");
        newUniversite.setNomUniversite("Universite3");

        when(universiteService.addUniversite(newUniversite))
                .thenReturn(newUniversite);

        Universite result = universiteController.addUniversite(newUniversite);

        assertNotNull(result, "Added university should not be null");
        assertEquals("Universite3", result.getNomUniv(), "Added university name should match");
        verify(universiteService).addUniversite(newUniversite);
    }

    @Test
    void testRemoveUniversite() {
        universiteController.removeUniversite(1L);

        verify(universiteService).removeUniversite(1L);
    }

    @Test
    void testModifyUniversite() {
        Universite updatedUniversite = new Universite(1, "UpdatedUniversite");
        updatedUniversite.setNomUniversite("UpdatedUniversite");

        when(universiteService.modifyUniversite(updatedUniversite))
                .thenReturn(updatedUniversite);

        Universite result = universiteController.modifyUniversite(updatedUniversite);

        assertNotNull(result, "Modified university should not be null");
        assertEquals("UpdatedUniversite", result.getNomUniv(), "Modified university name should match");
        verify(universiteService).modifyUniversite(updatedUniversite);
    }
}