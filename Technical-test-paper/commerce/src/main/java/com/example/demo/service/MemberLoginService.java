package com.example.demo.service;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberLoginService {

    @Autowired
    private MemberRepository memberRepository;

    // 註冊
    public Member register(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 自動生成 memberId
        Optional<Member> lastMember = memberRepository.findTopByOrderByMemberIdDesc();
        String lastmemberId = lastMember.map(Member::getMemberId).orElse("000000000");
        int nextNo = Integer.parseInt(lastmemberId.substring(1)) + 1;
        member.setMemberId(String.format("%04d", nextNo));



        return memberRepository.save(member);
    }

    // 登入
    public Member login(String username, String password) {
        Optional<Member> optMember = memberRepository.findByUsername(username);
        if (optMember.isPresent()) {
            Member member = optMember.get();



            if (member.getPassword().equals(password)) {
                return member;
            }
        }
        throw new RuntimeException("Invalid username or password");
    }

    // 變更密碼
    public Member changePassword(String username, String oldPassword, String newPassword) {
        Optional<Member> optMember = memberRepository.findByUsername(username);
        if (optMember.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Member member = optMember.get();

        if (!member.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Old password is incorrect");
        }

        member.setPassword(newPassword);
        return memberRepository.save(member);
    }
}
