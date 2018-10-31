package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import io.akhil.bookyourmovie.domain.enumeration.SeatClass;

/**
 * A SeatType.
 */
@Entity
@Table(name = "seat_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SeatType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private SeatClass type;

    @NotNull
    @Column(name = "price", nullable = false)
    private String price;

    @ManyToOne
    @JsonIgnoreProperties("types")
    private Seat seat;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SeatClass getType() {
        return type;
    }

    public SeatType type(SeatClass type) {
        this.type = type;
        return this;
    }

    public void setType(SeatClass type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public SeatType price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public SeatType seat(Seat seat) {
        this.seat = seat;
        return this;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
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
        SeatType seatType = (SeatType) o;
        if (seatType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), seatType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SeatType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", price='" + getPrice() + "'" +
            "}";
    }
}
