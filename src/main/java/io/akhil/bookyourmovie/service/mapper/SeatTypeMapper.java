package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.SeatTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeatType and its DTO SeatTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {ScreenMapper.class})
public interface SeatTypeMapper extends EntityMapper<SeatTypeDTO, SeatType> {

    @Mapping(source = "screen.id", target = "screenId")
    SeatTypeDTO toDto(SeatType seatType);

    @Mapping(source = "screenId", target = "screen")
    @Mapping(target = "seats", ignore = true)
    SeatType toEntity(SeatTypeDTO seatTypeDTO);

    default SeatType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeatType seatType = new SeatType();
        seatType.setId(id);
        return seatType;
    }
}
