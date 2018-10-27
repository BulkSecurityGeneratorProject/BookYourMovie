package io.akhil.bookyourmovie.repository;

import io.akhil.bookyourmovie.domain.Screen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Screen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScreenRepository extends JpaRepository<Screen, Long> {

}
