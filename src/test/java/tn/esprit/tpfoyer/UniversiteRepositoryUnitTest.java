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
        universite = new Universite(1, "Esprit");
        when(universiteRepository.findById(1L))
                .thenReturn(Optional.of(universite));
    }

    @Test
    void testGetUniversiteById() {
        Optional<Universite> result = universiteRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Esprit", result.get().getNomUniv());
    }
}