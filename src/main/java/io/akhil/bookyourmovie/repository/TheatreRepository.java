package io.akhil.bookyourmovie.repository;

import io.akhil.bookyourmovie.domain.Theatre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Theatre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

}
