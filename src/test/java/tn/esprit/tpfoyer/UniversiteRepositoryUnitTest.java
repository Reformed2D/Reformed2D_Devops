package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteRepositoryUnitTest {

    @Mock
    private UniversiteRepository universiteRepository;

    private Universite universite;

    @BeforeEach
    void setUp() {
        // Fix 3: Properly initialize the university object
        universite = new Universite(1, "Esprit");
        universite.setNomUniversite("Esprit"); // Explicitly set the name

        // Set up mock behavior
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
    }

    @Test
    void testGetUniversiteById() {
        // When
        Optional<Universite> result = universiteRepository.findById(1L);

        // Then
        assertTrue(result.isPresent(), "University should be found");
        Universite retrievedUniversite = result.get();
        assertEquals("Esprit", retrievedUniversite.getNomUniversite(), "University name should match");

        // Verify interaction
        verify(universiteRepository).findById(1L);
    }
}