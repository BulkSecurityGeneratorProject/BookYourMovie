package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Screen entity.
 */
public class ScreenDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Long theatreId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(Long theatreId) {
        this.theatreId = theatreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScreenDTO screenDTO = (ScreenDTO) o;
        if (screenDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screenDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScreenDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", theatre=" + getTheatreId() +
            "}";
    }
}
