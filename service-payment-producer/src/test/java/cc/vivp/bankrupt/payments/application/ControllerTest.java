package cc.vivp.bankrupt.payments.application;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
@DisplayName("Payments API")
@Disabled
public class ControllerTest {

  @Value("${payments.base-path}")
  private String basePath;

  @Autowired
  private MockMvc mvc;

  @Test
  @DisplayName("should initiate payment given valid input")
  void shouldInitiatePaymentGivenValidInput() throws Exception {

    mvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(
        "{ \"amount\": 10, \"creditorName\": \"Beneficiary\", \"debtorIban\": \"NL80ABNA0419499482\", \"creditorIban\": \"NL01ABNA0123456789\" }"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(notNullValue()))
        .andExpect(jsonPath("$.iban").value(equalTo("NL80ABNA0419499482")));
  }

  @Test
  @DisplayName("should not initiate payment given invalid input")
  void shouldNotInitiatePaymentGivenInvalidInput() throws Exception {

    mvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(
        "{ \"amount\": 10, \"creditorName\": \"Beneficiary!\", \"debtorIban\": \"NL80ABNA0419499482\", \"creditorIban\": \"NL01ABNA0123456789\" }"))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("should not initiate payment given malformed JSON")
  void shouldNotInitiatePaymentGivenMalformedJson() throws Exception {

    mvc.perform(post(basePath).contentType(MediaType.APPLICATION_JSON).content(
        "{ \"amount\": 10, \"creditorName\": \"Beneficiary\", \"debtorIban\": \"NL80ABNA0419499482\", \"creditorIban\": \"NL01ABNA0123456789\", }"))
        .andExpect(status().is4xxClientError());
  }

}
