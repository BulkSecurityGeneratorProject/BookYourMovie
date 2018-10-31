package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.akhil.bookyourmovie.domain.enumeration.SeatClass;

/**
 * A DTO for the SeatType entity.
 */
public class SeatTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private SeatClass type;

    @NotNull
    private String price;

    private Long seatId;

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

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
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
            ", seat=" + getSeatId() +
            "}";
    }
}
