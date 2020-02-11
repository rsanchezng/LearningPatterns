package com.fime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fime.web.rest.TestUtil;

public class StudentActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentActivity.class);
        StudentActivity studentActivity1 = new StudentActivity();
        studentActivity1.setId(1L);
        StudentActivity studentActivity2 = new StudentActivity();
        studentActivity2.setId(studentActivity1.getId());
        assertThat(studentActivity1).isEqualTo(studentActivity2);
        studentActivity2.setId(2L);
        assertThat(studentActivity1).isNotEqualTo(studentActivity2);
        studentActivity1.setId(null);
        assertThat(studentActivity1).isNotEqualTo(studentActivity2);
    }
}
