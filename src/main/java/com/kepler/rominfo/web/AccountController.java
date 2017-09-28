package com.kepler.rominfo.web;

import com.kepler.rominfo.domain.logic.BusinessMessageAndCode;
import com.kepler.rominfo.domain.vo.User;
import com.kepler.rominfo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@Api(value = "Account Api")
@Controller
public class AccountController {

    private static final Log LOGGER = LogFactory.getLog(AccountController.class);

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

    @ApiOperation(value = "Redirect to New Account View")
    @GetMapping(value = "/newAccount")
    public String newAccount() {
        return "newAccount";
    }

    @ApiOperation(value = "Redirect to Access Denied View")
    @RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
    public String accessDenied() {
        return "accessDenied";
    }

    @ApiOperation(value = "Add New Account")
    @PostMapping(value = "/addNewAccount")
    public @ResponseBody
    BusinessMessageAndCode addNewAccount(@RequestBody Map<String, Object> params, Locale locale) {
        String result = null;
        int code;

        String firstName = (String) params.get("firstName");
        String lastName = (String) params.get("lastName");
        String ssn = (String) params.get("ssn");
        String email = (String) params.get("email");
        String password = (String) params.get("password");
        String role = (String) params.get("role");

        User user = userService.findUser(email);
        if (user == null) {
            userService.addUser(firstName, lastName, Long.parseLong(ssn), email, password, role);
            LOGGER.info("register successful for " + email);
            result = messageSource.getMessage("newAccount.success", null, locale);
            code = BusinessMessageAndCode.SUCCESS;
        } else {
            LOGGER.info("register failed for " + email);
            result = messageSource.getMessage("newAccount.error3", null, locale);
            code = BusinessMessageAndCode.ERROR;
        }

        return new BusinessMessageAndCode(result, code);
    }
}
