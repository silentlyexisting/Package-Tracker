package com.tracker.code;


import com.tracker.code.model.Parcel;
import com.tracker.code.repository.ParcelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class AppTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParcelRepository parcelRepository;

    private final static String baseUrl = "/api/v1.0/shipments/";
    private final String registrateParcelJson = "src/test/resources/fixtures/registrateParcel.json";
    private final String arriveAtTempOfficeJson = "src/test/resources/fixtures/arrivedAtTempPostOffice.json";

    private Parcel parcel;
    private String postParcel;
    private String patchParcel;


    @BeforeAll
    public void beforeAll() throws IOException {
        postParcel = Files.readString(Path.of(registrateParcelJson));
        patchParcel = Files.readString(Path.of(arriveAtTempOfficeJson));
        parcel = new Parcel();
        parcelRepository.save(parcel);
    }

    @Test
    void testCreateShipment() throws Exception {
        MockHttpServletResponse postResponse = mockMvc
                .perform(post(baseUrl + "registrate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postParcel)
                )
                .andReturn()
                .getResponse();

        assertThat(postResponse.getStatus()).isEqualTo(200);
        assertThat(postResponse.getContentAsString()).contains(
                "Your parcel has been successfully registered and will be sent soon.");

    }

    @Test
    void testParcelArrivedAtTempPostOffice() throws Exception {
        MockHttpServletResponse patchResponse = mockMvc
                .perform(patch(baseUrl + parcel.getId() + "/temp-office")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patchParcel)
                )
                .andReturn()
                .getResponse();

        assertThat(patchResponse.getStatus()).isEqualTo(200);
        assertThat(patchResponse.getContentAsString()).contains(
                "Your parcel has arrived at the new temporary post office.");

        MockHttpServletResponse getResponse = mockMvc
                .perform(get(baseUrl + parcel.getId()))
                .andReturn()
                .getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentAsString()).contains(
                "AT_TEMPORARY_POST_OFFICE",
                "3521 Michigan Avenue"
        );
    }

    @Test
    void testParcelLeftTempPostOffice() throws Exception {
        MockHttpServletResponse patchResponse = mockMvc
                .perform(patch(baseUrl + parcel.getId() + "/shipped"))
                .andReturn()
                .getResponse();

        assertThat(patchResponse.getStatus()).isEqualTo(200);
        assertThat(patchResponse.getContentAsString()).contains(
                "Your parcel has left the temporary post office");

        MockHttpServletResponse getResponse = mockMvc
                .perform(get(baseUrl + parcel.getId()))
                .andReturn()
                .getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentAsString()).contains("SHIPPED");
    }

    @Test
    void testParcelReceived() throws Exception {
        MockHttpServletResponse patchResponse = mockMvc
                .perform(patch(baseUrl + parcel.getId() + "/received"))
                .andReturn()
                .getResponse();

        assertThat(patchResponse.getStatus()).isEqualTo(200);
        assertThat(patchResponse.getContentAsString()).contains(
                "Parcel was successfully received by the recipient");

        MockHttpServletResponse getResponse = mockMvc
                .perform(get(baseUrl + parcel.getId()))
                .andReturn()
                .getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(200);
        assertThat(getResponse.getContentAsString()).contains("RECEIVED");
    }
}
