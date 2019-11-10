package com.fime.repository;

import com.fime.domain.StudentSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentScheduleRepository extends JpaRepository<StudentSchedule, Long> {

}
