package com.fime.repository;
import com.fime.domain.StudentActivity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the StudentActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long>, JpaSpecificationExecutor<StudentActivity> {

}
