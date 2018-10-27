package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_time")
    private Instant time;

    @OneToOne    @JoinColumn(unique = true)
    private Show show;

    @OneToMany(mappedBy = "booking")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Seat> seats = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public Booking time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Show getShow() {
        return show;
    }

    public Booking show(Show show) {
        this.show = show;
        return this;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public Booking seats(Set<Seat> seats) {
        this.seats = seats;
        return this;
    }

    public Booking addSeat(Seat seat) {
        this.seats.add(seat);
        seat.setBooking(this);
        return this;
    }

    public Booking removeSeat(Seat seat) {
        this.seats.remove(seat);
        seat.setBooking(null);
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
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
