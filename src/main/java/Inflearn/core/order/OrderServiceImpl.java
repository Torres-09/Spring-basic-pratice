package Inflearn.core.order;

import Inflearn.core.discount.DiscountPolicy;
import Inflearn.core.discount.FixDiscountPolicy;
import Inflearn.core.discount.RateDiscountPolicy;
import Inflearn.core.member.Member;
import Inflearn.core.member.MemberRepository;
import Inflearn.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private DiscountPolicy discountPolicy;

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
