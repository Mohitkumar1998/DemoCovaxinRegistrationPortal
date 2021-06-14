package com.wipro.covaxin.controller;

import com.wipro.covaxin.entity.Applicant;
import com.wipro.covaxin.exceptionhandling.DuplicateResourceException;
import com.wipro.covaxin.exceptionhandling.ResourceNotFoundException;
import com.wipro.covaxin.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/")
public class RestController {
    @Autowired
    private ApplicantService service;

    @GetMapping("/applicants")
    private List<Applicant> getAllApplicants() throws ResourceNotFoundException {
        return service.getAllApplicants();
    }

    @GetMapping("/applicant/{id}")
    private Applicant getApplicantById(@PathVariable long id) throws ResourceNotFoundException {
        return service.getApplicantById(id);
    }

    @PostMapping("/applicants")
    private ResponseEntity<Object> createApplicant(@RequestBody Applicant newApplicant) throws DuplicateResourceException {
        return service.createApplicant(newApplicant);
    }

    @PutMapping("/updateApplicant/{id}")
    private ResponseEntity<Object> updateApplicant(@RequestBody Applicant applicant, @PathVariable long id) throws ResourceNotFoundException {
        return service.updateApplicant(applicant,id);
    }

    @DeleteMapping("/removeApplicant/{id}")
    private ResponseEntity<Object> removeApplicant(@PathVariable long id) throws ResourceNotFoundException {
        return service.removeApplicant(id);
    }
}
