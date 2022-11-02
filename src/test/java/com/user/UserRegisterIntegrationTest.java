package com.user;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@ActiveProfiles({"integration", "localhost"})
@SpringBootTest(classes = UserRegisterIntegrationTest.class)
public class UserRegisterIntegrationTest {

    @ClassRule
    public static final WireMockRule wireMockRule = new WireMockRule(8600);

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws IOException{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
   }

   @Test
   public void getModule1() throws Exception {
        String expectedResponse = FileUtils.readFileToString(new File(this.getClass().getClassLoader().getResource("integration/module1Response.json").getFile()), "UTF-8");
        stubFor(get(urlEqualTo("/module1")).willReturn(aResponse().withBody(expectedResponse).withHeader("Content-Type", "application/json")));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/home")
                .header("Authorization", "Auth")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
       System.out.println(mvcResult.getResponse().getContentAsString());
       JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
   }
}
