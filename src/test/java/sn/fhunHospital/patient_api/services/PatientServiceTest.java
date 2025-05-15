package sn.fhunHospital.patient_api.services;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.dao.EmptyResultDataAccessException;
import sn.fhunHospital.patient_api.dto.requests.ContactRequest;
import sn.fhunHospital.patient_api.dto.requests.PatientRequest;
import sn.fhunHospital.patient_api.dto.responses.ContactResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientResponse;
import sn.fhunHospital.patient_api.dto.responses.PatientWithContactResponse;
import sn.fhunHospital.patient_api.model.ContactEntity;
import sn.fhunHospital.patient_api.model.PatientEntity;
import sn.fhunHospital.patient_api.repository.PatientRepository;
import sn.fhunHospital.patient_api.service.ContactService;
import sn.fhunHospital.patient_api.service.impl.PatientServiceImpl;
import sn.fhunHospital.patient_api.utils.enums.SexeEnum;
import sn.fhunHospital.patient_api.utils.enums.TypeContactEnum;
import sn.fhunHospital.patient_api.utils.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private PatientServiceImpl patientService;

    private PatientEntity patientEntity;
    private PatientRequest patientRequest;
    private PatientResponse patientResponse;
    private PatientWithContactResponse patientWithContactResponse;
    private List<ContactEntity> contactEntities;
    private List<ContactRequest> contactRequests;
    private List<ContactResponse> contactResponses;

    @BeforeEach
    void setUp() {
        // Setup ContactEntity
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId("contact1");
        contactEntity.setType(TypeContactEnum.valueOf("MOBILE"));
        contactEntity.setContact("771234567");
        contactEntities = List.of(contactEntity);

        // Setup ContactRequest
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setId("contact1");
        contactRequest.setType(TypeContactEnum.valueOf("MOBILE"));
        contactRequest.setContact("771234567");
        contactRequests = List.of(contactRequest);

        // Setup PatientEntity
        patientEntity = new PatientEntity();
        patientEntity.setId("patient1");
        patientEntity.setNom("Diop");
        patientEntity.setPrenom("Fatou");
        patientEntity.setDateNaissance(LocalDate.of(1985, 5, 15));
        patientEntity.setSexe(SexeEnum.valueOf("FEMME"));
        patientEntity.setContacts(contactEntities);

        patientRequest = new PatientRequest();
        patientRequest.setNom("Diop");
        patientRequest.setPrenom("Fatou");
        patientRequest.setDateNaissance(LocalDate.of(1985, 5, 15));
        patientRequest.setSexe(SexeEnum.valueOf("FEMME"));
        patientRequest.setContacts(contactRequests);

        // Setup PatientResponse
        patientResponse = new PatientResponse();
        patientResponse.setId("patient1");
        patientResponse.setNom("Diop");
        patientResponse.setPrenom("Fatou");
        patientResponse.setDateNaissance(LocalDate.of(1985, 5, 15));
        patientResponse.setSexe(SexeEnum.valueOf("FEMME"));

        // Setup PatientWithContactResponse
        patientWithContactResponse = new PatientWithContactResponse();
        patientWithContactResponse.setId("patient1");
        patientWithContactResponse.setNom("Diop");
        patientWithContactResponse.setPrenom("Fatou");
        patientWithContactResponse.setDateNaissance(LocalDate.of(1985, 5, 15));
        patientWithContactResponse.setSexe(SexeEnum.valueOf("FEMME"));
        patientWithContactResponse.setContacts(contactResponses);
    }

    @Test
    void testSavePatient() {
        // Arrange
        when(contactService.saveContacts(any())).thenReturn(contactEntities);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        // Act
        PatientResponse result = patientService.savePatient(patientRequest);

        // Assert
        assertNotNull(result);
        assertEquals(patientEntity.getId(), result.getId());
        assertEquals(patientEntity.getNom(), result.getNom());
        assertEquals(patientEntity.getPrenom(), result.getPrenom());
        verify(contactService, times(1)).saveContacts(any());
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }

    @Test
    void testGetAllPatients() {
        // Arrange
        List<PatientEntity> patientEntities = List.of(patientEntity);
        when(patientRepository.findAll()).thenReturn(patientEntities);

        // Act
        List<PatientResponse> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(patientEntity.getId(), result.get(0).getId());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testGetPatientById_Success() {
        // Arrange
        when(patientRepository.findById("patient1")).thenReturn(Optional.of(patientEntity));

        // Act
        Optional<PatientWithContactResponse> result = patientService.getPatientById("patient1");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patientEntity.getId(), result.get().getId());
        assertEquals(patientEntity.getNom(), result.get().getNom());
        verify(patientRepository, times(1)).findById("patient1");
    }

    @Test
    void testGetPatientById_NotFound() {
        // Arrange
        when(patientRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.getPatientById("nonExistentId");
        });

        assertEquals("Le patient avec l'ID nonExistentId n'existe pas.", exception.getMessage());
        verify(patientRepository, times(1)).findById("nonExistentId");
    }

    @Test
    void testDeletePatient_Success() {
        // Arrange
        when(patientRepository.findById("patient1")).thenReturn(Optional.of(patientEntity));
        doNothing().when(contactService).deleteContacts(any());
        doNothing().when(patientRepository).deleteById(anyString());

        // Act
        boolean result = patientService.deletePatient("patient1");

        // Assert
        assertTrue(result);
        verify(patientRepository, times(1)).findById("patient1");
        verify(contactService, times(1)).deleteContacts(any());
        verify(patientRepository, times(1)).deleteById("patient1");
    }

    @Test
    void testDeletePatient_NotFound() {
        // Arrange
        when(patientRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act
        boolean result = patientService.deletePatient("nonExistentId");

        // Assert
        assertFalse(result);
        verify(patientRepository, times(1)).findById("nonExistentId");
        verify(contactService, never()).deleteContacts(any());
        verify(patientRepository, never()).deleteById(anyString());
    }

    @Test
    void testDeletePatient_RepositoryThrowsException() {
        // Arrange
        when(patientRepository.findById("patient1")).thenThrow(new EmptyResultDataAccessException(1));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.deletePatient("patient1");
        });

        assertEquals("Le patient avec l'ID patient1 n'existe pas.", exception.getMessage());
        verify(patientRepository, times(1)).findById("patient1");
    }

    @Test
    void testUpdatePatient_Success() {
        // Arrange
        when(patientRepository.findById("patient1")).thenReturn(Optional.of(patientEntity));
        when(contactService.saveContacts(any())).thenReturn(contactEntities);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        // Act
        Optional<PatientWithContactResponse> result = patientService.updatePatient("patient1", patientRequest);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(patientEntity.getId(), result.get().getId());
        assertEquals(patientRequest.getNom(), result.get().getNom());
        assertEquals(patientRequest.getPrenom(), result.get().getPrenom());
        verify(patientRepository, times(1)).findById("patient1");
        verify(contactService, times(1)).saveContacts(any());
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }

    @Test
    void testUpdatePatient_NotFound() {
        // Arrange
        when(patientRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.updatePatient("nonExistentId", patientRequest);
        });

        assertEquals("Le patient avec l'ID nonExistentId n'existe pas.", exception.getMessage());
        verify(patientRepository, times(1)).findById("nonExistentId");
        verify(contactService, never()).saveContacts(any());
        verify(patientRepository, never()).save(any(PatientEntity.class));
    }

    @Test
    void testAddOrUpdateContacts_Success_ExistingContact() {
        // Arrange
        when(patientRepository.findById("patient1")).thenReturn(Optional.of(patientEntity));
        when(contactService.saveContactsEntities(any())).thenReturn(contactEntities);
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patientEntity);

        // Act
        Optional<PatientWithContactResponse> result = patientService.addOrUpdateContacts("patient1", contactRequests);

        // Assert
        assertTrue(result.isPresent());
        verify(patientRepository, times(1)).findById("patient1");
        verify(contactService, times(1)).saveContactsEntities(any());
        verify(patientRepository, times(1)).save(any(PatientEntity.class));
    }

    @Test
    void testAddOrUpdateContacts_NotFound() {
        // Arrange
        when(patientRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.addOrUpdateContacts("nonExistentId", contactRequests);
        });

        assertEquals("Le patient avec l'ID nonExistentId n'existe pas.", exception.getMessage());
        verify(patientRepository, times(1)).findById("nonExistentId");
    }

    @Test
    void testDeleteContact_PatientNotFound() {
        // Arrange
        when(patientRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.deleteContact("nonExistentId", "contact1");
        });

        assertEquals("Le patient avec l'ID nonExistentId n'existe pas.", exception.getMessage());
        verify(patientRepository, times(1)).findById("nonExistentId");
    }


    @Test
    void testDeleteContact_NoContacts() {
        // Arrange
        PatientEntity patientWithoutContacts = new PatientEntity();
        patientWithoutContacts.setId("patient1");
        patientWithoutContacts.setNom("Diop");
        patientWithoutContacts.setPrenom("Fatou");
        patientWithoutContacts.setDateNaissance(LocalDate.of(1985, 5, 15));
        patientWithoutContacts.setSexe(SexeEnum.valueOf("FEMME"));
        patientWithoutContacts.setContacts(null);

        when(patientRepository.findById("patient1")).thenReturn(Optional.of(patientWithoutContacts));

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            patientService.deleteContact("patient1", "contact1");
        });

        assertEquals("Le patient avec l'ID patient1 n'a pas aucun contact.", exception.getMessage());
        verify(patientRepository, times(1)).findById("patient1");
    }
}
