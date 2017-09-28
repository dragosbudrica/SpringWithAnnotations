package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.dto.LoginDto;
import com.kepler.rominfo.domain.vo.User;
import com.kepler.rominfo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Api(value = "Login Api")
@Controller
public class LoginController {

    private static final Log LOGGER = LogFactory.getLog(LoginController.class);

    private UserService userService;

    private MessageSource messageSource;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ApiOperation(value = "Get login page")
    @GetMapping(value="/login")
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @ApiOperation(value = "Send credentials")
    @PostMapping(value = "/doLogin")
    public ModelAndView loginProcess(HttpSession session,
                                     @ModelAttribute("loginDto") LoginDto loginDto, Locale locale) {
        ModelAndView mav = null;
        User user = userService.findUser(loginDto.getEmail());
        String message;
        if (user != null) {
            if (userService.checkCredentials(user, loginDto.getEmail(), loginDto.getPassword())) {
                session.setAttribute("user", user);
                switch (user.getRole().getRoleName()) {
                    case "Admin":
                        mav = new ModelAndView("redirect:/newAccount");
                        break;
                    case "Professor":
                        mav = new ModelAndView("redirect:/professorCourses");
                        break;
                    default:
                        mav = new ModelAndView("redirect:/allCourses");
                        break;
                }
                LOGGER.info(mav);
            }
            else {
                message = messageSource.getMessage("login.error2", null, locale);
                LOGGER.info("login failed for " + loginDto.getEmail() + ". Reason: Wrong password!");
                mav = new ModelAndView("login");
                mav.addObject("message", message);
            }
        } else {
            message = messageSource.getMessage("login.error1", null, locale);
            LOGGER.info("LoginDto failed for " + loginDto.getEmail() + ". Reason: That user does not exists!");
            mav = new ModelAndView("login");
            mav.addObject("message", message);
        }

        return mav;
    }

    @ApiOperation(value = "Logout Action")
    @PostMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("redirect:/login");
        LOGGER.debug("invalidating session for " + ((User) session.getAttribute("user")).getEmail());
        session.invalidate();
        return mav;
    }
}
