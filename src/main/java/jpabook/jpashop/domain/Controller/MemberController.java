package jpabook.jpashop.domain.Controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping("/members/new") //member/new로 오면 이 컨트롤러를 타서 createMemberForm이 열리고 랜더링된다.
  public String createForm(Model model){
    model.addAttribute("memberForm",new MemberForm()); //memberForm이라는 껍데기 객체를 가지고 view로 넘어간다.
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create (@Valid MemberForm form, BindingResult result){ //BindingResult하면 result에 오류가 담겨서 코드가 실행이된다.
    // Entity를 바로 안쓰고 MemberForm을 사용하는 이유 -> 엔티티랑 필드가 딱 맞지 않는다. + @Valid같은 기능을 쓰기 애매하다.
    // DTO같은 기능?
    //@Valid는 이 어노테이션이 붙어있는 (여기서는 MemberForm의 Name의 @Notempty)를 끌어온다.

    if(result.hasErrors()){ // 만약 오류가 발생하면 (ex. 이름 입력x) - > 다시 createMemberForm으로 보낸다.
      return "members/createMemberForm"; //그러면 여기서 오류메시지를 띄운다. -> hasErrors(name)에 따라 메시지 출력.
      // 여기서는 예시로 이름 입력 x시 MemberForm 파일에 있는 NotEmpty의 message가 출력된다.
    }
    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
    Member member = new Member();
    member.setName(form.getName());
    member.setAddress(address);

    memberService.join(member); //저장 끝

    return "redirect:/"; // 저장 끝나고 어디로 갈래? -> redirect
  }
}
