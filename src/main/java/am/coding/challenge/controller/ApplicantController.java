package am.coding.challenge.controller;

import am.coding.challenge.core.dto.ApplicantDto;

import am.coding.challenge.service.ApplicantService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class ApplicantController {

    private final ApplicantService applicantService;

    public ApplicantController(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    @Operation(summary = "Get all applicants")
    @GetMapping("/applicant")
    public ResponseEntity getAll() {
        try {
            List<ApplicantDto> allApplicants = applicantService.getAllApplicants();
            return ResponseEntity.ok(allApplicants);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get applicant by id")
    @GetMapping("/applicant/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        try {
            ApplicantDto result = applicantService.getApplicant(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Add new applicant")
    @PostMapping("/applicant")
    public ResponseEntity add(@Valid @RequestBody ApplicantDto dto) {
        try {
            ApplicantDto result = applicantService.addApplicant(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Update applicant")
    @PutMapping("/applicant")
    public ResponseEntity update(@Valid @RequestBody ApplicantDto dto) {
        try {
            ApplicantDto result = applicantService.updateApplicant(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete applicant")
    @DeleteMapping("/applicant/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            applicantService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get pdf file for applicant data")
    @GetMapping(value = "/applicant/pdf/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getPdf(@PathVariable Long id) {
        try {
            ApplicantDto applicant = applicantService.getApplicant(id);
            byte[] bytes = applicantService.toPdf(applicant);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + applicant.getFirstName() + applicant.getLastName() + ".pdf")
                    .contentLength(bytes.length)
                    .body(bytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get pdf file for all applicants data")
    @GetMapping(value = "/applicant/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity getAllPdf() {
        try {
            List<ApplicantDto> applicants = applicantService.getAllApplicants();
            byte[] bytes = applicantService.toPdf(applicants);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + "AllApplicants.pdf")
                    .contentLength(bytes.length)
                    .body(bytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
