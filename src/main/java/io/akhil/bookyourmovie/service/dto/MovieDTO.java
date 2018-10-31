package io.akhil.bookyourmovie.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.akhil.bookyourmovie.domain.enumeration.Language;

/**
 * A DTO for the Movie entity.
 */
public class MovieDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Language language;

    private String description;

    @NotNull
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MovieDTO movieDTO = (MovieDTO) o;
        if (movieDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), movieDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", language='" + getLanguage() + "'" +
            ", description='" + getDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
