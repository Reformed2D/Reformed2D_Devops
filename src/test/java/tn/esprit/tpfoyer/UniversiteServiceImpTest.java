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
        universite2 = new Universite(11, "ben");

        when(universiteRepository.findById(10L))
                .thenReturn(Optional.of(universite1));
        when(universiteRepository.findAll())
                .thenReturn(Arrays.asList(universite1, universite2));
    }

    @Test
    void testRetrieveUniversiteById() {
        Universite result = universiteService.retrieveUniversite(10L);

        assertNotNull(result);
        assertEquals("george", result.getNomUniv());
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ben", result.get(1).getNomUniv());
    }
}


