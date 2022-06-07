package am.coding.challenge.core.dto;

import am.coding.challenge.entity.Applicant;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ApplicantDto {
    private Long id;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String githubUserName;
    @NotNull
    @NotEmpty
    private List<ProjectDto> pastProjects;

    public static ApplicantDto toDto(Applicant applicant) {
        return ApplicantDto.builder()
                .id(applicant.getId())
                .firstName(applicant.getFirstName())
                .lastName(applicant.getLastName())
                .email(applicant.getEmail())
                .githubUserName(applicant.getGithubUserName())
                .pastProjects(ProjectDto.toDto(applicant.getPastProjects()))
                .build();
    }

    public static List<ApplicantDto> toDto(List<Applicant> applicants) {
        return applicants.stream().map(ApplicantDto::toDto).collect(Collectors.toList());
    }

    public static Applicant fromDto(ApplicantDto dto) {
        Applicant applicant = new Applicant();
        applicant.setEmail(dto.getEmail());
        applicant.setFirstName(dto.getFirstName());
        applicant.setLastName(dto.getLastName());
        applicant.setGithubUserName(dto.getGithubUserName());
        applicant.setPastProjects(dto.getPastProjects().stream()
                .map(e -> ProjectDto.fromDto(applicant, e))
                .collect(Collectors.toList()));
        return applicant;
    }
}
