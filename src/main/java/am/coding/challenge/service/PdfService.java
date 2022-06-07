package am.coding.challenge.service;

import am.coding.challenge.core.dto.ApplicantDto;

import java.util.List;

public interface PdfService {

    byte[] createPdf(ApplicantDto applicantDto);

    byte[] createPdf(List<ApplicantDto> applicantDtoList);
}
