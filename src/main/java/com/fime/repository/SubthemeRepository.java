package com.fime.repository;

import com.fime.domain.Subtheme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Subtheme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubthemeRepository extends JpaRepository<Subtheme, Long> {

}
