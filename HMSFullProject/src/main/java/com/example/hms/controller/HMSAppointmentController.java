package com.example.hms.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.hms.exception.ExaminationRoomNotFoundException;
import com.example.hms.exception.FieldsNotFoundException;
import com.example.hms.exception.NoAppointmentFoundForId;
import com.example.hms.exception.NoAppointmentFoundForPhysicianId;
import com.example.hms.exception.NurseNotFoundException;
import com.example.hms.exception.PatientIdNotFoundException;
import com.example.hms.model.Nurse;
import com.example.hms.model.Physician;
import com.example.hms.model.Appointment;
import com.example.hms.model.Patient;
import com.example.hms.model.Room;
import com.example.hms.service.HMSAppointmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Transactional
public class HMSAppointmentController {

    @Autowired
    HMSAppointmentService appService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final Logger logger = LoggerFactory.getLogger(HMSAppointmentController.class);

    /** 1
     * Fetch all appointment dates for a given patient.
     * @param patientId - ID of the patient whose appointment dates are being fetched.
     * @return ResponseEntity containing the list of appointment dates.
     */
    @GetMapping("/api/appointment/date/{patientid}")
    public ResponseEntity<List<LocalDateTime>> getAppointmentDates(@PathVariable("patientid") int patientId){
    	logger.info("Fetching appointment dates for patient ID{}",patientId);
    	List<LocalDateTime> appointmentDates = appService.getAppointmentDatesByPatientId(patientId);
    	if(appointmentDates.isEmpty()) {
    		logger.warn("No appointment dates found for patient ID:{}",patientId);
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	else {
    		logger.info("Successfully fetched appointmentdates for patient ID: {}",patientId);
    		return new ResponseEntity<>(appointmentDates, HttpStatus.OK);
    	}
    }
    
    
    
    

    /** 2
     * Get a list of patients checked by a particular physician.
     * @param physicianid - ID of the physician whose patients are being fetched.
     * @return ResponseEntity containing a set of patients checked by the physician.
     */
    @GetMapping("api/appointment/patient/{physicianid}")
    public ResponseEntity<HashSet<Patient>> getListOfPatientCheckedByPhysician(@PathVariable("physicianid") int physicianid) {
        logger.info("Fetching patients checked by physician ID: {}", physicianid);
        HashSet<Patient> patients = appService.getPatientCheckedByPhysician(physicianid);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /** 3
     * Get a list of patients checked by a physician on a particular date.
     * @param physicianid - ID of the physician.
     * @param date - Date on which patients were checked.
     * @return ResponseEntity containing a set of patients checked by the physician on the specified date.
     */
    @GetMapping("api/appointment/patient/physicianid/{physicianid}/date/{date}")
    public ResponseEntity<HashSet<Patient>> getListOfPatientCheckedPhysicianOnDate(@PathVariable("physicianid") int physicianid, @PathVariable("date") LocalDate date) {
        logger.info("Fetching patients checked by physician ID: {} on date: {}", physicianid, date);
        HashSet<Patient> patients = appService.getPatientCheckedByPhysicianOnDate(physicianid, date);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /** 4
     * Get patient details based on physician ID and appointment ID.
     * @param physicianid - ID of the physician.
     * @param patientid - ID of the patient.
     * @return ResponseEntity containing the patient details.
     */
    @GetMapping("api/appointment/physician/{physicianid}/patient/{patientid}")
    public ResponseEntity<Patient> getPatientByPhysicianIdAndAppointmentId(@PathVariable("physicianid") int physicianid, @PathVariable("patientid") int patientid) {
        logger.info("Fetching patient with patient ID: {} and physician ID: {}", patientid, physicianid);
        Patient patient = appService.getPatientByPatientIdAndPhysicianId(physicianid, patientid);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /** 5
     * Get a list of patients checked by a particular nurse.
     * @param nurseid - ID of the nurse.
     * @return ResponseEntity containing the set of patients checked by the nurse.
     */
    @GetMapping("api/appointment/patient/nurseid/{nurseid}")
    public ResponseEntity<HashSet<Patient>> getPatientsByNurse(@PathVariable("nurseid") Integer nurseid) {
        logger.info("Fetching patients checked by nurse ID: {}", nurseid);
        HashSet<Patient> patients = appService.getPateientsByNusreId(nurseid);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /** 6
     * Get patient details based on nurse ID and patient ID.
     * @param nurseid - ID of the nurse.
     * @param patientid - ID of the patient.
     * @return ResponseEntity containing the patient details.
     */
    @GetMapping("api/appointment/patient/{nurseid}/{patientid}")
    public ResponseEntity<Patient> getPatientByNurseIdAndAppointmentID(@PathVariable("nurseid") int nurseid, @PathVariable("patientid") int patientid) {
        logger.info("Fetching patient with patient ID: {} and nurse ID: {}", patientid, nurseid);
        Patient patient = appService.getPatientByPatientIdAndNurseId(nurseid, patientid);
        return new ResponseEntity<>(patient, HttpStatus.OK);
    }

    /** 7
     * Get all patients checked by a nurse on a particular date.
     * @param nurseid - ID of the nurse.
     * @param date - Date on which patients were checked.
     * @return ResponseEntity containing the set of patients checked by the nurse.
     */
    @RequestMapping(value="api/appointment/patient/nurseid/{nurseid}/date/{date}", method=RequestMethod.GET)
    public ResponseEntity<HashSet<Patient>> getAllPatientsByNurseAndDate(@PathVariable("nurseid") int nurseid, @PathVariable("date") LocalDate date) {
        logger.info("Fetching patients checked by nurse ID: {} on date: {}", nurseid, date);
        HashSet<Patient> patients = appService.getPatientsBynurseandDate(nurseid, date);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /** 8
     * Get examination room details by patient ID and date.
     * @param patientId - ID of the patient.
     * @param date - Date of the examination.
     * @return ResponseEntity containing the room information.
     */
    @GetMapping("api/appointment/room/patientid/{patientid}/date/{date}")
    public ResponseEntity<String> getExaminationRoom(@PathVariable("patientid") int patientId, @PathVariable("date") String date) {
        logger.info("Fetching examination room for patient ID: {} on date: {}", patientId, date);
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
            String room = appService.getExaminationRoomByPatientIdAndDate(patientId, localDateTime.toLocalDate());
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format for patient ID: {}: {}", patientId, e.getMessage());
            return new ResponseEntity<>("Invalid date format: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error fetching examination room for patient ID: {}: {}", patientId, e.getMessage());
            return new ResponseEntity<>("Error fetching data: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 9
     * Get room details by physician ID and date.
     * @param physicianid - ID of the physician.
     * @param date - Date on which the physician works.
     * @return ResponseEntity containing a list of rooms.
     */
    @GetMapping("api/appointment/room/physician/{physicianid}/{date}")
    public ResponseEntity<List<Room>> getRoomByPhysicianIdOnDate(@PathVariable("physicianid") int physicianid, @PathVariable("date") LocalDateTime date) {
        logger.info("Fetching room details for physician ID: {} on date: {}", physicianid, date);
        List<Room> rooms = appService.getRoomByPhysicianIdOnDate(physicianid, date);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    /** 10
     * Get room details by nurse ID and date.
     * @param nurseid - ID of the nurse.
     * @param date - Date on which the nurse works.
     * @return ResponseEntity containing a list of rooms.
     */
    @GetMapping("api/appointment/room/nurseid/{nurseid}/{date}")
    public ResponseEntity<List<Room>> getAllRoomDetails(@PathVariable("nurseid") int nurseid, @PathVariable("date") LocalDateTime date) {
        logger.info("Fetching room details for nurse ID: {} on date: {}", nurseid, date);
        List<Room> rooms = appService.getRoomList(nurseid, date);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    /** 11
     * Update the examination room by appointment ID.
     * @param appointmentid - ID of the appointment.
     * @param reqBody1 - Request body containing new examination room details.
     * @return ResponseEntity containing the updated appointment.
     */
    @PutMapping("api/appointment/room/{appointmentid}")
    public ResponseEntity<Appointment> updateRoomByAppointmentId(@PathVariable("appointmentid") Integer appointmentid, @RequestBody Map<String, String> reqBody1) {
        String newExaminationRoom = reqBody1.get("newExaminationRoom");
        logger.info("Updating examination room for appointment ID: {} to {}", appointmentid, newExaminationRoom);
        Appointment updatedAppointment = appService.updateExaminationRoom(appointmentid, newExaminationRoom);
        return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
    }
    
    //ranjjan
    /**
	 * Retrieves all appointments.
	 * 
	 * @return ResponseEntity containing the list of all appointments.
	 */

	@GetMapping("/api/appointment")
	public ResponseEntity<List<Appointment>> getAllAppointments() {
		logger.info("fetching all appointments :) ");
		List<Appointment> li = appService.getAllAppointments();
		
		logger.info("tot appointments fetched :) {}", li.size());

		ResponseEntity<List<Appointment>> response = new ResponseEntity<>(li, HttpStatus.OK);
		return response;
	}

	// Add a appointment detail to DB
	// ranjjan
//	 @PostMapping("api/appointment/")
//	 public ResponseEntity<String> postAppointment(@RequestBody Appointment a) {
//	     logger.info("adding a new appointment :) {}", a);
//	     appService.postApp(a);
//	     logger.info("Appointment added successfully with details :) {}", a);
//	      ResponseEntity<String> str=  new ResponseEntity<String>("Record Created Successfully", HttpStatus.CREATED);
//	      return str;
//	 }
//	 

	// Add a appointment detail to DB
	// ranjjan

	/**
	 * posting a new appointment details, used the logger for info and error using
	 * the try catch dynamic response body accordingly
	 * 
	 * @param a Appointment details
	 * @return ResponseEntity with the status of the operation
	 * 
	 */	
	
	
	@PostMapping("api/appointment/")
	public ResponseEntity<String> postAppointment(@RequestBody Appointment a) {
		logger.info("Adding a new appointment :) {}", a);
		ResponseEntity<String> response;
		try {
			appService.postApp(a);
			logger.info("Appointment added successfully with details: {}", a);
			response = ResponseEntity.status(HttpStatus.CREATED).body("Record has created successfully!");
		} catch (FieldsNotFoundException e) {
			logger.error("Error adding appointment: {}", e.getMessage());
			response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			logger.error("Error adding appointment: {}", e.getMessage());
			response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating record");
		}
		return response;
	}
	

	

	/**
	 * retrieve all appointments provided we give the startdate as a path variable
	 * in our api tool multiple logger methods has been added try catch methods for
	 * relevant return statements
	 * 
	 * @param startdate Start date in ISO format
	 * @return ResponseEntity containing the list of appointments or an error
	 *         message.
	 * 
	 */

	// ranjjan
	@GetMapping("/api/appointment/{startdate}")
	public ResponseEntity<List<Appointment>> getAppointmentByStartDate(@PathVariable("startdate") String startdate) {
		// Parse the startdate string to LocalDateTime
		LocalDateTime startDate;
		ResponseEntity<List<Appointment>> response;
		try {
			startDate = LocalDateTime.parse(startdate, formatter);
		} catch (Exception e) {
			logger.error("Error parsing startdate :( {}", e.getMessage());
			response = ResponseEntity.badRequest().body(null);
			return response;
		}
		List<Appointment> li = appService.getAppointmentsByStartDate(startDate);
		logger.info("Your list of appointments for the given date has been successfully returned! Total: {}",
				li.size());
		response = new ResponseEntity<>(li, HttpStatus.OK);
		return response;
	}

	/**
	 * retrieving patient from the appointment using the appointment id appointment
	 * id as the path variable in our api, relevant logger has been added for the
	 * sake of the users *
	 * 
	 */

	// ranjjan
	@GetMapping("api/appointment/patient/appointmentid/{appointmentid}")
	public ResponseEntity<Patient> getPatientByAppointment(@PathVariable("appointmentid") int appointmentid) {
		logger.info("Fetching patient for the given appointment ID :)", appointmentid);
		Patient p = appService.getPatientByAppointment(appointmentid);
		if (p == null) {
			throw new NoAppointmentFoundForId("No appointment found with ID: " + appointmentid);
		}
		logger.info("Patient found :) ", p);
		ResponseEntity<Patient> response = new ResponseEntity<>(p, HttpStatus.OK);
		return response;
	}

	// ranjjan

	/**
	 * retrieving physician from the appointment using the appointment id
	 * appointment id as the path variable in our api, logger interface has been
	 * added
	 */

	@GetMapping("api/appointment/physician/appointmentid/{appointmentid}")
	public ResponseEntity<Physician> getPhysicianByAppointment(@PathVariable("appointmentid") int appointmentid) {
		logger.info("Fetching Physician for the given appointment ID :)", appointmentid);
		Physician p = appService.getPhysicianByAppointment(appointmentid);
		if (p == null) {
			throw new NoAppointmentFoundForId("No appointment found with ID: " + appointmentid);
		}
		logger.info("Successfully retrieved your physician data :) ", p);
		ResponseEntity<Physician> response = new ResponseEntity<>(p, HttpStatus.OK);
		return response;
	}

	// ranjjan
	/**
	 * retrieving the nurse data from the appointment using the appointmentid
	 * appointmentid as the path variable loggers are declared
	 * 
	 */
	// Search a nurse detail by appointment id
	@RequestMapping(value = "api/appointment/nurse/appointmentid/{appointmentid}", method = RequestMethod.GET)
	public ResponseEntity<Nurse> getNurseByAppointmentId(@PathVariable("appointmentid") Integer appointmentid) {
		logger.info("getting you your nurse data for the given appointment id :)", appointmentid);
		Nurse nurse = appService.getNurseByAppointmentId(appointmentid);
		if (nurse == null) {
			throw new NurseNotFoundException("No nurse found for appointment ID: " + appointmentid);
		}
		logger.info("successfully retrieved your date :) ", nurse);
		ResponseEntity<Nurse> response = new ResponseEntity<Nurse>(nurse, HttpStatus.OK);
		return response;

	}

	// given exception
	// ranjjan

	/**
	 * retrieving the examination room(a string value) with the appointment id as a
	 * path variable
	 * 
	 * 
	 */

	// Search a examination room detail by appointment id
	@RequestMapping(value = "api/appointment/examinationroom/{appointmentid}", method = RequestMethod.GET)
	public ResponseEntity<String> getExaminationRoom(@PathVariable("appointmentid") int appointmentid) {
		logger.info("preparing the relevant examination-room with the provided  appointmentID-->", appointmentid);
		String room = appService.getExamintionRoomByappId(appointmentid);
		if (room == null) {
			throw new ExaminationRoomNotFoundException(
					"No examination room found for appointment ID: " + appointmentid);
		}

		logger.info("successfully retrieved your Examination Room :) ---", room);
		ResponseEntity<String> response = new ResponseEntity<String>(room, HttpStatus.OK);
		return response;

	}

	// ranjjan
	//
	/**
	 * list of physicians are being retrieved here with patientid as the
	 * pathvariable logger is added here as well
	 * 
	 */

	@GetMapping("api/appointment/physician/{patientid}")
	public ResponseEntity<List<Physician>> getPhysiciansByPatientId(@PathVariable("patientid") int patientId) {
		logger.info("kindly wait while we prepare the list of assigned physicians for your patientid :)---", patientId);
		List<Physician> physicians = appService.getPhysiciansByPatientId(patientId);
		if (physicians.isEmpty()) {
			throw new PatientIdNotFoundException("No physicians found for patient ID: " + patientId);
		}
		logger.info("thank you for waiting! here's your list of assigned Physicians! total of-->", physicians.size());
		ResponseEntity<List<Physician>> li = new ResponseEntity<>(physicians, HttpStatus.OK);
		return li;

	}

	// ranjjan

	/**
	 * retrieving the list of physicians with the help of the patientid and date
	 * getting the physicians assigned for the respective patient at a particular
	 * date loggers and responseentity has been added for the user convenience
	 * 
	 */

	@GetMapping("api/appointment/physician/{patientId}/{date}")
	public ResponseEntity<List<Physician>> getPhysicianByPatientIdAndDate(@PathVariable("patientId") String patientId,
			@PathVariable("date") String date) {

		// Define the DateTimeFormatter to match the date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate;
		ResponseEntity<List<Physician>> response;

		try {
			// Parse the date string to LocalDateTime
			localDate = LocalDateTime.parse(date, formatter);
		} catch (Exception e) {
			// Return bad request if date parsing fails
			logger.error("Error parsing date :( {}", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return response;
		}
		// Fetch physicians from the service
		List<Physician> physicians = appService.getPhysicianByPatientIdAndDate(patientId, localDate);
		// Return appropriate response based on the result
		if (physicians.isEmpty()) {
			throw new NoAppointmentFoundForPhysicianId(
					"No physicians found for patient ID: " + patientId + " on date: " + localDate);
		}
		logger.info("yay! Physicians found for patientId: {} on date: {}. Total: {}", patientId, localDate,
				physicians.size());
		response = new ResponseEntity<>(physicians, HttpStatus.OK);
		return response;
	}

	// ranjjan

	/**
	 * retrieving the list of nurses for the provided patient id the patientid is
	 * given as the path variable to determine the assigned nurse loggers and
	 * responseEntity is present for the UI
	 * 
	 */

	@GetMapping("api/appointment/nurse/{patientid}")
	public ResponseEntity<List<Nurse>> getNursesByPatientId(@PathVariable("patientid") int patientId) {
		logger.info("fetching the list of nurses for the patientID: {}", patientId);
		List<Nurse> nurses = appService.getNursesByPatientId(patientId);
		ResponseEntity<List<Nurse>> response;
		if (nurses.isEmpty()) {
			logger.info("oops! there are no nurses assigned for the provided patientId: {}", patientId);
			throw new NurseNotFoundException("No nurses found for patient ID: " + patientId);
		}

		logger.info("YAY! Here  is/are the nurses assigned for the provided patientId: {}. Total: {}", patientId,
				nurses.size());
		response = new ResponseEntity<>(nurses, HttpStatus.OK);
		return response;
	}

	// ranjjan
	/**
	 * 
	 * retrieving the list of nurses with the patientid and date
	 * 
	 * both of these are given as the path variable
	 * 
	 * try catch blocks are used accordingly along with loggers and the
	 * responseentity
	 * 
	 * 
	 */

	@GetMapping("api/appointment/nurse/{patientId}/{date}")
	public ResponseEntity<List<Nurse>> getNursesByPatientIdAndDate(@PathVariable("patientId") String patientId,
			@PathVariable("date") String date) {
		// Define the DateTimeFormatter to match the date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate;
		ResponseEntity<List<Nurse>> response;

		try {
			// Parse the date string to LocalDateTime
			localDate = LocalDateTime.parse(date, formatter);
		} catch (Exception e) {
			// Return bad request if date parsing fails
			logger.error("Oops! unable to parse the date ", e.getMessage());
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			return response;
		}

		// Fetch nurses from the service
		List<Nurse> nurses = appService.getNursesByPatientIdAndDate(patientId, localDate);
		// Return appropriate response based on the result
		if (nurses.isEmpty()) {
			throw new NurseNotFoundException("No nurses found for patient ID: " + patientId + " on date: " + localDate);
		}
		logger.info("YAY! Here  is/are the nurses assigned for the provided patientId: {}. Total: {}. Date: {}",
				patientId, nurses.size(), localDate);
		response = new ResponseEntity<>(nurses, HttpStatus.OK);

		return response;

	}

}


