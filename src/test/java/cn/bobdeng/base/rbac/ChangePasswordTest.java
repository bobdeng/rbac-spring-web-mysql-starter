package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.rbac.repos.UserPasswordDAO;
import cn.bobdeng.base.user.PasswordDO;
import cn.bobdeng.testtools.TestResource;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangePasswordTest extends IntegrationTest {
    @Autowired
    UserPasswordDAO userPasswordDAO;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        super.setup();
        super.setSessionUser();
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void should_set_password() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/rbac/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new TestResource(this, "change_password.json").readString())
        ).andExpect(status().isOk());

        PasswordDO passwordDO = userPasswordDAO.findById(getSessionUser().getUserId().id()).orElseThrow();
        assertThat(bCryptPasswordEncoder.matches("M47FY7k6M", passwordDO.getPassword()), CoreMatchers.is(true));
    }
}
