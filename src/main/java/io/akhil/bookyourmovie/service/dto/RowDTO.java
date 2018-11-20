package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Row entity.
 */
public class RowDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer serialNumber;

    @NotNull
    private Integer startPos;

    @NotNull
    private String price;

    @NotNull
    private String name;

    private Long seatId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        RowDTO rowDTO = (RowDTO) o;
        if (rowDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rowDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RowDTO{" +
            "id=" + getId() +
            ", serialNumber=" + getSerialNumber() +
            ", startPos=" + getStartPos() +
            ", price='" + getPrice() + "'" +
            ", name='" + getName() + "'" +
            ", seat=" + getSeatId() +
            "}";
    }
}
