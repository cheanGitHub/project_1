package com.cc.user.controller;

import com.cc.user.domain.User;
import com.cc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
//@Transactional()
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping(value = "/get-by-id")
    public String getById(@RequestParam("id") Long id, Model model) {
        // System.out.println("\n\nTestController\n\n");
        ModelAndView mav = new ModelAndView("test");
        try {
//        mav.addAllObjects(map);
//        Model model = null;
//        model.addAllAttributes(map);
//        ModelMap modelMap = null;
//        modelMap.addAllAttributes(map);

            User user = userService.getUserById(id);
//            mav.addObject("user", user);
//            model.addAttribute("user", user);
            return user.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return mav;
        return null;
    }

    @ResponseBody
    @GetMapping(value = "/insert")
    public String handle(@RequestParam("userName") String userName, Model model) {
        try {
            User user = new User().setUserName(userName);
            userService.insertUser(user);
            return user.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
