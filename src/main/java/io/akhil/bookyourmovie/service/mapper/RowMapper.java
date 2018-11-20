package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.RowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Row and its DTO RowDTO.
 */
@Mapper(componentModel = "spring", uses = {SeatMapper.class})
public interface RowMapper extends EntityMapper<RowDTO, Row> {

    @Mapping(source = "seat.id", target = "seatId")
    RowDTO toDto(Row row);

    @Mapping(source = "seatId", target = "seat")
    Row toEntity(RowDTO rowDTO);

    default Row fromId(Long id) {
        if (id == null) {
            return null;
        }
        Row row = new Row();
        row.setId(id);
        return row;
    }
}
