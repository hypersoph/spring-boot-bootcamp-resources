package com.ltp.gradesubmission;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.ltp.gradesubmission.controller.GradeController;

@SpringBootTest
@AutoConfigureMockMvc
class GradeSubmissionApplicationTests {

	@Autowired
	private GradeController controller;

	@Autowired
	private MockMvc mockMvc;
	

	@Test
	void contextLoads() {
		assertNotNull(mockMvc);
	}

	@Test
	public void testShowGradeForm() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/?id=123");
		
		mockMvc.perform(request)
		.andExpect(status().is2xxSuccessful())
		.andExpect(model().attributeExists("form"))
		.andExpect(model().attributeExists("grade"));

	}

	@Test
	public void testSuccessfulSubmission() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.post("/handleSubmit")
		.param("name","Harry")
		.param("subject","Maths")
		.param("score","C");

		mockMvc.perform(request)
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/grades"));
	}

	@Test
	public void testUnsuccessfulSubmission() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/handleSubmit")
        .param("name","    ")
        .param("subject","    ")
        .param("score","R+");

		mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("form"));
    
	}

	@Test
	public void testGetGrades() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/grades");
        
        mockMvc.perform(request)
        .andExpect(status().is2xxSuccessful())
		.andExpect(view().name("grades"))
        .andExpect(model().attributeExists("grades"));
	}

}
