package am.coding.challenge.core.dto;

import am.coding.challenge.core.enums.Capacity;
import am.coding.challenge.core.enums.DurationOption;
import am.coding.challenge.core.enums.EmploymentMode;
import am.coding.challenge.entity.Applicant;
import am.coding.challenge.entity.Project;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    private String repositoryUrl;
    private String liveUrl;
    @NotNull
    @NotBlank
    private String role;
    @NotNull
    private Integer startYear;
    @NotNull
    private Integer teamSize;
    @NotNull
    private Integer durationValue;
    @NotNull
    private DurationOption durationOption;
    @NotNull
    private EmploymentMode employmentMode;
    @NotNull
    private Capacity capacity;

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .repositoryUrl(project.getRepositoryUrl())
                .liveUrl(project.getLiveUrl())
                .role(project.getRole())
                .startYear(project.getStartYear())
                .teamSize(project.getTeamSize())
                .durationValue(project.getDurationValue())
                .durationOption(project.getDurationOption())
                .employmentMode(project.getEmploymentMode())
                .capacity(project.getCapacity())
                .build();
    }

    public static List<ProjectDto> toDto(List<Project> projects) {
        return projects.stream().map(ProjectDto::toDto).collect(Collectors.toList());
    }

    public static Project fromDto(Applicant applicant, ProjectDto dto) {
        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setRepositoryUrl(dto.getRepositoryUrl());
        project.setLiveUrl(dto.getLiveUrl());
        project.setRole(dto.getRole());
        project.setStartYear(dto.getStartYear());
        project.setTeamSize(dto.getTeamSize());
        project.setDurationValue(dto.getDurationValue());
        project.setDurationOption(dto.getDurationOption());
        project.setEmploymentMode(dto.getEmploymentMode());
        project.setCapacity(dto.getCapacity());
        project.setApplicant(applicant);
        return project;
    }
}
