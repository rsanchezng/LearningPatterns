package com.fime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fime.web.rest.TestUtil;

public class StudentScheduleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentSchedule.class);
        StudentSchedule studentSchedule1 = new StudentSchedule();
        studentSchedule1.setId(1L);
        StudentSchedule studentSchedule2 = new StudentSchedule();
        studentSchedule2.setId(studentSchedule1.getId());
        assertThat(studentSchedule1).isEqualTo(studentSchedule2);
        studentSchedule2.setId(2L);
        assertThat(studentSchedule1).isNotEqualTo(studentSchedule2);
        studentSchedule1.setId(null);
        assertThat(studentSchedule1).isNotEqualTo(studentSchedule2);
    }
}
