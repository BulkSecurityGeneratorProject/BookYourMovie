package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.ScreenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Screen and its DTO ScreenDTO.
 */
@Mapper(componentModel = "spring", uses = {TheatreMapper.class})
public interface ScreenMapper extends EntityMapper<ScreenDTO, Screen> {

    @Mapping(source = "theatre.id", target = "theatreId")
    ScreenDTO toDto(Screen screen);

    @Mapping(source = "theatreId", target = "theatre")
    @Mapping(target = "seatTypes", ignore = true)
    @Mapping(target = "shows", ignore = true)
    Screen toEntity(ScreenDTO screenDTO);

    default Screen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Screen screen = new Screen();
        screen.setId(id);
        return screen;
    }
}
