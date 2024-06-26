package com.cow.cow_mvc_practice.member.controller;

import java.util.List;

import com.cow.cow_mvc_practice.member.dto.request.UpdateMemberRequest;
import com.cow.cow_mvc_practice.member.dto.response.FoundMemberResponse;
import com.cow.cow_mvc_practice.member.dto.response.UpdatedMemberResponse;
import org.springframework.web.bind.annotation.*;
import com.cow.cow_mvc_practice.member.dto.request.CreateMemberRequest;
import com.cow.cow_mvc_practice.member.dto.response.CreatedMemberResponse;
import com.cow.cow_mvc_practice.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/new")
    public CreatedMemberResponse create(@RequestBody final CreateMemberRequest createMemberRequest) {
        return memberService.create(createMemberRequest);
    }

    @GetMapping("/{memberId}")
    public FoundMemberResponse find(@PathVariable final Long memberId) {
        return memberService.find(memberId);
    }

    @GetMapping("/all")
    public List<FoundMemberResponse> findAll() {
        return memberService.findAll();
    }

    @PatchMapping("/{memberId}")
    public UpdatedMemberResponse update(@PathVariable("memberId") Long memberId, @RequestBody final UpdateMemberRequest updateMemberRequest) {
        return memberService.update(memberId, updateMemberRequest);
    }
}

