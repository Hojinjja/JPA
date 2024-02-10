package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model){
    model.addAttribute("data","hello"); //name이 data인 키에 hello라는 값 담기 , templates에 있는 hello.html 파일에 data에 hello가 들어감
    return "hello"; // templates에 있는 hello.html로 감
  }
}
