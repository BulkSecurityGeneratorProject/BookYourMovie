package io.akhil.bookyourmovie.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.akhil.bookyourmovie.domain.enumeration.SeatClass;

/**
 * A DTO for the SeatType entity.
 */
public class SeatTypeDTO implements Serializable {

    private Long id;

    private SeatClass type;

    private String price;

    private Long screenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeatClass getType() {
        return type;
    }

    public void setType(SeatClass type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SeatTypeDTO seatTypeDTO = (SeatTypeDTO) o;
        if (seatTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", price='" + getPrice() + "'" +
            ", screen=" + getScreenId() +
            "}";
    }
}
