package bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @RequestMapping("/")
    public String getHomePage() {
        log.debug("Getting home page");
        return "home";
    }

}
