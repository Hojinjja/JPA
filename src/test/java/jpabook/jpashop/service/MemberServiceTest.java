package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 이게 있어야 롤백이 된다 (테스트 케이스에서만 롤백 / service,repository에서는 롤백 x)
public class MemberServiceTest {

  @Autowired MemberRepository memberRepository;
  @Autowired MemberService memberService;

  @Test
  //@Rollback(value = false) -> DB에 반영된것을 확인하고 싶은경우.
  public void 회원가입() throws Exception{
    //given
    Member member = new Member();
    member.setName("kim");

    //when
    Long saveId = memberService.join(member);

    //then
    assertEquals(member, memberRepository.findOne(saveId));
  }

  @Test(expected = IllegalStateException.class) // -> IllegalStateException이 뜨는것을 기대함.
  public void 중복회원예제() throws Exception{
    //given
    Member member1 = new Member();
    member1.setName("kim");

    Member member2 = new Member();
    member2.setName("kim");
    //when
    memberService.join(member1);
    memberService.join(member2); //예외가 발생해야 한다!

    //then
    fail("예외가 발생해야 한다. "); //fail문 -> 여기까지 오면 테스트 실패다. (테스트 코드를 잘못짠것
  }

}