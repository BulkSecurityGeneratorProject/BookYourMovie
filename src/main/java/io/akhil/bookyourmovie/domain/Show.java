package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Show.
 */
@Entity
@Table(name = "show")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Show implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @ManyToOne
    @JsonIgnoreProperties("shows")
    private Screen screen;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Movie movie;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Show time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Screen getScreen() {
        return screen;
    }

    public Show screen(Screen screen) {
        this.screen = screen;
        return this;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public Movie getMovie() {
        return movie;
    }

    public Show movie(Movie movie) {
        this.movie = movie;
        return this;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
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
        Show show = (Show) o;
        if (show.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), show.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Show{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
