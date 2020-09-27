package bot.controller;

import bot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @RequestMapping("/users")
    public ModelAndView getUsersPage() {
        log.debug("Getting users page");
        return new ModelAndView("users", "users", userService.getAllUsers());
    }
}
