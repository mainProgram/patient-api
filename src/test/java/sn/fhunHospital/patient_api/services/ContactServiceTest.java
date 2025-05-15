package sn.fhunHospital.patient_api.services;


import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.model.ContactEntity;
import sn.fhunHospital.patient_api.repository.ContactRepository;
import sn.fhunHospital.patient_api.service.impl.ContactServiceImpl;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnum;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    private List<ContactRequest> contactRequests;
    private List<ContactEntity> contactEntities;

    @BeforeEach
    void setUp() {
        // Configuration des ContactRequest
        ContactRequest contactRequest1 = new ContactRequest();
        contactRequest1.setId("contact1");
        contactRequest1.setType(TypeContactEnum.MOBILE);
        contactRequest1.setContact("771234567");

        ContactRequest contactRequest2 = new ContactRequest();
        contactRequest2.setId("contact2");
        contactRequest2.setType(TypeContactEnum.EMAIL);
        contactRequest2.setContact("test@example.com");

        contactRequests = List.of(contactRequest1, contactRequest2);

        // Configuration des ContactEntity
        ContactEntity contactEntity1 = new ContactEntity();
        contactEntity1.setId("contact1");
        contactEntity1.setType(TypeContactEnum.MOBILE);
        contactEntity1.setContact("771234567");

        ContactEntity contactEntity2 = new ContactEntity();
        contactEntity2.setId("contact2");
        contactEntity2.setType(TypeContactEnum.EMAIL);
        contactEntity2.setContact("test@example.com");

        contactEntities = List.of(contactEntity1, contactEntity2);
    }

    @Test
    void testSaveContacts() {
        // Arrangement
        // Au lieu de mocker la méthode statique, nous allons simuler le résultat
        // de la méthode saveAll du repository
        when(contactRepository.saveAll(any())).thenReturn(contactEntities);

        // Action
        List<ContactEntity> result = contactService.saveContacts(contactRequests);

        // Assertion
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("contact1", result.get(0).getId());
        assertEquals("contact2", result.get(1).getId());
        verify(contactRepository, times(1)).saveAll(any());
    }

    @Test
    void testSaveContactsEntities() {
        // Arrangement
        when(contactRepository.saveAll(contactEntities)).thenReturn(contactEntities);

        // Action
        List<ContactEntity> result = contactService.saveContactsEntities(contactEntities);

        // Assertion
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("contact1", result.get(0).getId());
        assertEquals("contact2", result.get(1).getId());
        verify(contactRepository, times(1)).saveAll(contactEntities);
    }

    @Test
    void testDeleteContacts() {
        // Arrangement
        doNothing().when(contactRepository).deleteAll(contactEntities);

        // Action
        contactService.deleteContacts(contactEntities);

        // Assertion
        verify(contactRepository, times(1)).deleteAll(contactEntities);
    }

    @Test
    void testSaveContactsWithEmptyList() {
        // Arrangement
        List<ContactRequest> emptyContactRequests = new ArrayList<>();
        List<ContactEntity> emptyContactEntities = new ArrayList<>();

        when(contactRepository.saveAll(any())).thenReturn(emptyContactEntities);

        // Action
        List<ContactEntity> result = contactService.saveContacts(emptyContactRequests);

        // Assertion
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(contactRepository, times(1)).saveAll(any());
    }

    @Test
    void testSaveContactsEntitiesWithEmptyList() {
        // Arrangement
        List<ContactEntity> emptyContactEntities = new ArrayList<>();
        when(contactRepository.saveAll(emptyContactEntities)).thenReturn(emptyContactEntities);

        // Action
        List<ContactEntity> result = contactService.saveContactsEntities(emptyContactEntities);

        // Assertion
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(contactRepository, times(1)).saveAll(emptyContactEntities);
    }

    @Test
    void testDeleteContactsWithEmptyList() {
        // Arrangement
        List<ContactEntity> emptyContactEntities = new ArrayList<>();
        doNothing().when(contactRepository).deleteAll(emptyContactEntities);

        // Action
        contactService.deleteContacts(emptyContactEntities);

        // Assertion
        verify(contactRepository, times(1)).deleteAll(emptyContactEntities);
    }
}
