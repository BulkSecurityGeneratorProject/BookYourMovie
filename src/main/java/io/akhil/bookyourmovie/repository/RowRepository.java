package io.akhil.bookyourmovie.repository;

import io.akhil.bookyourmovie.domain.Row;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Row entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RowRepository extends JpaRepository<Row, Long> {

}
