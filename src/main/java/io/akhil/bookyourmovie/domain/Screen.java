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

/**
 * A Screen.
 */
@Entity
@Table(name = "screen")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Screen implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonIgnoreProperties("screens")
    private Theatre theatre;

    @OneToMany(mappedBy = "screen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SeatType> seatTypes = new HashSet<>();
    @OneToMany(mappedBy = "screen")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Show> shows = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Screen name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public Screen theatre(Theatre theatre) {
        this.theatre = theatre;
        return this;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public Set<SeatType> getSeatTypes() {
        return seatTypes;
    }

    public Screen seatTypes(Set<SeatType> seatTypes) {
        this.seatTypes = seatTypes;
        return this;
    }

    public Screen addSeatType(SeatType seatType) {
        this.seatTypes.add(seatType);
        seatType.setScreen(this);
        return this;
    }

    public Screen removeSeatType(SeatType seatType) {
        this.seatTypes.remove(seatType);
        seatType.setScreen(null);
        return this;
    }

    public void setSeatTypes(Set<SeatType> seatTypes) {
        this.seatTypes = seatTypes;
    }

    public Set<Show> getShows() {
        return shows;
    }

    public Screen shows(Set<Show> shows) {
        this.shows = shows;
        return this;
    }

    public Screen addShow(Show show) {
        this.shows.add(show);
        show.setScreen(this);
        return this;
    }

    public Screen removeShow(Show show) {
        this.shows.remove(show);
        show.setScreen(null);
        return this;
    }

    public void setShows(Set<Show> shows) {
        this.shows = shows;
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
        Screen screen = (Screen) o;
        if (screen.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), screen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Screen{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
