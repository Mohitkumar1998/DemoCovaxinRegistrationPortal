package com.wipro.covaxin.service;

import com.wipro.covaxin.entity.Applicant;
import com.wipro.covaxin.exceptionhandling.DuplicateResourceException;
import com.wipro.covaxin.exceptionhandling.ResourceNotFoundException;
import com.wipro.covaxin.repository.AadharDetailsRepository;
import com.wipro.covaxin.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Autowired
    private ApplicantRepository applicantRepo;

    @Autowired
    private AadharDetailsRepository aadharDetailsRepo;

    @Override
    public List<Applicant> getAllApplicants() throws ResourceNotFoundException {
        if(applicantRepo.findAll().size()==0){
            throw new ResourceNotFoundException("No applicants registered for vaccination");
        }
        return applicantRepo.findAll();
    }

    @Override
    public Applicant getApplicantById(long id) throws ResourceNotFoundException {
        if(!applicantRepo.existsById(id)){
            throw new ResourceNotFoundException("Applicant with id "+id+" is not registered");
        }
        return applicantRepo.findById(id).get();
    }

    @Override
    public ResponseEntity<Object> createApplicant(Applicant newApplicant) throws DuplicateResourceException {
        if(aadharDetailsRepo.existsById(newApplicant.getAadharDetails().getAadharId())){
            throw new DuplicateResourceException("Applicant with Aadhar Id "+newApplicant.getAadharDetails().getAadharId()+" already registered");
        }
        Date advanceDate = newApplicant.getAadharDetails().getDob();
        Calendar thisDateCalender = Calendar.getInstance();
        thisDateCalender.setTime(advanceDate);
        thisDateCalender.add(Calendar.YEAR,45);
        advanceDate=thisDateCalender.getTime();
        Date currentdate = new Date();
        if((currentdate.compareTo(advanceDate)>0)){
            if(newApplicant.getVaccinationTimeSlot().compareTo(currentdate)<0){
                return ResponseEntity.accepted().body("Applicant not registered, vaccination time slot is past current date");
            }
            applicantRepo.save(newApplicant);
            return new ResponseEntity<>("Applicant registered", HttpStatus.CREATED);
        }else{
            return ResponseEntity.accepted().body("Applicant not registered, age is less than 45");
        }
    }

    @Override
    public ResponseEntity<Object> updateApplicant(Applicant applicant, long id) throws ResourceNotFoundException {
        if(!applicantRepo.existsById(id)){
            throw new ResourceNotFoundException("Applicant with id "+id+" is not registered");
        }
        Applicant thisApplicant = applicantRepo.findById(id).get();
        if(applicant.getAadharDetails()!=null){
            return ResponseEntity.accepted().body("Update unsuccessful, aadhar details can not be updated");
        }
        applicant.setApplicantId(thisApplicant.getApplicantId());
        applicant.setAadharDetails(thisApplicant.getAadharDetails());
        applicantRepo.save(applicant);
        return ResponseEntity.accepted().body("Update Successful");
    }

    @Override
    public ResponseEntity<Object> removeApplicant(long id) throws ResourceNotFoundException {
        if(!applicantRepo.existsById(id)){
            throw new ResourceNotFoundException("Applicant with id "+id+" does not exist, so can not be deleted");
        }
        applicantRepo.deleteById(id);
        return ResponseEntity.accepted().body("Delete Successful");
    }
}
