package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImpTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite1;
    private Universite universite2;

    @BeforeEach
    void setUp() {
        universite1 = new Universite(10, "george");
        universite1.setNomUniversite("george");

        universite2 = new Universite(11, "ben");
        universite2.setNomUniversite("ben");

        // Use lenient() to prevent unnecessary stubbing warnings
        lenient().when(universiteRepository.findById(10L)).thenReturn(Optional.of(universite1));
        lenient().when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite1, universite2));
        lenient().when(universiteRepository.save(any(Universite.class))).thenReturn(universite1);
    }

    @Test
    void testRetrieveUniversiteById() {
        Universite result = universiteService.retrieveUniversite(10L);

        assertNotNull(result, "Retrieved university should not be null");
        assertEquals("george", result.getNomUniversite(), "University name should match");
        verify(universiteRepository).findById(10L);
    }

    @Test
    void testRetrieveUniversiteById_NotFound() {
        // Stubbing to return Optional.empty for non-existent ID
        when(universiteRepository.findById(999L)).thenReturn(Optional.empty());

        Universite result = universiteService.retrieveUniversite(999L);

        assertNull(result, "Should return null for non-existent university");
        verify(universiteRepository).findById(999L);
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result, "Result list should not be null");
        assertEquals(2, result.size(), "Should return two universities");
        assertEquals("george", result.get(0).getNomUniversite(), "First university name should match");
        assertEquals("ben", result.get(1).getNomUniversite(), "Second university name should match");
        verify(universiteRepository).findAll();
    }

    @Test
    void testRetrieveAllUniversites_EmptyList() {
        // Stubbing to return an empty list
        when(universiteRepository.findAll()).thenReturn(Arrays.asList());

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result, "Result list should not be null even when empty");
        assertTrue(result.isEmpty(), "Result list should be empty");
        verify(universiteRepository).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite newUniversite = new Universite(12, "newUniv");
        newUniversite.setNomUniversite("newUniv");

        // Stubbing to return the new university
        when(universiteRepository.save(any(Universite.class))).thenReturn(newUniversite);

        Universite result = universiteService.addUniversite(newUniversite);

        assertNotNull(result, "Added university should not be null");
        assertEquals("newUniv", result.getNomUniversite(), "Added university name should match");
        verify(universiteRepository).save(newUniversite);
    }

    @Test
    void testRemoveUniversite() {
        // Stubbing deleteById to do nothing when called
        doNothing().when(universiteRepository).deleteById(10L);

        universiteService.removeUniversite(10L);

        verify(universiteRepository).deleteById(10L);
    }

    @Test
    void testModifyUniversite() {
        Universite updatedUniversite = new Universite(10, "updatedGeorge");
        updatedUniversite.setNomUniversite("updatedGeorge");

        // Stubbing save method to return updated university
        when(universiteRepository.save(any(Universite.class))).thenReturn(updatedUniversite);

        Universite result = universiteService.modifyUniversite(updatedUniversite);

        assertNotNull(result, "Modified university should not be null");
        assertEquals("updatedGeorge", result.getNomUniversite(), "Modified university name should match");
        verify(universiteRepository).save(updatedUniversite);
    }

    @Test
    void testModifyUniversite_WithNullInput() {
        Universite nullUniversite = null;

        // Assert that an exception is thrown when trying to modify with null input
        assertThrows(IllegalArgumentException.class, () -> {
            universiteService.modifyUniversite(nullUniversite);
        }, "Should throw IllegalArgumentException for null input");

        // Verify that save was never called
        verify(universiteRepository, never()).save(any());
    }
}
