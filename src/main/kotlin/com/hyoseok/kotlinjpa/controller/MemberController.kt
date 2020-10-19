package com.hyoseok.kotlinjpa.controller

import com.hyoseok.kotlinjpa.controller.request.CreateMemberRequest
import com.hyoseok.kotlinjpa.controller.response.CreateMemberResponse
import com.hyoseok.kotlinjpa.controller.response.FindMemberResponse
import com.hyoseok.kotlinjpa.entity.Member
import com.hyoseok.kotlinjpa.service.MemberService
import com.hyoseok.kotlinjpa.service.dto.FindMemberDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/members")
class MemberController (
        private val memberService: MemberService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createMember(@Valid @RequestBody request: CreateMemberRequest): CreateMemberResponse {
        val memberId: Long = memberService.createMember(request.username!!, request.email!!, request.teamName!!)
        return CreateMemberResponse(memberId)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun findMember(@PathVariable("id") id: Long): FindMemberResponse {
        val memberDto: FindMemberDto = memberService.findMember(id)!!
        return FindMemberResponse(id, memberDto.username, memberDto.email, memberDto.teamName)
    }

}