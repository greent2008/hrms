package com.lrm.web.admin;

import com.lrm.config.UserTypeEnum;
import com.lrm.po.Group;
import com.lrm.po.Info;
import com.lrm.po.User;
import com.lrm.service.GroupService;
import com.lrm.service.InfoService;
import com.lrm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;

/**
 * Created by jianengxi on 2020/2/24
 */
@Controller
@RequestMapping("/admin")
public class InfoController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    InfoService infoService;

    @Autowired
    UserService userService;

    @Autowired
    GroupService groupService;

    @GetMapping("/info/{id}")
    public String editInput(@PathVariable Long id, //此id为userId
                            Model model,
                            HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        Group currentGroup = currentUser.getGroup();
        User user = userService.getUser(id);
        Info info = infoService.getInfoByUser(id);

        if (user == null) {
            return "redirect:/admin/users";
        }
        
        if (currentUser.getType().equals(UserTypeEnum.OUTSOURCING.getUserType()) &&
                !currentUser.getId().equals(user.getId())) {
            return "redirect:/admin/users";
        } else if (currentUser.getType().equals(UserTypeEnum.GROUPLEADER.getUserType())) {
            if (!currentGroup.getId().equals(user.getGroup().getId()) ||
                    currentUser.getId().equals(user.getId())) {
                return "redirect:/admin/users";
            }
        } else if(currentUser.getType().equals(UserTypeEnum.MANAGER.getUserType())) {
            if (!user.getType().equals(UserTypeEnum.OUTSOURCING.getUserType())) {
                return "redirect:/admin/users";
            }
        }

        if (info == null) {
            info = new Info();
            info.setUser(user);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("info", info);
        model.addAttribute("groups", groupService.listGroup());
        return "admin/info-input";
    }

    @PostMapping("/info/{id}")
    public String post(@Valid Info info,
                       BindingResult result,
                       Model model,
                       @PathVariable Long id,
                       RedirectAttributes attributes) {
        User user = userService.getUser(id);
        Info info1 = infoService.getInfoByUser(id);
        model.addAttribute("groups", groupService.listGroup());
        if (info.getName() == null || info.getName().isEmpty()) {
            result.rejectValue("name", "nameError", "用户信息名不能为空");
        }
        if (info.getEmail() == null || info.getEmail().isEmpty()) {
            result.rejectValue("email", "emailError", "用户信息邮箱名不能为空");
        }
        if (result.hasErrors()) {
            return "admin/info-input";
        }

        info.setUser(user);
        if (info1 == null) {
            info.setCreateTime(new Date());
            info.setUpdateTime(new Date());
            Info info2 = infoService.saveInfo(info);
            if (info2 == null) {
                attributes.addFlashAttribute("message", "提交用户信息失败");
            } else {
                attributes.addFlashAttribute("message", "提交用户信息成功");
            }
        } else {
            info.setCreateTime(info1.getCreateTime());
            info.setUpdateTime(new Date());
            info.setId(info1.getId());
            Info info2 = infoService.updateInfo(info1.getId(), info);
            if (info2 == null) {
                attributes.addFlashAttribute("message", "更新用户信息失败");
            } else {
                attributes.addFlashAttribute("message", "更新用户信息成功");
            }
        }
        user.setUsernameCN(info.getName());
        user.setEmail(info.getEmail());
        user.setGroup(groupService.getGroupByName(info.getGroupName()));
        userService.updateUser(user.getId(), user);
        return "redirect:/admin/users";
    }
}
