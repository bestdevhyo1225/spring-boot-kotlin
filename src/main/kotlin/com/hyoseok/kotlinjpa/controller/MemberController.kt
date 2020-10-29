package com.hyoseok.kotlinjpa.controller

import com.hyoseok.kotlinjpa.controller.request.CreateMemberRequest
import com.hyoseok.kotlinjpa.controller.response.CreateMemberResponse
import com.hyoseok.kotlinjpa.controller.response.FindMemberResponse
import com.hyoseok.kotlinjpa.controller.response.FindMembersResponse
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

    @GetMapping("no-offset")
    @ResponseStatus(HttpStatus.OK)
    fun findMembersByNoOffset(@RequestParam("lastMemberId", required = false, defaultValue = "0") id: Long,
                              @RequestParam("pageSize") pageSize: Long): FindMembersResponse {
        val memberDtos: List<FindMemberDto> = memberService.findMembersByNoOffset(id, pageSize)

        return FindMembersResponse(memberDtos)
    }

    @GetMapping("covering-index")
    @ResponseStatus(HttpStatus.OK)
    fun findMembersByConveringIndex(@RequestParam("pageNo", defaultValue = "1") pageNo: Long,
                                    @RequestParam("pageSize") pageSize: Long): FindMembersResponse {

        val memberDtos: List<FindMemberDto> = memberService.findMembersByConveringIndex(
                if (pageNo > 0) { pageNo } else { 1 },
                pageSize
        )

        return FindMembersResponse(memberDtos)
    }

}
