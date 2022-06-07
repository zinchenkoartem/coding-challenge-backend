package am.coding.challenge.service;

import am.coding.challenge.core.dto.ApplicantDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplicantService {

    ApplicantDto getApplicant(Long id);

    List<ApplicantDto> getAllApplicants();

    ApplicantDto addApplicant(ApplicantDto applicantDto);

    ApplicantDto updateApplicant(ApplicantDto applicantDto);

    void delete(Long id);

    byte[] toPdf(ApplicantDto applicant);

    byte[] toPdf(List<ApplicantDto> applicants);
}
