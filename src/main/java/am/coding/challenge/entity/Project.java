package am.coding.challenge.entity;

import am.coding.challenge.core.enums.Capacity;
import am.coding.challenge.core.enums.DurationOption;
import am.coding.challenge.core.enums.EmploymentMode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(exclude = {"applicant"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String repositoryUrl;
    private String liveUrl;
    private String role;
    private Integer startYear;
    private Integer teamSize;
    private Integer durationValue;
    @Enumerated(EnumType.STRING)
    private DurationOption durationOption;
    @Enumerated(EnumType.STRING)
    private EmploymentMode employmentMode;
    @Enumerated(EnumType.STRING)
    private Capacity capacity;
    @ManyToOne
    private Applicant applicant;
}
