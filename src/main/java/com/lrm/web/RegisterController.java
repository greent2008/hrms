package com.lrm.web;

import com.lrm.config.GroupEnum;
import com.lrm.config.UserTypeEnum;
import com.lrm.po.User;
import com.lrm.service.GroupService;
import com.lrm.service.UserService;
import com.lrm.util.AvatarHelper;
import com.lrm.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

/**
 * Created by jianengxi on 2020/3/10
 */
@Controller
@RequestMapping("/")
public class RegisterController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/register")
    public String register(Model model,
                           HttpSession session) {
        User user = new User();
        model.addAttribute("groups", groupService.listGroup());
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String post(@Valid User user,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes
                       ) throws IOException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            result.rejectValue("username", "usernameError", "用户名不能为空");
        }
        if (userService.getUserByName(user.getUsername()) != null) {
            result.rejectValue("username", "usernameError", "重复的用户名");
        }
        if (user.getUsernameCN() == null || user.getUsernameCN().isEmpty()) {
            result.rejectValue("usernameCN", "usernameCNError", "用户中文名不能为空");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            result.rejectValue("password", "passwordError", "用户密码不能为空");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            result.rejectValue("email", "emailError", "用户邮箱不能为空");
        }
        if (user.getType() == null) {
            result.rejectValue("type", "typeError", "用户类型不能为空");
        }
        if (user.getGroup() == null) {
            result.rejectValue("group", "groupError", "用户组不能为空");
        }

        if (user.getType().equals(UserTypeEnum.ADMIN.getUserType()) &&
                !user.getGroup().getGroupName().equals(GroupEnum.SUPER.getGroupName())) {
            result.rejectValue("group", "groupError", "超级管理员只属于超级用户组");
        }
        if (user.getType().equals(UserTypeEnum.MANAGER.getUserType()) &&
                !user.getGroup().getGroupName().equals(GroupEnum.MANAGER.getGroupName())) {
            result.rejectValue("group", "groupError", "经理只属于经理用户组");
        }
        if (user.getType().equals(UserTypeEnum.GROUPLEADER.getUserType()) &&
                (user.getGroup().getGroupName().equals(GroupEnum.SUPER.getGroupName())
                        || user.getGroup().getGroupName().equals(GroupEnum.MANAGER.getGroupName()))) {
            result.rejectValue("group", "groupError", "组长只属于其他组");
        }
        if (user.getType().equals(UserTypeEnum.OUTSOURCING.getUserType()) &&
                (user.getGroup().getGroupName().equals(GroupEnum.SUPER.getGroupName()) ||
                        user.getGroup().getGroupName().equals(GroupEnum.MANAGER.getGroupName()))) {
            result.rejectValue("group", "groupError", "外包员工用户不属于其他组");
        }

        if (result.hasErrors()) {
            return "register";
        }

        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setAvatar(AvatarHelper.BASE64_PREFIX + AvatarHelper.createBase64Avatar(Math.abs(user.getUsername().hashCode())));
        User user1 = userService.saveUser(user);
        if (user1 == null) {
            attributes.addFlashAttribute("register_failed", "用户注册失败");
        } else {
            attributes.addFlashAttribute("register_success", "用户注册成功");
        }

        return "redirect:/";
    }

}
