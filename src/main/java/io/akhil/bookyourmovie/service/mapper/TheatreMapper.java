package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.TheatreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Theatre and its DTO TheatreDTO.
 */
@Mapper(componentModel = "spring", uses = {CityMapper.class})
public interface TheatreMapper extends EntityMapper<TheatreDTO, Theatre> {

    @Mapping(source = "city.id", target = "cityId")
    TheatreDTO toDto(Theatre theatre);

    @Mapping(source = "cityId", target = "city")
    @Mapping(target = "screens", ignore = true)
    Theatre toEntity(TheatreDTO theatreDTO);

    default Theatre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Theatre theatre = new Theatre();
        theatre.setId(id);
        return theatre;
    }
}
