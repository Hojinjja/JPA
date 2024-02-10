package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 데이터 변경시 꼭 있어야함. -> 클래스 레벨에서 하면 그 아래 public들은 모두 transaction을 거친다.
@RequiredArgsConstructor // final만 있는 필드만 가지고 생성자를 만들어줌 (lombok)
                        // 현재 final을 가지고 있는 필드는 memberRepository 하나인데, 스프링에서는 생성자가 하나인 경우 자동으로 @Autowired 시켜줌 . ->@Autowired 안붙여도 된다. (자동으로 의존성 주입)
public class MemberService {

  private final MemberRepository memberRepository;


  //회원가입

  @Transactional(readOnly = false) // Transactional은 readOnly가 기본값
  public Long join(Member member) {
    validateDuplicateMember(member); //중복회원검증
    memberRepository.save(member);
    return member.getId(); //아이디를 반환
  }

  private void validateDuplicateMember(Member member) {
    List<Member> findMembers = memberRepository.findByName(member.getName()); //이름으로 조회 (리스트)\
    //EXCEPTION
    if (!findMembers.isEmpty()) {// 만약 findMembers(이름으로 조회한 내역이 존재하면
      throw new IllegalStateException("이미 존재하는 회원입니다.");

    }
  }

    //회원 전체 조회
  @Transactional(readOnly = true) // 읽는 메서드(쓰는 메서드에서는 x)에서는 readOnly로 하면 더 최적화된다.
    public List<Member> findMembers(){
      return memberRepository.findAll();
    }

  @Transactional(readOnly = true)
    public Member findOne(Long memberId){
    return memberRepository.findOne(memberId);
    }
  }
