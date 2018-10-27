package io.akhil.bookyourmovie.repository;

import io.akhil.bookyourmovie.domain.Show;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Show entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

}
