package com.wipro.covaxin.service;

import com.wipro.covaxin.entity.Applicant;
import com.wipro.covaxin.exceptionhandling.DuplicateResourceException;
import com.wipro.covaxin.exceptionhandling.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ApplicantService {

    List<Applicant> getAllApplicants() throws ResourceNotFoundException;

    Applicant getApplicantById(long id) throws ResourceNotFoundException;

    ResponseEntity<Object> createApplicant(Applicant applicant) throws DuplicateResourceException;

    ResponseEntity<Object> updateApplicant(Applicant applicant, long id) throws ResourceNotFoundException;

    ResponseEntity<Object> removeApplicant(long id) throws ResourceNotFoundException;
}
