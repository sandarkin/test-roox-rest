package ru.sandarkin.roox;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class ApiDocumentation {

  private static final String KEVIN_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI5YmE1YzdiYi04MDQwLTQ0NTAtYTJmZS1lNjc3ZWI4MmZjNjMiLCJ1c2VybmFtZSI6ImtldmluIiwicm9sZXMiOlsidXNlciJdfQ.76ATYe4azE0Mle0Lx-5A2GHQxy4L2ctRfa1v-FJrfho";
  private static final String FILLER_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJmNTU5M2QyMi0xZmM3LTQ2MjItOTI1Ny1kMTViMjYxZjE1YTkiLCJ1c2VybmFtZSI6ImZ1bGxlciIsInJvbGVzIjpbInVzZXIiXX0.ITAXjAv1VgXKCar8V7d5wVn0YPqLF7Fg6ErxHB2--hw";
  private static final String MARLEY_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIyNWQ2YmNlYy0yZWU0LTQ1NjUtOTc5Ny01N2YxMmM4OWE5NzMiLCJ1c2VybmFtZSI6Im1hcmxleSIsInJvbGVzIjpbInVzZXIiLCJhZG1pbiJdfQ.huE3ia0JsxdlwTMR57oPPrONXklAMBlP19mt4B7_KSI";

  private static final String KEVIN_UUID = "9ba5c7bb-8040-4450-a2fe-e677eb82fc63";
  private static final String KEVIN_MAPPING_UUID = "356afa54-4434-4399-8b3d-c9fe82df530e";
  private static final String KEVIN_MAPPING_FOR_DELETE_UUID = "f7e1fa4c-c98c-11e6-9d9d-cec0c932ce01";

  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("build/generated-snippets");

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private FilterChainProxy springSecurityFilter;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(this.context)
        .addFilter(this.springSecurityFilter, "/*")
        .apply(documentationConfiguration(this.restDocumentation)).build();
  }

  @Test
  public void errorExample() throws Exception {
    this.mockMvc
        .perform(get("/error")
            .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
            .requestAttr(RequestDispatcher.ERROR_REQUEST_URI, "/customers")
            .requestAttr(RequestDispatcher.ERROR_MESSAGE,
                "The tag 'http://localhost:8080/partnerMappings/123' does not exist"))
        .andDo(print()).andExpect(status().isBadRequest())
        .andExpect(jsonPath("error", is("Bad Request")))
        .andExpect(jsonPath("timestamp", is(notNullValue())))
        .andExpect(jsonPath("status", is(400)))
        .andExpect(jsonPath("path", is(notNullValue())))
        .andDo(document("error-example",
            responseFields(
                fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
                fieldWithPath("message").description("A description of the cause of the error"),
                fieldWithPath("path").description("The path to which the request was made"),
                fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
                fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
  }

  @Test
  public void indexExample() throws Exception {
    this.mockMvc.perform(get("/").header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andDo(document("index-example",
            links(
                linkWithRel("customers").description("The <<resources-customers,Customers resource>>"),
                linkWithRel("partnerMappings").description("The <<resources-partnerMappings,PartnerMappings resource>>"),
                linkWithRel("profile").description("The ALPS profile for the service")),
            responseFields(
                fieldWithPath("_links").description("<<resources-index-links,Links>> to other resources"))));

  }

  @Test
  public void customersListExample() throws Exception {
    this.mockMvc.perform(get("/customers").header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andDo(document("customers-list-example",
            links(
                linkWithRel("self").description("Canonical link for this resource"),
                linkWithRel("profile").description("The ALPS profile for customers resource"),
                linkWithRel("search").description("Search variants for customers resource")
            ),
            responseFields(
                fieldWithPath("_embedded.customers").description("An array of <<resources-customer, Customer resources>>"),
                fieldWithPath("_links").description("<<resources-customers-list-links, Links>> to other resources"),
                fieldWithPath("page").description("Paging information"))));

  }

  @Test
  public void customerGetExample() throws Exception {
    this.mockMvc.perform(get("/customers/" + KEVIN_UUID)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(KEVIN_UUID)))
        .andExpect(jsonPath("username", is("kevin")))
        .andExpect(jsonPath("fullName", is("Kevin McCallister")))
        .andExpect(jsonPath("_links.self.href", containsString(KEVIN_UUID)))
        .andExpect(jsonPath("_links.partnerMappings", is(notNullValue())))
        .andDo(print())
        .andDo(document("customer-get-example",
            links(
                linkWithRel("self").description("Canonical link for this customer"),
                linkWithRel("customer").description("This customer"),
                linkWithRel("partnerMappings").description("This customer's mappings")),
            responseFields(
                fieldWithPath("id").description("Customer id"),
                fieldWithPath("fullName").description("Full name"),
                fieldWithPath("balance").description("Balance"),
                fieldWithPath("active").description("Activate status"),
                fieldWithPath("username").description(""),
                fieldWithPath("password").description(""),
                fieldWithPath("_links").description("<<resources-customer-links,Links>> to other resources"))));

  }

  @Test
  public void customerGetMeExample() throws Exception {
    this.mockMvc.perform(get("/customers/@me")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(KEVIN_UUID)))
        .andExpect(jsonPath("username", is("kevin")))
        .andExpect(jsonPath("fullName", is("Kevin McCallister")))
        .andExpect(jsonPath("_links.self.href", containsString(KEVIN_UUID)))
        .andExpect(jsonPath("_links.partnerMappings", is(notNullValue())))
        .andDo(print())
        .andDo(document("customer-get-me-example",
            links(
                linkWithRel("self").description("Canonical link for this customer"),
                linkWithRel("customer").description("This customer"),
                linkWithRel("partnerMappings").description("This customer's mappings")),
            responseFields(
                fieldWithPath("id").description("Customer id"),
                fieldWithPath("fullName").description("Full name"),
                fieldWithPath("balance").description("Balance"),
                fieldWithPath("active").description("Activate status"),
                fieldWithPath("username").description(""),
                fieldWithPath("password").description(""),
                fieldWithPath("_links").description("<<resources-customer-links,Links>> to other resources"))));

  }

  @Test
  public void mappingsListExample() throws Exception {
    this.mockMvc.perform(get("/customers/" + KEVIN_UUID + "/partnerMappings")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andDo(document("mappings-list-example",
            links(
                linkWithRel("self").description("Canonical link for this resource")
            ),
            responseFields(
                fieldWithPath("_embedded.partnerMappings").description("An array of <<resources-mappings, Partner Mappings resources>>"),
                fieldWithPath("_links").description("<<resources-mappings-list-links, Links>> to other resources"))));

  }

  @Test
  public void mappingsAvailableListExample() throws Exception {
    this.mockMvc.perform(get("/partnerMappings")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andDo(document("mappings-avail-list-example",
            links(
                linkWithRel("self").description("Canonical link for this resource"),
                linkWithRel("profile").description("The ALPS profile for customers resource"),
                linkWithRel("search").description("Search variants for customers resource")
            ),
            responseFields(
                fieldWithPath("_embedded.partnerMappings").description("An array of <<resources-mappings, Partner Mappings resources>>"),
                fieldWithPath("_links").description("<<resources-mappings-list-links, Links>> to other resources"),
                fieldWithPath("page").description("Paging information"))));

  }

  @Test
  public void mappingGetExample() throws Exception {
    this.mockMvc.perform(get("/partnerMappings/" + KEVIN_MAPPING_UUID)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isOk())
        .andExpect(jsonPath("id", is(KEVIN_MAPPING_UUID)))
        .andExpect(jsonPath("fullName", is("Kevin on FB")))
        .andExpect(jsonPath("accountId", is("100001006414253")))
        .andExpect(jsonPath("_links.self.href", containsString(KEVIN_MAPPING_UUID)))
        .andExpect(jsonPath("_links.customer", is(notNullValue())))
        .andExpect(jsonPath("_links.partnerMapping", is(notNullValue())))
        .andDo(print())
        .andDo(document("mapping-get-example",
            links(
                linkWithRel("self").description("Canonical link for this mapping"),
                linkWithRel("customer").description("The customer for this mapping"),
                linkWithRel("partnerMapping").description("This mapping")),
            responseFields(
                fieldWithPath("id").description("Partner Mapping id"),
                fieldWithPath("partnerId").description("Partner id"),
                fieldWithPath("accountId").description("Customer id for this mapping"),
                fieldWithPath("fullName").description("Customer's name for this mapping"),
                fieldWithPath("avatar").description("Base64 encoded customer avatar for this mapping in RFC 2397"),
                fieldWithPath("_links").description("<<resources-mapping-links,Links>> to other resources"))));

  }

  @Test
  public void mappingCreateExample() throws Exception {

    Map<String, Object> mapping = new HashMap<>();
    ///mapping.put("id", NEW_KEVIN_MAPPING_UUID);
    mapping.put("partnerId", "765276128768761");
    mapping.put("fullName", "Kevin on Google");
    mapping.put("accountId", "127651298792874");
    mapping.put("customer", "customers/" + KEVIN_UUID);
    mapping.put("avatar", "ZGF0YTppbWFnZS9wbmc7YmFzZTY0LGlWQk9SdzBLR2dvQUFBQU5TVWhFVWdBQUFCQUFBQUFRQ0FZQUFBQWY4LzloQUFBQUdYUkZXSFJUYjJaMGQyRnlaUUJCWkc5aVpTQkpiV0ZuWlZKbFlXUjVjY2xsUEFBQUF2TkpSRUZVZU5wOGsxMU1VbUVZeC85d1FFcklXYUVvS2pSdGdxSTFtOWtzemFHdHFadTY1U3pYcEp1MlBwYmQxTHpWelM3cjBubmZSYktwaVY1NTRZMDZxNm00UlY4aVJzZ01vb0dCZ2dKNmdKNzNaTXRxZGJZZmg3M24rZitmai9kOVJXMXRiUkNKUkJDTHhjS2J5Q0pLQUtpSURQeDR0b2l2cVZScW1mQVRTQ2FUWUc4SmZqMHlva3FyMVY3bzZPaTRYbGhZV003enZQQkJJcEhBNlhTK0hSa1pHWEs3M1M5b2FZR0lzMitjWHE5bldabjRja3RMUzVmSlpIckkyMndxMy9Bd3Znd09JakEyaHJEVENZVmNycXEvZHZVU1ZjbzVIQTRtZGhNSlRxZlRzVkpxV2x0YnU0eEc0dzFYWHgra2MzUFFVbFp0Ymk3eWxFb290cmF3T1RzTG4zVUo1YWF1MHh6SDhXVGlaeWJpUkNLaDBtZzB0UTBOOVNablh5OXlLRGhIcGNMMjlqWUNnWURBenM0T1ZMU20zTmpBV244LzZ1cnFUSGw1ZWJWTXl3d003ZTFYT2o5UFRTSEQ0NFdJQnVQMWVoRU1Cbi9ENS9OQnhBYmxXb04vWmhyTnpVMmRUQ3VoSDNWQlFZSEI4V3dJSnhRS2VEd2VZWEI3dTd0QzVoMnFaSmYrL3h5b09qOGZXTFFpOTg1dEE5TXlnOHh3T0lLb3pRWStLd3ZWTXpOQ0JhRlFDTlFyNUhJNUZHU2NscFltR0V4WFZDQkZzZEZvREV3cklXZldCbmphMStqbUp1eDJ1NUJOS3BYK0pSYTJqYXFKVWF5Z0lTMHpDSE9jQkZKREdXTFdSYmlOUnZ6dlNTY3pGZ3VJbVVHWVpmZXVybjYwS3lxckVJekZvSkhKb0dhOS9vTW90WlYrNWl4Y3JqVTcwektETjJhejJhS3NxVU84N0JRMktlQUlIV3NGQmY5Sm1OcEtVcUtqMVRXWW1MQlltSmJMenM2T0JJUGZwSURvOE1WN0Qwby92WHNELzdvYmNsYXVVQ2dKaVZWaSs5eDVsRHg2Z3ZIeDBlZno4eTh0ZEFCZmMvc1h3N084L0Y1TXN4RTM5dlRxY2JJWWJrNktKYzg2SEJJcCtQcEdxRy9lUmZHdCt6Q2JuNDZQamc1Wm90SG9KSjBQWHJTZjVKQk1KanRHRXpmcWRLVlYzZDA5VFpXVjFVVUhoMmUxdm5JT0REeWVYRm41c0JDSlJLYmo4ZmdHTGNlWUFVZGtFc2RacTdSOW1YVDdpdWpTWkIyOHpsU2xuNmJ1M052YkM3RnVpQUFSK2k3QUFLanllNDdGbkN4dUFBQUFBRWxGVGtTdVFtQ0M=");

    this.mockMvc.perform(
        post("/partnerMappings").contentType(MediaTypes.HAL_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT)
            .content(this.objectMapper.writeValueAsString(mapping))).andExpect(
        status().isCreated())
        .andDo(document("mapping-create-example",
            requestFields(
                ///fieldWithPath("id").description("Partner Mapping id"),
                fieldWithPath("partnerId").description("Partner id"),
                fieldWithPath("accountId").description("Customer id for this mapping"),
                fieldWithPath("fullName").description("Customer's name for this mapping"),
                fieldWithPath("customer").description("Link to customer"),
                fieldWithPath("avatar").description("Base64 encoded customer avatar for this mapping in RFC 2397")
            )));
  }

  @Test
  public void mappingUpdateExample() throws Exception {
    Map<String, Object> mapping = new HashMap<>();
    mapping.put("partnerId", "973065359403599");
    mapping.put("fullName", "Super Kevin on FB");
    mapping.put("accountId", "100001006414253");
    mapping.put("customer", "customers/" + KEVIN_UUID);
    mapping.put("avatar", "ZGF0YTppbWFnZS9wbmc7YmFzZTY0LGlWQk9SdzBLR2dvQUFBQU5TVWhFVWdBQUFCQUFBQUFRQ0FZQUFBQWY4LzloQUFBQUdYUkZXSFJUYjJaMGQyRnlaUUJCWkc5aVpTQkpiV0ZuWlZKbFlXUjVjY2xsUEFBQUF2TkpSRUZVZU5wOGsxMU1VbUVZeC85d1FFcklXYUVvS2pSdGdxSTFtOWtzemFHdHFadTY1U3pYcEp1MlBwYmQxTHpWelM3cjBubmZSYktwaVY1NTRZMDZxNm00UlY4aVJzZ01vb0dCZ2dKNmdKNzNaTXRxZGJZZmg3M24rZitmai9kOVJXMXRiUkNKUkJDTHhjS2J5Q0pLQUtpSURQeDR0b2l2cVZScW1mQVRTQ2FUWUc4SmZqMHlva3FyMVY3bzZPaTRYbGhZV003enZQQkJJcEhBNlhTK0hSa1pHWEs3M1M5b2FZR0lzMitjWHE5bldabjRja3RMUzVmSlpIckkyMndxMy9Bd3Znd09JakEyaHJEVENZVmNycXEvZHZVU1ZjbzVIQTRtZGhNSlRxZlRzVkpxV2x0YnU0eEc0dzFYWHgra2MzUFFVbFp0Ymk3eWxFb290cmF3T1RzTG4zVUo1YWF1MHh6SDhXVGlaeWJpUkNLaDBtZzB0UTBOOVNablh5OXlLRGhIcGNMMjlqWUNnWURBenM0T1ZMU20zTmpBV244LzZ1cnFUSGw1ZWJWTXl3d003ZTFYT2o5UFRTSEQ0NFdJQnVQMWVoRU1Cbi9ENS9OQnhBYmxXb04vWmhyTnpVMmRUQ3VoSDNWQlFZSEI4V3dJSnhRS2VEd2VZWEI3dTd0QzVoMnFaSmYrL3h5b09qOGZXTFFpOTg1dEE5TXlnOHh3T0lLb3pRWStLd3ZWTXpOQ0JhRlFDTlFyNUhJNUZHU2NscFltR0V4WFZDQkZzZEZvREV3cklXZldCbmphMStqbUp1eDJ1NUJOS3BYK0pSYTJqYXFKVWF5Z0lTMHpDSE9jQkZKREdXTFdSYmlOUnZ6dlNTY3pGZ3VJbVVHWVpmZXVybjYwS3lxckVJekZvSkhKb0dhOS9vTW90WlYrNWl4Y3JqVTcwektETjJhejJhS3NxVU84N0JRMktlQUlIV3NGQmY5Sm1OcEtVcUtqMVRXWW1MQlltSmJMenM2T0JJUGZwSURvOE1WN0Qwby92WHNELzdvYmNsYXVVQ2dKaVZWaSs5eDVsRHg2Z3ZIeDBlZno4eTh0ZEFCZmMvc1h3N084L0Y1TXN4RTM5dlRxY2JJWWJrNktKYzg2SEJJcCtQcEdxRy9lUmZHdCt6Q2JuNDZQamc1Wm90SG9KSjBQWHJTZjVKQk1KanRHRXpmcWRLVlYzZDA5VFpXVjFVVUhoMmUxdm5JT0REeWVYRm41c0JDSlJLYmo4ZmdHTGNlWUFVZGtFc2RacTdSOW1YVDdpdWpTWkIyOHpsU2xuNmJ1M052YkM3RnVpQUFSK2k3QUFLanllNDdGbkN4dUFBQUFBRWxGVGtTdVFtQ0M=");

    this.mockMvc.perform(
        put("/partnerMappings/" + KEVIN_MAPPING_UUID).contentType(MediaTypes.HAL_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT)
            .content(this.objectMapper.writeValueAsString(mapping))).andExpect(
        status().isNoContent())
        .andDo(document("mapping-update-example",
            requestFields(
                fieldWithPath("partnerId").description("Partner id"),
                fieldWithPath("accountId").description("Customer id for this mapping"),
                fieldWithPath("fullName").description("Customer's name for this mapping"),
                fieldWithPath("customer").description("Link to customer"),
                fieldWithPath("avatar").description("Base64 encoded customer avatar for this mapping in RFC 2397")
            )));
  }

  @Test
  public void ZorderMappingDeleteExample() throws Exception {
    this.mockMvc.perform(
        delete("/partnerMappings/" + KEVIN_MAPPING_FOR_DELETE_UUID).contentType(MediaTypes.HAL_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + KEVIN_JWT))
        .andExpect(status().isNoContent())
        .andDo(document("mapping-delete-example"));
  }

}
