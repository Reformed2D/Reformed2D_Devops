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
        // Create and setup test universities
        universite1 = new Universite(1, "Universite1");
        universite1.setNomUniversite("Universite1");

        universite2 = new Universite(2, "Universite2");
        universite2.setNomUniversite("Universite2");

        // Setup basic mocks
        when(universiteService.retrieveAllUniversites())
                .thenReturn(Arrays.asList(universite1, universite2));
        when(universiteService.retrieveUniversite(1L))
                .thenReturn(universite1);
        when(universiteService.addUniversite(any(Universite.class)))
                .thenReturn(universite1);
        when(universiteService.modifyUniversite(any(Universite.class)))
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
        // When
        Universite result = universiteController.retrieveUniversite(1L);

        // Then
        assertNotNull(result, "Retrieved university should not be null");
        assertEquals("Universite1", result.getNomUniv(), "University name should match");
        verify(universiteService).retrieveUniversite(1L);
    }

    @Test
    void testAddUniversite() {
        // Given
        Universite newUniversite = new Universite(3, "Universite3");
        newUniversite.setNomUniversite("Universite3");

        when(universiteService.addUniversite(newUniversite))
                .thenReturn(newUniversite);

        // When
        Universite result = universiteController.addUniversite(newUniversite);

        // Then
        assertNotNull(result, "Added university should not be null");
        assertEquals("Universite3", result.getNomUniv(), "Added university name should match");
        verify(universiteService).addUniversite(newUniversite);
    }

    @Test
    void testRemoveUniversite() {
        // When
        universiteController.removeUniversite(1L);

        // Then
        verify(universiteService).removeUniversite(1L);
    }

    @Test
    void testModifyUniversite() {
        // Given
        Universite updatedUniversite = new Universite(1, "UpdatedUniversite");
        updatedUniversite.setNomUniversite("UpdatedUniversite");

        when(universiteService.modifyUniversite(updatedUniversite))
                .thenReturn(updatedUniversite);

        // When
        Universite result = universiteController.modifyUniversite(updatedUniversite);

        // Then
        assertNotNull(result, "Modified university should not be null");
        assertEquals("UpdatedUniversite", result.getNomUniv(), "Modified university name should match");
        verify(universiteService).modifyUniversite(updatedUniversite);
    }
}