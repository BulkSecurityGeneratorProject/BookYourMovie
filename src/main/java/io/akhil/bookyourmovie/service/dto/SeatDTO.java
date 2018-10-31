package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.akhil.bookyourmovie.domain.enumeration.Status;

/**
 * A DTO for the Seat entity.
 */
public class SeatDTO implements Serializable {

    private Long id;

    @NotNull
    private String seatNumber;

    @NotNull
    private Status status;

    private Long bookingId;

    private Long screenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

        SeatDTO seatDTO = (SeatDTO) o;
        if (seatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatDTO{" +
            "id=" + getId() +
            ", seatNumber='" + getSeatNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", booking=" + getBookingId() +
            ", screen=" + getScreenId() +
            "}";
    }
}
