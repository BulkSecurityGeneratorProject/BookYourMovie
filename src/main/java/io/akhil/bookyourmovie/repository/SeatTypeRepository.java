package io.akhil.bookyourmovie.repository;

import io.akhil.bookyourmovie.domain.SeatType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SeatType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType, Long> {

}
