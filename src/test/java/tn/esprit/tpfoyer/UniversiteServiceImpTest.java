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
        // Create and setup test universities
        universite1 = new Universite(10, "george");
        universite1.setNomUniversite("george");

        universite2 = new Universite(11, "ben");
        universite2.setNomUniversite("ben");

        // Setup mocks
        when(universiteRepository.findById(10L))
                .thenReturn(Optional.of(universite1));
        when(universiteRepository.findAll())
                .thenReturn(Arrays.asList(universite1, universite2));
        when(universiteRepository.save(any(Universite.class)))
                .thenReturn(universite1);
    }

    @Test
    void testRetrieveUniversiteById() {
        // When
        Universite result = universiteService.retrieveUniversite(10L);

        // Then
        assertNotNull(result, "Retrieved university should not be null");
        assertEquals("george", result.getNomUniv(), "University name should match");
        verify(universiteRepository).findById(10L);
    }

    @Test
    void testRetrieveUniversiteById_NotFound() {
        // Given
        when(universiteRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Universite result = universiteService.retrieveUniversite(999L);

        // Then
        assertNull(result, "Should return null for non-existent university");
        verify(universiteRepository).findById(999L);
    }

    @Test
    void testRetrieveAllUniversites() {
        // When
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Then
        assertNotNull(result, "Result list should not be null");
        assertEquals(2, result.size(), "Should return two universities");
        assertEquals("george", result.get(0).getNomUniv(), "First university name should match");
        assertEquals("ben", result.get(1).getNomUniv(), "Second university name should match");
        verify(universiteRepository).findAll();
    }

    @Test
    void testRetrieveAllUniversites_EmptyList() {
        // Given
        when(universiteRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Then
        assertNotNull(result, "Result list should not be null even when empty");
        assertTrue(result.isEmpty(), "Result list should be empty");
        verify(universiteRepository).findAll();
    }

    @Test
    void testAddUniversite() {
        // Given
        Universite newUniversite = new Universite(12, "newUniv");
        newUniversite.setNomUniversite("newUniv");
        when(universiteRepository.save(any(Universite.class))).thenReturn(newUniversite);

        // When
        Universite result = universiteService.addUniversite(newUniversite);

        // Then
        assertNotNull(result, "Added university should not be null");
        assertEquals("newUniv", result.getNomUniv(), "Added university name should match");
        verify(universiteRepository).save(newUniversite);
    }

    @Test
    void testRemoveUniversite() {
        // Given
        doNothing().when(universiteRepository).deleteById(10L);

        // When
        universiteService.removeUniversite(10L);

        // Then
        verify(universiteRepository).deleteById(10L);
    }

    @Test
    void testModifyUniversite() {
        // Given
        Universite updatedUniversite = new Universite(10, "updatedGeorge");
        updatedUniversite.setNomUniversite("updatedGeorge");
        when(universiteRepository.save(any(Universite.class))).thenReturn(updatedUniversite);
        Universite result = universiteService.modifyUniversite(updatedUniversite);

        // Then
        assertNotNull(result, "Modified university should not be null");
        assertEquals("updatedGeorge", result.getNomUniv(), "Modified university name should match");
        verify(universiteRepository).save(updatedUniversite);
    }

    @Test
    void testModifyUniversite_WithNullInput() {
        Universite nullUniversite = null;

        // When/Then
        assertThrows(IllegalArgumentException.class, () -> {
            universiteService.modifyUniversite(nullUniversite);
        }, "Should throw IllegalArgumentException for null input");

        verify(universiteRepository, never()).save(any());
    }
}


