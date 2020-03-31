package com.lrm.web.admin;

import com.lrm.config.GroupEnum;
import com.lrm.config.UserTypeEnum;
import com.lrm.po.Group;
import com.lrm.po.User;
import com.lrm.service.GroupService;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jianengxi on 2020/2/21
 */
@Controller
@RequestMapping("/admin")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @GetMapping("/users")
    public String list(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC)
                       Pageable pageable,
                       Model model,
                       HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        Integer type = currentUser.getType();

        List<Integer> types = new ArrayList<Integer>();

        if (type.equals(UserTypeEnum.ADMIN.getUserType())) {//系统管理员
            model.addAttribute("page", userService.listUser(pageable));
            model.addAttribute("currentUser", currentUser);
            return "admin/users";
        }
        if (type.equals(UserTypeEnum.MANAGER.getUserType())) {//经理用户
            types.add(UserTypeEnum.OUTSOURCING.getUserType());
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", userService.getUserByType(types, pageable));
            return "admin/users";
        }
        if (type.equals(UserTypeEnum.GROUPLEADER.getUserType())) {//组长用户
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", userService.getUserByGroup(currentUser.getGroup().getId(), pageable));
            return "admin/users";
        }
        else {//外包用户
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("page", userService.getUserById(currentUser.getId(), pageable));
            return "admin/users";
        }
    }

    @GetMapping("/user/{id}")
    public String editInput(@PathVariable Long id,
                            Model model,
                            HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        User user = userService.getUser(id);
        model.addAttribute("groups", groupService.listGroup());

        if (user == null) {
            return "redirect:/admin/users";
        }

        if (!currentUser.getType().equals(UserTypeEnum.ADMIN.getUserType())) {
            if (!currentUser.getId().equals(id)) {
                return "redirect:/admin/users";
            }
        }

        if (!currentUser.getType().equals(UserTypeEnum.MANAGER.getUserType()) &&
                !currentUser.getId().equals(user.getId())) {
            return "redirect:/admin/users";
        } else if(currentUser.getType().equals(UserTypeEnum.MANAGER.getUserType())) {
            if (!user.getType().equals(UserTypeEnum.OUTSOURCING.getUserType())) {
                return "redirect:/admin/users";
            }
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);

        return "admin/user-input";
    }

    @PostMapping("/user/{id}")
    public String post(@Valid User user,
                       BindingResult result,
                       Model model,
                       @PathVariable Long id,
                       RedirectAttributes attributes) {
        User user1 = userService.getUser(id);
        model.addAttribute("groups", groupService.listGroup());

        if (user1 == null) {
            return "redirect:/admin/users";
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            result.rejectValue("username", "usernameError", "用户名不能为空");
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
            logger.info(result.getAllErrors().toString());
            return "admin/user-input";
        }

        user.setCreateTime(user1.getCreateTime());
        user.setUpdateTime(new Date());
        user.setId(id);
        user.setPassword(MD5Utils.code(user.getPassword()));
        user.setAvatar(user1.getAvatar());
        User user2 = userService.updateUser(user1.getId(), user);
        if (user2 == null) {
            attributes.addFlashAttribute("message", "更新用户失败");
        } else {
            attributes.addFlashAttribute("message", "更新用户成功");
        }

        return "redirect:/admin/users"; 
    }

}
