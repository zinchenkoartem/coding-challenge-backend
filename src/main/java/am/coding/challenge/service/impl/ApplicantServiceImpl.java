package am.coding.challenge.service.impl;

import am.coding.challenge.core.dto.ApplicantDto;
import am.coding.challenge.core.dto.ProjectDto;
import am.coding.challenge.entity.Applicant;
import am.coding.challenge.entity.Project;
import am.coding.challenge.repository.ApplicantRepository;
import am.coding.challenge.service.ApplicantService;
import am.coding.challenge.service.PdfService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final PdfService pdfService;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository, PdfService pdfService) {
        this.applicantRepository = applicantRepository;
        this.pdfService = pdfService;
    }

    @Override
    public ApplicantDto getApplicant(Long id) {
        Applicant applicant = applicantRepository.getById(id);
        return ApplicantDto.toDto(applicant);
    }

    @Override
    public List<ApplicantDto> getAllApplicants() {
        List<Applicant> all = applicantRepository.findAll();
        ArrayList<ApplicantDto> result = new ArrayList<>();
        all.forEach(e -> result.add(ApplicantDto.toDto(e)));
        return result;
    }

    @Override
    public ApplicantDto addApplicant(ApplicantDto applicantDto) {
        List<Applicant> allByEmail = applicantRepository.findAllByEmail(applicantDto.getEmail());
        if (!allByEmail.isEmpty()) {
            throw new RuntimeException("Applicant with such email already exists");
        }
        Applicant applicant = applicantRepository.save(ApplicantDto.fromDto(applicantDto));
        return ApplicantDto.toDto(applicant);
    }

    @Override
    public ApplicantDto updateApplicant(ApplicantDto applicantDto) {
        if (applicantDto == null || applicantDto.getId() == null) {
            throw new RuntimeException("No id");
        }
        final Applicant applicant = applicantRepository.getById(applicantDto.getId());
        applicant.setEmail(applicantDto.getEmail());
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setGithubUserName(applicantDto.getGithubUserName());
        applicant.getPastProjects().clear();
        List<Project> collect = applicantDto.getPastProjects().stream().map(e -> ProjectDto.fromDto(applicant, e)).collect(Collectors.toList());
        applicant.getPastProjects().addAll(collect);
        Applicant result = applicantRepository.save(applicant);
        return ApplicantDto.toDto(result);
    }

    @Override
    public void delete(Long id) {
        applicantRepository.deleteById(id);
    }

    @Override
    public byte[] toPdf(ApplicantDto applicant) {
        return pdfService.createPdf(applicant);
    }

    @Override
    public byte[] toPdf(List<ApplicantDto> applicant) {
        return pdfService.createPdf(applicant);
    }
}
