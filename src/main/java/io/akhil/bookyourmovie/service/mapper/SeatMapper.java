package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.SeatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seat and its DTO SeatDTO.
 */
@Mapper(componentModel = "spring", uses = {BookingMapper.class, SeatTypeMapper.class})
public interface SeatMapper extends EntityMapper<SeatDTO, Seat> {

    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "seatType.id", target = "seatTypeId")
    SeatDTO toDto(Seat seat);

    @Mapping(source = "bookingId", target = "booking")
    @Mapping(source = "seatTypeId", target = "seatType")
    Seat toEntity(SeatDTO seatDTO);

    default Seat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seat seat = new Seat();
        seat.setId(id);
        return seat;
    }
}
