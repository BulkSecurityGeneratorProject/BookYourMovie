package io.akhil.bookyourmovie.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Row.
 */
@Entity
@Table(name = "row")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Row implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "serial_number", nullable = false)
    private Integer serialNumber;

    @NotNull
    @Column(name = "start_pos", nullable = false)
    private Integer startPos;

    @NotNull
    @Column(name = "price", nullable = false)
    private String price;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

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

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public Row serialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public Row startPos(Integer startPos) {
        this.startPos = startPos;
        return this;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public String getPrice() {
        return price;
    }

    public Row price(String price) {
        this.price = price;
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Row name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Seat getSeat() {
        return seat;
    }

    public Row seat(Seat seat) {
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
        Row row = (Row) o;
        if (row.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), row.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Row{" +
            "id=" + getId() +
            ", serialNumber=" + getSerialNumber() +
            ", startPos=" + getStartPos() +
            ", price='" + getPrice() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
