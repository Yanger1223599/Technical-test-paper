package com.example.demo.service;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    // 註冊
    public Member register(Member member) {
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }

        // 自動生成 memberId
        Optional<Member> lastMember = memberRepository.findTopByOrderByMemberIdDesc();
        String lastmemberId = lastMember.map(Member::getMemberId).orElse("D000");
        int nextNo = Integer.parseInt(lastmemberId.substring(1)) + 1;
        member.setMemberId(String.format("E%04d", nextNo));



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
    
    public Optional<Member> getBymemberId(String memberId) { 
        return memberRepository.findByMemberId(memberId);
    }
    
    /** 前端只需要 memberId + username 就好 */
    public Optional<MemberDto> getSimpleByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                   .map(m -> new MemberDto(m.getMemberId(), m.getUsername()));
    }

    public record MemberDto(String memberId, String username) {}
    
    
    /** 列出所有會員 */
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    /** 修改會員資料 */
    public Optional<Member> updateMember(Long id, Member updatedMember) {
        return memberRepository.findById(id).map(member -> {
            member.setUsername(updatedMember.getUsername());
            member.setPassword(updatedMember.getPassword());
            member.setName(updatedMember.getName());
            return memberRepository.save(member);
        });
    }

    /** 刪除會員 */
    public boolean deleteMember(Long id) {
        if (memberRepository.existsById(id)) {
            memberRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
    
    
        
    

}
