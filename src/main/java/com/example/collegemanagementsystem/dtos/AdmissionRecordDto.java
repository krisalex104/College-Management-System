package com.example.collegemanagementsystem.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdmissionRecordDto {

    private Long id;

    private Integer fees;

    private StudentDto studentDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdmissionRecordDto that = (AdmissionRecordDto) o;
        return Objects.equals(id, that.id) && Objects.equals(fees, that.fees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fees);
    }
}
