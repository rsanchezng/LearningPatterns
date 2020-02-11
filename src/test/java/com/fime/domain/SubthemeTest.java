package com.fime.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fime.web.rest.TestUtil;

public class SubthemeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subtheme.class);
        Subtheme subtheme1 = new Subtheme();
        subtheme1.setId(1L);
        Subtheme subtheme2 = new Subtheme();
        subtheme2.setId(subtheme1.getId());
        assertThat(subtheme1).isEqualTo(subtheme2);
        subtheme2.setId(2L);
        assertThat(subtheme1).isNotEqualTo(subtheme2);
        subtheme1.setId(null);
        assertThat(subtheme1).isNotEqualTo(subtheme2);
    }
}
