package tn.esprit.tpfoyer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UniversiteServiceImpTest {

    @Mock
    UniversiteRepository universiteRepository;

    @InjectMocks
    UniversiteServiceImpl iUniversiteService;

    @Test
    void testRetrieveUniversiteById() {
        // Création d'une instance de Universite pour le test
        Universite universite = new Universite(10, "george");

        // Mock de la méthode findById
        when(universiteRepository.findById(10L)).thenReturn(Optional.of(universite));

        // Appel du service pour récupérer l'université par ID
        Universite universiteById = iUniversiteService.retrieveUniversite(10L);

        // Vérifications
        assertNotNull(universiteById);
        assertEquals("george", universiteById.getNomUniv());
    }

    @Test
    void testRetrieveAllUniversites() {
        // Création de deux instances de Universite pour le test
        Universite universite1 = new Universite(9, "ben");
        Universite universite2 = new Universite(8, "kevin");

        // Mock de la méthode findAll
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(universite1, universite2));

        // Appel du service pour récupérer toutes les universités
        List<Universite> universiteList = iUniversiteService.retrieveAllUniversites();

        // Vérifications
        assertEquals(2, universiteList.size());
        assertEquals("ben", universiteList.get(0).getNomUniv());
        assertEquals("kevin", universiteList.get(1).getNomUniv());
    }
}
