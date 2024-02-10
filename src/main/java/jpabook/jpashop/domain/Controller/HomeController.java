package jpabook.jpashop.domain.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j   //log 찍게 도와주는 어노테이션
public class HomeController {

  @RequestMapping("/")
  public String home(){
    log.info("home controller");
    return "home"; // home.html로 찾아감
  }
}
