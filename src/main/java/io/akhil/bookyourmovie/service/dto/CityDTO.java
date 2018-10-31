package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.akhil.bookyourmovie.domain.enumeration.CityNames;

/**
 * A DTO for the City entity.
 */
public class CityDTO implements Serializable {

    private Long id;

    @NotNull
    private CityNames name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CityNames getName() {
        return name;
    }

    public void setName(CityNames name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;
        if (cityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
