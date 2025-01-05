package com.example.hms;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.example.hms.exception.AffiliatedWithExistsException;
import com.example.hms.exception.NoAffiliationsFoundException;
import com.example.hms.exception.PhysiciansNotFoundException;
import com.example.hms.model.AffiliatedWithId;
import com.example.hms.model.Affiliated_With;
import com.example.hms.model.Department;
import com.example.hms.model.Physician;
import com.example.hms.repository.Affiliated_With_Repository;
import com.example.hms.repository.DepartmentRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.serviceImpl.HMSAffiliatedWithServiceImpl;

public class HMSAffiliatedWithTest {
    @Mock
    private Affiliated_With_Repository affRepo;
    @Mock
    private PhysicianRepository phyRepo;
    @Mock
    private DepartmentRepository depRepo;
    @InjectMocks
    private HMSAffiliatedWithServiceImpl affiliatedWithService;
    private Affiliated_With affiliatedWith;
    private Physician physician;
    private Department department;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        // Initialize and save the physician and department
        physician = new Physician(1, "John Doe", "Cardiologist", 123456789);
        department = new Department(1, "Cardiology");
        
        // Save the physician and department to the repository
        when(phyRepo.save(any(Physician.class))).thenReturn(physician);
        when(depRepo.save(any(Department.class))).thenReturn(department);
        
        // Save the affiliated_with entity
        affiliatedWith = new Affiliated_With(new AffiliatedWithId(1, 1), physician, department, true);
        when(affRepo.existsById(affiliatedWith.getId())).thenReturn(false);
        when(affRepo.save(any(Affiliated_With.class))).thenReturn(affiliatedWith);
        
        // Save the department to the repository
        when(depRepo.findById(department.getId())).thenReturn(Optional.of(department));
    }
    @Test
    public void testCreateAffiliatedWith_Success() {
        when(affRepo.existsById(affiliatedWith.getId())).thenReturn(false);
        when(affRepo.save(any(Affiliated_With.class))).thenReturn(affiliatedWith);
        Affiliated_With result = affiliatedWithService.createAffiliatedWith(affiliatedWith);
        assertNotNull(result);
        assertEquals(affiliatedWith.getId(), result.getId());
    }

    @Test
    public void testCreateAffiliatedWith_Exists() {
        when(affRepo.existsById(affiliatedWith.getId())).thenReturn(true);
        assertThrows(AffiliatedWithExistsException.class, () -> {
            affiliatedWithService.createAffiliatedWith(affiliatedWith);
        });
    }

    @Test
    public void testGetPhysiciansByDeptId_Success() {
        when(affRepo.findByDepartment(department.getId())).thenReturn(List.of(physician.getEmployeeID()));
        when(phyRepo.findById(physician.getEmployeeID())).thenReturn(Optional.of(physician));
        List<Physician> result = affiliatedWithService.getPhysiciansByDeptId(department.getId());
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(physician.getEmployeeID(), result.get(0).getEmployeeID());
    }

    @Test
    public void testGetPhysiciansByDeptId_NotFound() {
        when(affRepo.findByDepartment(department.getId())).thenReturn(List.of());
        assertThrows(PhysiciansNotFoundException.class, () -> {
            affiliatedWithService.getPhysiciansByDeptId(department.getId());
        });
    }

    @Test
    public void testFindPhyCountByDeptIdSuccess() {
        when(affRepo.findPhyCountByDeptId(department.getId())).thenReturn(1);
        Integer count = affiliatedWithService.findPhyCountByDeptId(department.getId());
        assertEquals(1, count);
    }

    @Test
    public void testFindPrimaryAffByPhyId_Success() {
        when(affRepo.findByPhysician(physician.getEmployeeID())).thenReturn(List.of(affiliatedWith));
        boolean result = affiliatedWithService.findPrimaryAffByPhyId(physician.getEmployeeID());
        assertTrue(result);
    }

    @Test
    public void testFindPrimaryAffByPhyId_NoAffiliations() {
        when(affRepo.findByPhysician(physician.getEmployeeID())).thenReturn(List.of());
        assertThrows(NoAffiliationsFoundException.class, () -> {
            affiliatedWithService.findPrimaryAffByPhyId(physician.getEmployeeID());
        });
    }

    @Test
    public void testGetAllAffiliations_Success() {
        when(affRepo.findAll()).thenReturn(List.of(affiliatedWith));
        List<Affiliated_With> result = affiliatedWithService.getall();
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
