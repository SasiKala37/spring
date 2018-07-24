package com.bridgelabz.fundoonote;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	Date date=new Date();
	@Test
	public void verifyRegisterUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/register").contentType(MediaType.APPLICATION_JSON).content(
				"{ \"emailId\": \"sasikalag37@gmail.com\", \"userName\" : \"sasikala\", \"password\" : \"Sasi@123\" , \"confirmPassword\": \"Sasi@123\", \"firstName\" : \" sasi\", \"lastName\" : \"kala\",\"mobileNumber\": \"7032466169\"}")
				.accept(MediaType.APPLICATION_JSON))

				.andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("Registered successfully"))
				.andExpect(jsonPath("$.status").value(2)).andDo(print());
	}

	// @Test
	public void verifyLoginUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/login").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"emailId\": \"sasikalag37@gmail.com\",\"password\" : \"Sasi@123\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists()).andExpect(jsonPath("$.message").value("Login successfully"))
				.andExpect(jsonPath("$.status").value(3)).andDo(print());
	}

	// @Test
	public void verifyActivationUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/activation").param("token",
				"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1YjUwNWI4ZGJjZDI3OTc5N2QwNDBmNGEiLCJpYXQiOjE1MzE5OTI5NzQsImV4cCI6MTUzMjA3OTM3NH0.6HcFAKmPbKut3pSPw068RDkOfWnJlLixPZ4_9WKeVx4")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("User activated successfully"))
				.andExpect(jsonPath("$.status").value(0)).andDo(print());

	}

	// @Test
	public void verifyForgotPassword() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/forgotPassword").contentType(MediaType.APPLICATION_JSON)
				.content("sasikalag37@gmail.com").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("send the user mailid to reset password"))
				.andExpect(jsonPath("$.status").value(0)).andDo(print());
	}

	// @Test
	public void verifyResetPassword() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/user/resetPassword").contentType(MediaType.APPLICATION_JSON)
				.param("token",
						"eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1YjUwNWI4ZGJjZDI3OTc5N2QwNDBmNGEiLCJpYXQiOjE1MzE5OTM2NjAsImV4cCI6MTUzMjA4MDA2MH0.LC_IkWXbJrbnBOjB19F7TZTx6ASO85kf2u6fEj30o7s")
				.content("{\"newPassword\" : \"Sasi@453\" , \"confirmPassword\": \"Sasi@453\"}")
				.accept(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.message").value("reset the password successfully"))
				.andExpect(jsonPath("$.status").value(0)).andDo(print());
	}

	/******************** NoteController test cases ***********************/
	
	//@Test
	public void testCreateNote() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.post("/user/resetPassword").contentType(MediaType.APPLICATION_JSON)
				.content("{ \"title\": \"opinion\", \"description\" : \"mamta is a good girl\", \"color\" : \"blue\" , \"userId\": \"5b5586e0bcd27952c652cf1d\", \"archive\" : false, \"trash\" : false,\"pin\": false, \"createAt\": date, \"updateAt\": date, \"remindAt\": date)}")
				.accept(MediaType.APPLICATION_JSON))
				
				.andExpect(jsonPath("$.title").exists())
				.andExpect(jsonPath("$.description").exists())
				.andExpect(jsonPath("$.color").exists())
				.andExpect(jsonPath("$.userId").exists())
				.andExpect(jsonPath("$.archive").exists())
				.andExpect(jsonPath("$.pin").exists())
				.andExpect(jsonPath("$.trash").exists())
				.andExpect(jsonPath("createAt").exists())
				.andExpect(jsonPath("updateAt").exists())
				.andExpect(jsonPath("remindAt").exists())
				
				.andExpect(jsonPath("$.title").value("opinion"))
				.andExpect(jsonPath("$.description").value("mamta is a good girl"))
				.andExpect(jsonPath("$.color").value("blue"))
				.andExpect(jsonPath("$.userId").value("5b5586e0bcd27952c652cf1d"))
				;
				
				
	}
}
