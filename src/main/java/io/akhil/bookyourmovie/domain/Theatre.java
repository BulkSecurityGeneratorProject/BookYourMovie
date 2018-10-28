package io.akhil.bookyourmovie.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Theatre.
 */
@Entity
@Table(name = "theatre")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Theatre implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
	@SequenceGenerator(name = "sequenceGenerator")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "area")
	private String area;

	@ManyToOne
	@JsonIgnoreProperties("theatres")
	private City city;

	@OneToMany(mappedBy = "theatre")
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Screen> screens = new HashSet<>();

	@OneToOne
	@JoinColumn(unique = true)
	private User owner;

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not
	// remove
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public Theatre name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public Theatre area(String area) {
		this.area = area;
		return this;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public City getCity() {
		return city;
	}

	public Theatre city(City city) {
		this.city = city;
		return this;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Set<Screen> getScreens() {
		return screens;
	}

	public Theatre screens(Set<Screen> screens) {
		this.screens = screens;
		return this;
	}

	public Theatre addScreen(Screen screen) {
		this.screens.add(screen);
		screen.setTheatre(this);
		return this;
	}

	public Theatre removeScreen(Screen screen) {
		this.screens.remove(screen);
		screen.setTheatre(null);
		return this;
	}

	public void setScreens(Set<Screen> screens) {
		this.screens = screens;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters
	// and setters here, do not remove

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Theatre theatre = (Theatre) o;
		if (theatre.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), theatre.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "Theatre{" + "id=" + getId() + ", name='" + getName() + "'" + ", area='" + getArea() + "'" + "}";
	}
}
