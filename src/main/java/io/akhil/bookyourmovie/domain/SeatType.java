package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private SeatClass type;

    @Column(name = "price")
    private String price;

    @ManyToOne
    @JsonIgnoreProperties("seatTypes")
    private Screen screen;

    @OneToMany(mappedBy = "seatType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seat> seats = new HashSet<>();
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

    public Screen getScreen() {
        return screen;
    }

    public SeatType screen(Screen screen) {
        this.screen = screen;
        return this;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public SeatType seats(Set<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public SeatType addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setSeatType(this);
        return this;
    }

    public SeatType removeSeat(Seat seat) {
        this.seats.remove(seat);
        seat.setSeatType(null);
        return this;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
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
