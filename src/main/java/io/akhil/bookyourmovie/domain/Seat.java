package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.akhil.bookyourmovie.domain.enumeration.Status;

/**
 * A Seat.
 */
@Entity
@Table(name = "seat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "seat_number", nullable = false)
    private Integer seatNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne
    @JsonIgnoreProperties("seats")
    private Booking booking;

    @ManyToOne
    @JsonIgnoreProperties("seats")
    private Screen screen;

    @OneToMany(mappedBy = "seat")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Row> types = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public Seat seatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Status getStatus() {
        return status;
    }

    public Seat status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Booking getBooking() {
        return booking;
    }

    public Seat booking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Screen getScreen() {
        return screen;
    }

    public Seat screen(Screen screen) {
        this.screen = screen;
        return this;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Set<Row> getTypes() {
        return types;
    }

    public Seat types(Set<Row> rows) {
        this.types = rows;
        return this;
    }

    public Seat addType(Row row) {
        this.types.add(row);
        row.setSeat(this);
        return this;
    }

    public Seat removeType(Row row) {
        this.types.remove(row);
        row.setSeat(null);
        return this;
    }

    public void setTypes(Set<Row> rows) {
        this.types = rows;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Seat seat = (Seat) o;
        if (seat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Seat{" +
            "id=" + getId() +
            ", seatNumber=" + getSeatNumber() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
