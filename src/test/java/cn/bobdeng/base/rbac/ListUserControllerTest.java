package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ListUserControllerTest extends IntegrationTest {
    @Autowired
    ListUserController listUserController;

    @Test
    public void should_return_empty_when_has_no_user() {
        List list = listUserController.listUser();
        assertThat(list.isEmpty(), is(true));
    }
}
