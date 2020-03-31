package com.lrm.web.admin;

import com.lrm.config.UserTypeEnum;
import com.lrm.po.Group;
import com.lrm.po.Resume;
import com.lrm.po.User;
import com.lrm.service.ResumeService;
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
public class ResumeController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ResumeService resumeService;

    @Autowired
    UserService userService;

    @GetMapping("/resume/{id}")
    public String editInput(@PathVariable Long id, //此id为userId
                            Model model,
                            HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        Group currentGroup = currentUser.getGroup();
        User user = userService.getUser(id);
        Resume resume = resumeService.getResumeByUser(id);

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

        if (resume == null) {
            resume = new Resume();
            resume.setUser(user);
        }
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("resume", resume);
        return "admin/resume-input";
    }

    @PostMapping("/resume/{id}")
    public String post(@Valid Resume resume,
                       Model model,
                       BindingResult result,
                       @PathVariable Long id,
                       RedirectAttributes attributes) {
        User user = userService.getUser(id);
        Resume resume1 = resumeService.getResumeByUser(id);
        if (resume.getName() == null || resume.getName().isEmpty()) {
            result.rejectValue("name", "nameError", "用户简历名不能为空");
        }
        if (user.getEmail() == null || resume.getEmail().isEmpty()) {
            result.rejectValue("email", "emailError", "用户简历邮箱名不能为空");
        }
        if (result.hasErrors()) {
            return "admin/resume-input";
        }

        resume.setUser(user);
        if (resume1 == null) {
            resume.setCreateTime(new Date());
            resume.setUpdateTime(new Date());
            Resume resume2 = resumeService.saveResume(resume);
            if (resume2 == null) {
                attributes.addFlashAttribute("message", "提交用户简历失败");
            } else {
                attributes.addFlashAttribute("message", "提交用户简历成功");
            }
        } else {
            resume.setCreateTime(resume1.getCreateTime());
            resume.setUpdateTime(new Date());
            resume.setId(resume1.getId());
            Resume resume2 = resumeService.updateResume(resume1.getId(), resume);
            if (resume2 == null) {
                attributes.addFlashAttribute("message", "更新用户简历失败");
            } else {
                attributes.addFlashAttribute("message", "更新用户简历成功");
            }
        }
        return "redirect:/admin/users";
    }
}
