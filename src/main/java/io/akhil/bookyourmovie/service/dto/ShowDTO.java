package io.akhil.bookyourmovie.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Show entity.
 */
public class ShowDTO implements Serializable {

    private Long id;

    private ZonedDateTime time;

    private Long screenId;

    private Long movieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Long getScreenId() {
        return screenId;
    }

    public void setScreenId(Long screenId) {
        this.screenId = screenId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShowDTO showDTO = (ShowDTO) o;
        if (showDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), showDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShowDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", screen=" + getScreenId() +
            ", movie=" + getMovieId() +
            "}";
    }
}
