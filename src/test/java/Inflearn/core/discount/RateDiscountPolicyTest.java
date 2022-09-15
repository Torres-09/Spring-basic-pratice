package Inflearn.core.discount;

import Inflearn.core.member.Grade;
import Inflearn.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Component
class RateDiscountPolicyTest {
    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용된다.")
    public void vip_discount() throws Exception{
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        int discountPrice = discountPolicy.discount(member, 1000);
        //then
        assertThat(discountPrice).isEqualTo(100);

    }

    @Test
    @DisplayName("vip가 아니면 할인이 적용되면 안된다.")
    public void no_vip_discount() throws Exception{
        //given
        Member member = new Member(1L, "memberA", Grade.BASIC);

        //when
        int discountPrice = discountPolicy.discount(member, 1000);
        //then
        assertThat(discountPrice).isEqualTo(0);
    }
}