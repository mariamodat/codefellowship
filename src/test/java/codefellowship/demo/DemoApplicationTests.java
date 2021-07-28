package codefellowship.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CodefellowshipApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void testCodefellowshipMVC() throws Exception {
//		mockMvc.perform(g).andExpect(content().string(containsString("Codefellowship")));
//	}
//
//	@Test
//	public void testLoginMVC() throws Exception {
//		mockMvc.perform(get("/login")).andExpect(content().string(containsString("Username")));
//	}



}