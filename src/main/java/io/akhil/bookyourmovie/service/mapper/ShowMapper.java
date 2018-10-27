package io.akhil.bookyourmovie.service.mapper;

import io.akhil.bookyourmovie.domain.*;
import io.akhil.bookyourmovie.service.dto.ShowDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Show and its DTO ShowDTO.
 */
@Mapper(componentModel = "spring", uses = {ScreenMapper.class, MovieMapper.class})
public interface ShowMapper extends EntityMapper<ShowDTO, Show> {

    @Mapping(source = "screen.id", target = "screenId")
    @Mapping(source = "movie.id", target = "movieId")
    ShowDTO toDto(Show show);

    @Mapping(source = "screenId", target = "screen")
    @Mapping(source = "movieId", target = "movie")
    Show toEntity(ShowDTO showDTO);

    default Show fromId(Long id) {
        if (id == null) {
            return null;
        }
        Show show = new Show();
        show.setId(id);
        return show;
    }
}
