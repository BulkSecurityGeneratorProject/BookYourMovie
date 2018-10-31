package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.akhil.bookyourmovie.domain.enumeration.CityNames;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private CityNames name;

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Theatre> theatres = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CityNames getName() {
        return name;
    }

    public City name(CityNames name) {
        this.name = name;
        return this;
    }

    public void setName(CityNames name) {
        this.name = name;
    }

    public Set<Theatre> getTheatres() {
        return theatres;
    }

    public City theatres(Set<Theatre> theatres) {
        this.theatres = theatres;
        return this;
    }

    public City addTheatre(Theatre theatre) {
        this.theatres.add(theatre);
        theatre.setCity(this);
        return this;
    }

    public City removeTheatre(Theatre theatre) {
        this.theatres.remove(theatre);
        theatre.setCity(null);
        return this;
    }

    public void setTheatres(Set<Theatre> theatres) {
        this.theatres = theatres;
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
        City city = (City) o;
        if (city.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), city.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
