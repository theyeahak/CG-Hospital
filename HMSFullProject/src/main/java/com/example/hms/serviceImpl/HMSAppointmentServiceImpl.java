package com.example.hms.serviceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hms.exception.AlreadyFoundException;
import com.example.hms.exception.FieldsNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForId;
import com.example.hms.exception.NoNurseFoundByPatientIdException;
import com.example.hms.exception.NurseNotFoundException;
import com.example.hms.exception.PatientIdNotFoundException;
import com.example.hms.model.Nurse;
import com.example.hms.model.Physician;
import com.example.hms.exception.ExaminationRoomNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForPhysicianId;
import com.example.hms.exception.PatientNotFoundException;
import com.example.hms.exception.RoomNotFoundException;
import com.example.hms.model.Appointment;
import com.example.hms.model.Patient;
import com.example.hms.model.Room;
import com.example.hms.repository.AppointmentRepository;
import com.example.hms.repository.NurseRepository;
import com.example.hms.repository.PatientRepository;
import com.example.hms.repository.PhysicianRepository;
import com.example.hms.repository.RoomRepository;
import com.example.hms.service.HMSAppointmentService;


/**
 * Implementation of the HMSAppointmentService interface.
 * Provides methods to manage appointments, patients, nurses, physicians, and rooms in the hospital management system.
 */
@Service
public class HMSAppointmentServiceImpl implements HMSAppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(HMSAppointmentServiceImpl.class);

    @Autowired
    AppointmentRepository appRepo;

    @Autowired
    PatientRepository patRepo;

    @Autowired
    PhysicianRepository phyRepo;

    @Autowired
    RoomRepository roomRepo;

    @Autowired
    NurseRepository nurserepo;

    /**
     * Updates the examination room for a given appointment ID.
     * @param appointmentid ID of the appointment to update.
     * @param newExaminationRoom New examination room to set.
     * @return Updated Appointment object.
     */
    @Override
    public Appointment updateExaminationRoom(Integer appointmentid, String newExaminationRoom) {
        logger.info("Updating examination room for appointment ID: {}", appointmentid);
        Appointment a = appRepo.findById(appointmentid).orElse(null);
        if (a == null) {
            logger.error("Appointment ID {} not found", appointmentid);
            throw new ExaminationRoomNotFoundException("Appointment not found");
        }
        a.setExaminationRoom(newExaminationRoom);
        logger.info("Examination room updated to: {}", newExaminationRoom);
        return appRepo.save(a);
    }

    /**
     * Retrieves patients checked by a specific physician.
     * @param physicianid ID of the physician.
     * @return A set of patients checked by the physician.
     */
    @Override
    public HashSet<Patient> getPatientCheckedByPhysician(int physicianid) {
        logger.info("Retrieving patients checked by physician ID: {}", physicianid);
        HashSet<Patient> patSet = new HashSet<>();
        List<Appointment> applist = appRepo.findByPhysician(physicianid);
        if (applist.isEmpty()) {
            logger.error("No appointments found for physician ID: {}", physicianid);
            throw new NoAppointmentFoundForPhysicianId("No Appointment Found For PhysicianId " + physicianid);
        }
        for (Appointment a : applist) {
            Patient p = a.getPatient();
            int ssn = p.getSsn();
            patSet.add(patRepo.findById(ssn).get());
        }
        logger.info("Patients retrieved for physician ID: {}", physicianid);
        return patSet;
    }

    /**
     * Retrieves a patient checked by a specific physician using patient and physician IDs.
     * @param physicianid ID of the physician.
     * @param patientid ID of the patient.
     * @return The patient if found, otherwise null.
     */
    @Override
    public Patient getPatientByPatientIdAndPhysicianId(int physicianid, int patientid) {
        logger.info("Retrieving patient ID: {} checked by physician ID: {}", patientid, physicianid);
        for (Appointment a : appRepo.findByPhysician(physicianid)) {
            if (a.getPatient().getSsn() == patientid) {
                logger.info("Patient ID: {} found for physician ID: {}", patientid, physicianid);
                return a.getPatient();
            }
        }
        logger.warn("Patient ID: {} not found for physician ID: {}", patientid, physicianid);
        return null;
    }

    /**
     * Retrieves rooms used by a physician on a specific date.
     * @param physicianid ID of the physician.
     * @param date Date for which rooms are to be retrieved.
     * @return A list of rooms.
     */
    @Override
    public List<Room> getRoomByPhysicianIdOnDate(int physicianid, LocalDateTime date) {
        logger.info("Retrieving rooms for physician ID: {} on date: {}", physicianid, date);
        return roomRepo.getRoomByPhyIdOnDate(physicianid, date);
    }

    /**
     * Retrieves appointment dates for a specific patient.
     * @param patientId ID of the patient.
     * @return A list of appointment dates.
     */

    @Override
    public List<LocalDateTime> getAppointmentDatesByPatientId(int patientId){
    	logger.info("Retrieving appointment dates for patient ID: {}",patientId);
    	List<Appointment> appointments = appRepo.findByPatientSsn(patientId);
		return appointments.stream().map(Appointment::getStart).collect(Collectors.toList());
    	
    }
    
    

    /**
     * Retrieves the examination room for a patient on a specific date.
     * @param patientId ID of the patient.
     * @param date Date of the appointment.
     * @return Examination room as a string.
     */
    @Override
    public String getExaminationRoomByPatientIdAndDate(int patientId, LocalDate date) {
        logger.info("Retrieving examination room for patient ID: {} on date: {}", patientId, date);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        Optional<Appointment> appointment = appRepo.findByPatientSsnAndStartBetween(patientId, startOfDay, endOfDay);
        return appointment.map(Appointment::getExaminationRoom).orElse("No appointment found for the given date.");
    }

    /**
     * Retrieves patients attended by a specific nurse.
     * @param nurseid ID of the nurse.
     * @return A set of patients attended by the nurse.
     */
    @Override
    public HashSet<Patient> getPateientsByNusreId(int nurseid) {
        logger.info("Retrieving patients attended by nurse ID: {}", nurseid);
        List<Appointment> appList = appRepo.findByPrepNurse(nurseid);
        HashSet<Patient> patientList = new HashSet<>();
        for (Appointment app : appList) {
            patientList.add(app.getPatient());
        }

        if (patientList.isEmpty()) {
            logger.error("No patients found for nurse ID: {}", nurseid);
            throw new PatientNotFoundException("Patients not found");
        }
        logger.info("Patients retrieved for nurse ID: {}", nurseid);
        return patientList;
    }

    /**
     * Retrieves a patient attended by a specific nurse using nurse and patient IDs.
     * @param nurseid ID of the nurse.
     * @param patientid ID of the patient.
     * @return The patient if found.
     */
    @Override
    public Patient getPatientByPatientIdAndNurseId(int nurseid, int patientid) {
        logger.info("Retrieving patient ID: {} attended by nurse ID: {}", patientid, nurseid);
        for (Appointment a : appRepo.findByPrepNurse(nurseid)) {
            if (a.getPatient().getSsn() == patientid) {
                logger.info("Patient ID: {} found for nurse ID: {}", patientid, nurseid);
                return a.getPatient();
            }
        }
        logger.error("Patient ID: {} not found for nurse ID: {}", patientid, nurseid);
        throw new PatientNotFoundException("Patient with ID " + patientid + " not found for nurse with ID " + nurseid);
    }

    /**
     * Retrieves rooms used by a nurse on a specific date.
     * @param nurseId ID of the nurse.
     * @param date Date for which rooms are to be retrieved.
     * @return A list of rooms.
     */
    @Override
    public List<Room> getRoomList(int nurseId, LocalDateTime date) {
        logger.info("Retrieving rooms for nurse ID: {} on date: {}", nurseId, date);
        List<Room> li = roomRepo.getRoomByNurseIdOnDate(nurseId, date);
        if (li.isEmpty()) {
            logger.error("No rooms found for nurse ID: {} on date: {}", nurseId, date);
            throw new RoomNotFoundException("Rooms not found with this details: " + nurseId);
        }
        return li;
    }

    /**
     * Retrieves patients checked by a physician on a specific date.
     * @param physicianid ID of the physician.
     * @param date Date for which patients are to be retrieved.
     * @return A set of patients checked by the physician on the given date.
     */
    @Override
    public HashSet<Patient> getPatientCheckedByPhysicianOnDate(int physicianid, LocalDate date) {
        logger.info("Retrieving patients checked by physician ID: {} on date: {}", physicianid, date);
        HashSet<Patient> patSet = new HashSet<>();
        for (Appointment a : appRepo.findByPhysician(physicianid)) {
            LocalDate d = a.getStart().toLocalDate();
            if (date.equals(d)) {
                Patient p = a.getPatient();
                int ssn = p.getSsn();
                patSet.add(patRepo.findById(ssn).get());
            }
        }
        logger.info("Patients retrieved for physician ID: {} on date: {}", physicianid, date);
        return patSet;
    }

    /**
     * Retrieves patients attended by a nurse on a specific date.
     * @param nurseid ID of the nurse.
     * @param starto Date for which patients are to be retrieved.
     * @return A set of patients attended by the nurse on the given date.
     */
    @Override
    public HashSet<Patient> getPatientsBynurseandDate(int nurseid, LocalDate starto) {
        logger.info("Retrieving patients attended by nurse ID: {} on date: {}", nurseid, starto);
        List<Appointment> appList = appRepo.findByPrepNurse(nurseid);
        HashSet<Patient> patientList = new HashSet<>();

        for (Appointment app : appList) {
            LocalDate d = app.getStart().toLocalDate();
            if (starto.equals(d)) {
                Patient p = app.getPatient();
                int ssn = p.getSsn();
                patientList.add(patRepo.findById(ssn).get());
            }
        }
        if (patientList.isEmpty()) {
            logger.error("No patients found for nurse ID: {} on date: {}", nurseid, starto);
            throw new PatientNotFoundException("Patients not found");
        }
        logger.info("Patients retrieved for nurse ID: {} on date: {}", nurseid, starto);
        return patientList;
    }
    
 // ranjjan
 	/**
 	 * retrieves physicians associated with a particular patient by their patient
 	 * ID. gives and handles cases where the patient ID is not found.
 	 *
 	 * @param patientId ID of the patient.
 	 * 
 	 * @return List of physicians assigned to the patient.
 	 * 
 	 * @throws PatientIdNotFoundException if the patient ID is not found.
 	 */
 	@Override
 	public List<Physician> getPhysiciansByPatientId(int patientId) {

 		logger.info("getting physicians for patient ID: {}", patientId);
 		Patient patient = patRepo.findById(patientId).orElse(null);
 		if (patient == null) {
 			logger.error("Patient with ID {} not found", patientId);
 			throw new PatientIdNotFoundException(patientId + " Patient not found");
 		}
 		// Assuming a Patient is associated with multiple physicians via appointments
 		List<Physician> physicians = patient.getAppointments().stream().map(appointment -> appointment.getPhysician())
 				.distinct().collect(Collectors.toList());
 		return physicians;
 	}

 	// ranjjan
 	/**
 	 * retrieves all appointments in the system. gives the number of appointments
 	 * retrieved.
 	 * 
 	 * @return List of all appointments.
 	 */
 	@Override
 	public List<Appointment> getAllAppointments() {
 		logger.info("Fetching all appointments");
 		List<Appointment> a = appRepo.findAll();
 		logger.info("Total appointments retrieved: {}", a.size());
 		return a;
 	}

 	// ranjjan
 	/**
 	 * Posts a new appointment to the system after validation. Logs the appointment
 	 * ID and handles error cases.
 	 */
 	
 	@Override
 	public void postApp(Appointment a) throws AlreadyFoundException, FieldsNotFoundException{
 		logger.info("Posting a new appointment with ID: {}", a.getAppointmentID());
  
 		int id = a.getAppointmentID();
 		if (appRepo.existsById(id)) {
 			logger.error("appointment with ID {} already exists", id);
 			throw new AlreadyFoundException("Appointment already found by this ID");
 		}
  
 		if (a.getPhysician() == null || a.getPatient() == null || a.getPrepNurse() == null) {
 			logger.error("req mandatory fields are missing for appointment ID: {}", id);
 			throw new FieldsNotFoundException("All details must be assigned properly");
 		}
  
 		appRepo.save(a);
 		logger.info("Appointment with ID {} successfully posted", id);
 	}
 	
 	// ranjjan
 	/**
 	 * retrieves appointments by the specified start date. gives the number of
 	 * appointments found for the given start date.
 	 *
 	 * @param startDate The start date of the appointments.
 	 * @return List of appointments with the specified start date.
 	 */
 	@Override
 	public List<Appointment> getAppointmentsByStartDate(LocalDateTime startDate) {
 		logger.info("getting appointments for start date: {}", startDate);
 		List<Appointment> appointments = appRepo.findByStart(startDate);
 		logger.info("found {} appointments for start date: {}", appointments.size(), startDate);
 		return appointments;
 	}

 	// ranjjan
 	/**
 	 * Retrieves the patient associated with a given appointment ID. gives the ID
 	 * and handles cases where the appointment is not found.
 	 * 
 	 * @param appointmentid The appointment ID.
 	 * @return The patient associated with the appointment.
 	 * @throws NoAppointmentFoundForId if the appointment ID is not found.
 	 */
 	@Override
 	public Patient getPatientByAppointment(int appointmentid) throws NoAppointmentFoundForId{
 		logger.info("getting patient for appointment ID: {}", appointmentid);
 		Appointment a = appRepo.findById(appointmentid).orElse(null);
 		if (a == null) {
 			logger.error("No appointment found with ID: {}", appointmentid);
 			throw new NoAppointmentFoundForId(appointmentid + " Not Found!");
 		}
 		Integer ssn = a.getPatient().getSsn();

 		return patRepo.findById(ssn).orElse(null);
 	}

 	// ranjjan
 	/**
 	 * retrieves the physician associated with a given appointment ID. gives the
 	 * physician retrieval process.
 	 * 
 	 * @param appointmentid The appointment ID.
 	 * @return The physician associated with the appointment.
 	 */
 	@Override
 	public Physician getPhysicianByAppointment(int appointmentid) {
 		logger.info("getting physician for appointment ID: {}", appointmentid);
 		Integer empId = appRepo.findById(appointmentid).orElse(null).getPhysician().getEmployeeID();
 		return phyRepo.findById(empId).orElse(null);
 	}

 	// ranjjan
 	/**
 	 * Retrieves the nurse associated with a given appointment ID.
 	 */
 	@Override
 	public Nurse getNurseByAppointmentId(Integer appointmentId) throws  NurseNotFoundException{
 		logger.info("getting nurse for appointment ID: {}", appointmentId);
 		Nurse n = nurserepo.getNurseByappId(appointmentId);
 		if (n == null) {
 			logger.error("No nurse found for appointment ID: {}", appointmentId);
 			throw new NurseNotFoundException("Nurse  not found");
 		}
 		logger.info("nurse found for appointment ID: {}", appointmentId);
 		return n;

 	}

 	// ranjjan
 	/**
 	 * Retrieves the examination room associated with a given appointment ID.
 	 */
 	@Override
 	public String getExamintionRoomByappId(int id) throws ExaminationRoomNotFoundException {
 		logger.info("getting examination room for appointment ID: {}", id);
 		String s = appRepo.getExaminationRoomBYappID(id);

 		if (s == null) {
 			logger.error("no examination room found for appointment ID: {}", id);
 			throw new ExaminationRoomNotFoundException("No examinationroom found for this id: " + id);

 		}
 		logger.info("Examination room found for appointment ID: {}", id);
 		return appRepo.getExaminationRoomBYappID(id);

 	}

 	// ranjjan
 	/**
 	 * @param patientId The patient ID.
 	 * @param date      ---> date to search for physicians.
 	 * @return List of physicians assigned to the patient on the specified date.
 	 */
 	@Override
 	public List<Physician> getPhysicianByPatientIdAndDate(String patientId, LocalDateTime date) {
 		logger.info("getting physicians for patient ID: {} on date: {}", patientId, date);
 		List<Appointment> appointments = appRepo.findByPatientIdAndDate(patientId, date);

 		List<Physician> physicians = appointments.stream().map(Appointment::getPhysician)
 				.filter(physician -> physician != null) // Handle null physicians
 				.distinct().collect(Collectors.toList());
 		logger.info("Found {} physicians for patient ID: {} on date: {}", physicians.size(), patientId, date);

 		return physicians;
 	}

 	// ranjjan
 	/**
 	 * gets us the list of nurses for the given patientID and in the particular date
 	 * 
 	 */
 	@Override
 	public List<Nurse> getNursesByPatientIdAndDate(String patientId, LocalDateTime date) {
 		logger.info("getting nurses for patient ID: {} on date: {}", patientId, date);
 		List<Nurse> nurses = appRepo.findNursesByPatientIdAndDate(patientId, date);

 		if (nurses.isEmpty()) {
 			logger.warn("No nurses found for patient ID: {} on date: {}", patientId, date);
 		} else {
 			logger.info("Found {} nurses for patient ID: {} on date: {}", nurses.size(), patientId, date);
 		}

 		return nurses;
 	}

 	// ranjjan
 	/**
 	 * now getting list of nurses using just the patientid *
 	 */
 	@Override
 	public List<Nurse> getNursesByPatientId(int patientId)throws NoNurseFoundByPatientIdException {
 		logger.info("Fetching nurses for patient ID: {}", patientId);
 		List<Nurse> nurses = appRepo.findNursesByPatientId(patientId);

 		if (nurses.isEmpty()) {
 			logger.error("No nurses found for patient ID: {}", patientId);
 			throw new NoNurseFoundByPatientIdException(patientId + " Nurse not found for this patient id");
 		}

 		logger.info("Found {} nurses for patient ID: {}", nurses.size(), patientId);
 		return nurses;
 	}

}

