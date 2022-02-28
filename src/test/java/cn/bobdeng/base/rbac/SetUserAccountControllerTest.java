package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.rbac.user.SetUserAccountForm;
import cn.bobdeng.base.user.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetUserAccountControllerTest extends IntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_bind_account_when_set_account() throws Exception {
        User user = new Users().newUser(UserName.empty());
        SetUserAccountForm form = new SetUserAccountForm();
        form.setAccount("zhangsan");
        mockMvc.perform(put("/rbac/user/" + user.id() + "/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form))
        )
                .andExpect(status().isOk());
        UserId userId = Users.accountRepository.findUserByAccount(new Account(form.getAccount())).orElse(null);
        assertThat(userId, is(user.getId()));
    }
}
