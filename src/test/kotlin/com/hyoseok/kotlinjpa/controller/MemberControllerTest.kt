package com.hyoseok.kotlinjpa.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.hyoseok.kotlinjpa.controller.request.CreateMemberRequest
import com.hyoseok.kotlinjpa.service.MemberService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.mockito.BDDMockito.given
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [MemberController::class])
@DisplayName("CouponConditionAdminController 테스트")
internal class MemberControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var memberService: MemberService

    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    @Test
    @DisplayName("[POST] /api/v1/members")
    fun createMember() {
        // given
        val createMemberRequest = CreateMemberRequest()
        createMemberRequest.username = "hyoseok"
        createMemberRequest.email = "hyoseok@gmail.com"
        createMemberRequest.teamName = "teamA"

        given(
                memberService.createMember(
                        createMemberRequest.username,
                        createMemberRequest.email,
                        createMemberRequest.teamName
                )
        ).willReturn(1)

        // when
        val resultActions: ResultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createMemberRequest))
        ).andDo(MockMvcResultHandlers.print())

        // then
        resultActions
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.memberId").exists())
    }

}
