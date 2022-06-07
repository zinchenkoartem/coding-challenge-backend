package am.coding.challenge.service.impl;

import am.coding.challenge.core.dto.ApplicantDto;
import am.coding.challenge.core.dto.ProjectDto;
import am.coding.challenge.service.PdfService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {
    private static final String GIT_HUB_USER_URL = "https://api.github.com/users/";

    @Override
    public byte[] createPdf(ApplicantDto applicantDto) {
        Document document = new Document();
        document.setMargins(2, 2, 10, 10);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
            document.open();
            fillData(document, applicantDto);
            document.close();
            pdfWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return os.toByteArray();
    }

    @Override
    public byte[] createPdf(List<ApplicantDto> applicantDtoList) {
        Document document = new Document();
        document.setMargins(2, 2, 10, 10);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
            document.open();
            applicantDtoList.forEach(e -> {
                fillData(document, e);
                document.newPage();
            });
            document.close();
            pdfWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return os.toByteArray();
    }

    private void fillData(Document document, ApplicantDto applicantDto) {
        try {
            PdfPTable applicantDatatable = new PdfPTable(2);
            applicantDatatable.setWidths(new float[]{100f, 300f});
            Image gitHubAvatar = getGitHubAvatar(applicantDto);
            gitHubAvatar.scaleToFit(100f, 100f);
            applicantDatatable.addCell(gitHubAvatar);
            addApplicantInfo(applicantDatatable, applicantDto);
            document.add(applicantDatatable);

            PdfPTable captionTable = new PdfPTable(1);
            PdfPCell captionCell = new PdfPCell();
            captionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            captionCell.setPhrase(new Phrase("Past Projects:"));
            captionTable.addCell(captionCell);
            document.add(captionTable);

            for (ProjectDto dto : applicantDto.getPastProjects()) {
                PdfPTable prjNameTable = new PdfPTable(1);
                PdfPCell prjNameCell = new PdfPCell();
                prjNameCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                prjNameCell.setPhrase(new Phrase("Project name: " + dto.getName()));
                prjNameTable.addCell(prjNameCell);
                document.add(prjNameTable);
                PdfPTable projInfoTable = new PdfPTable(1);
                addApplicantPastProject(projInfoTable, dto);
                document.add(projInfoTable);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Image getGitHubAvatar(ApplicantDto applicantDto) throws IOException, BadElementException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Postman");
        headers.add("Accept", "application/json");
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(GIT_HUB_USER_URL + applicantDto.getGithubUserName(), HttpMethod.GET, request, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode avatarUrlValue = root.path("avatar_url");
        String avatarUrl = avatarUrlValue.asText();
        return Image.getInstance(new URL(avatarUrl));
    }

    private void addApplicantInfo(PdfPTable table, ApplicantDto applicantDto) {
        PdfPTable innerTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(applicantDto.getFirstName() + " " + applicantDto.getLastName()));
        innerTable.addCell(cell);
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase("Email: " + applicantDto.getEmail()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("GitHubUserName: " + applicantDto.getGithubUserName()));
        innerTable.addCell(cell);

        table.addCell(innerTable);
    }

    private void addApplicantPastProject(PdfPTable table, ProjectDto project) {
        PdfPTable innerTable = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase("Employment mode: " + project.getEmploymentMode()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Capacity: " + project.getCapacity()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Duration: " + project.getDurationValue() + " " + project.getDurationOption().toString().toLowerCase()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Start year: " + project.getStartYear()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Role: " + project.getRole()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Team size: " + project.getTeamSize()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Link to the repository: " + project.getRepositoryUrl()));
        innerTable.addCell(cell);
        cell.setPhrase(new Phrase("Link to the live url: " + project.getLiveUrl()));
        innerTable.addCell(cell);

        table.addCell(innerTable);
    }

}
