package nl.tudelft.oopp.group54.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import nl.tudelft.oopp.group54.controllers.lectures.LectureController;
import nl.tudelft.oopp.group54.controllers.lectures.LectureService;
import nl.tudelft.oopp.group54.controllers.lectures.LectureServiceImpl;
import nl.tudelft.oopp.group54.entities.Lecture;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = {LectureController.class})
public class LectureTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private LectureServiceImpl lectureService;

    @InjectMocks
    private LectureController lectureController;


    /**
     * Initialize the mockMvc.
     */
    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(lectureController).build();

    }

    @Test
    public void createLectureTest() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        when(lectureService.createNewLecture(any(Date.class), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
            .post("/lectures")
            .content("{\n"
                    +
                "    \"startTime\": 1614451405020,\n"
                    +
                "    \"lectureName\": \"lecture1\"\n"
                    +
                "}")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
            .andReturn().getResponse();
        System.out.println(result);


        assertEquals("{\"success\":true}", result.getContentAsString());
    }

    @Test
    public void createLectureTest_noInfo() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        //when(lectureService.createNewLecture(any(Date.class), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);


        assertEquals("{\"message\":\"Expected lectureName and startTime "
                +
                "to be provided.\",\"success\":\"false\"}", result.getContentAsString());
    }

    @Test
    public void joinOngoing_info() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        when(lectureService.joinOngoingLecture(anyInt(),anyString(), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/j/1/1")
                .content("{\"userName\": \"Martynas\"}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);


        assertEquals("{\"success\":true}", result.getContentAsString());
    }

    @Test
    public void joinOnGoingLecture_noInfo() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        //when(lectureService.createNewLecture(any(Date.class), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/j/1/1")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);


        assertEquals("{\"message\":\"Expected userName to be provided.\",\"success\":\"false\"}", result.getContentAsString());
    }

    @Test
    public void postFeedback_info() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        when(lectureService.postLectureFeedback(anyInt(),anyString(), anyInt())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/feedback")
                .content("{\n"
                        +
                        "    \"userId\": 1,\n"
                        +
                        "    \"lectureFeedbackCode\": 1\n"
                        +
                        "}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();


        assertEquals("{\"success\":true}", result.getContentAsString());
    }

    @Test
    public void postFeedback_noInfo() throws Exception {

        Map<String, Object> status = new TreeMap<>();
        status.put("success", true);

        //when(lectureService.createNewLecture(any(Date.class), anyString())).thenReturn(status);

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                .post("/lectures/1/feedback")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andReturn().getResponse();
        System.out.println(result);


        assertEquals("{\"message\":\"Expected userId and lectureFeedbackId"
                +
                " to be provided.\",\"success\":\"false\"}", result.getContentAsString());
    }

    /**
     * convert Stribg to JSON.
     * @param obj - string
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
