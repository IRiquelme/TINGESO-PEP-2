package como.tingeso.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentEntity {
    @Id
    @NotNull
    private String rut;
    private String names;
    private String lastNames;
    private String dateBirth;
    private String schoolType;
    private String schoolName;
    private String yearsSinceGraduation;
}