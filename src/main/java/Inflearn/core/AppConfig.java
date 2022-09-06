package Inflearn.core;

import Inflearn.core.member.MemberService;
import Inflearn.core.member.MemberServiceImpl;
import Inflearn.core.member.MemoryMemberRepository;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

}
