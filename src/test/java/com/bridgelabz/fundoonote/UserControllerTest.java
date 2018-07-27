package com.bridgelabz.fundoonote;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
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

	Date date = new Date();

	//@Test
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

/*	@MockBean
	private NoteService noteService;
*/
	/*// @Test
	public void testCreateNote() throws Exception {
		List<Label> list = new ArrayList<>();
		Label mockLabel = new Label();
		mockLabel.setLabelId("");
		mockLabel.setLabelName("");
		mockLabel.setUserId("");
		list.add(mockLabel);

		NoteDTO mockNote = new NoteDTO();
		mockNote.setNoteId("");
		mockNote.setArchive(false);
		mockNote.setColor("white");
		mockNote.setCreateAt(date);
		mockNote.setDescription("dhfgdjfghfk");
		mockNote.setLabelList(list);
		mockNote.setPin(false);
		mockNote.setRemindAt(date);
		mockNote.setTitle("fhdsjgh");
		mockNote.setTrash(false);
		mockNote.setUpdateAt(date);
		String userId = "5b56";
		mockNote.setUserId("");

		String inputJson = this.mapToJson(mockNote);
		
		Mockito.when(noteService.createNote(Mockito.any(CreateNoteDTO.class), userId)).thenReturn(mockNote);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.
				post("/note/create").
				accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result=mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response=result.getResponse();
		String outputJson=response.getContentAsString();
		assertThat(outputJson).isEqualTo(inputJson);
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	public String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}*/
}
