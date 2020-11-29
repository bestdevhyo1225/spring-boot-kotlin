package com.hyoseok.kotlinjpa.controller

import com.hyoseok.kotlinjpa.controller.request.CreateMemberRequest
import com.hyoseok.kotlinjpa.controller.response.CreateMemberResponse
import com.hyoseok.kotlinjpa.controller.response.FindMemberResponse
import com.hyoseok.kotlinjpa.controller.response.FindMembersResponse
import com.hyoseok.kotlinjpa.service.MemberService
import com.hyoseok.kotlinjpa.service.dto.FindMemberDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/api/v1/members"])
class MemberController (
        private val memberService: MemberService
) {

    @PostMapping
    fun createMember(@Valid @RequestBody request: CreateMemberRequest): ResponseEntity<CreateMemberResponse> {
        val memberId: Long = memberService.createMember(request.username, request.email, request.teamName)

        return ResponseEntity
                .created(URI.create("/api/v1/members/$memberId"))
                .body(CreateMemberResponse(memberId))
    }

    @GetMapping(value = ["/{id}"])
    fun findMember(@PathVariable("id") id: Long): ResponseEntity<FindMemberResponse> {
        val memberDto: FindMemberDto = memberService.findMember(id)

        return ResponseEntity.ok(FindMemberResponse(id, memberDto.username, memberDto.email, memberDto.teamName))
    }

    @GetMapping(value = ["no-offset"])
    fun findMembersByNoOffset(@RequestParam("lastMemberId", required = false, defaultValue = "0") id: Long,
                              @RequestParam("pageSize") pageSize: Long): ResponseEntity<FindMembersResponse> {
        val memberDtos: List<FindMemberDto> = memberService.findMembersByNoOffset(id, pageSize)

        return ResponseEntity.ok(FindMembersResponse(memberDtos))
    }

    @GetMapping("covering-index")
    fun findMembersByConveringIndex(@RequestParam("pageNo", defaultValue = "1") pageNo: Long,
                                    @RequestParam("pageSize") pageSize: Long): ResponseEntity<FindMembersResponse> {

        val memberDtos: List<FindMemberDto> = memberService.findMembersByConveringIndex(
                if (pageNo > 0) { pageNo } else { 1 },
                pageSize
        )

        return ResponseEntity.ok(FindMembersResponse(memberDtos))
    }

}
