package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Theatre entity.
 */
public class TheatreDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String area;

    private Long cityId;

    private Long ownerId;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TheatreDTO theatreDTO = (TheatreDTO) o;
        if (theatreDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), theatreDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TheatreDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", area='" + getArea() + "'" +
            ", city=" + getCityId() +
            ", owner=" + getOwnerId() +
            "}";
    }
}
