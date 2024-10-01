package com.systex.hw4.controller;

import com.systex.hw4.model.Member;
import com.systex.hw4.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.Optional;

@Controller
public class MemberController {

    private final MemberRepository memberRepo;

    public MemberController(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @GetMapping("/login")
    public ModelAndView loginView() {
        return new ModelAndView("login", "command", new Member());
    }

    @PostMapping("/login")
    public ModelAndView loginAction(@Valid @ModelAttribute Member member,
                                    HttpSession session, Model model) {

        Optional<Member> obj =
                this.memberRepo.findByUsernameAndPassword(member.getUsername(), member.getPassword());
        if (obj.isEmpty()) {
            LinkedList<String> errorMessages = new LinkedList<>();
            errorMessages.add("帳號或密碼錯誤");
            model.addAttribute("errorMessages", errorMessages);
            return new ModelAndView("login", "command", new Member());
        }
        session.setAttribute("member", obj.get());
        return new ModelAndView("redirect:/lottery/main");

    }

    @GetMapping("/createMember")
    public ModelAndView createMemberView(Member member, Model model) {
        return new ModelAndView("sign", "command", new Member());
    }

    @PostMapping("/createMember")
    public ModelAndView createMember(@Valid @ModelAttribute Member member,
                                     Model model, BindingResult bindingResult) {
        LinkedList<String> errorMessages = new LinkedList<>();
        if (bindingResult.hasErrors()) {
            return new ModelAndView("sign", "command", new Member());
        }
        if (member.getUsername() == null || member.getPassword() == null) {
            errorMessages.add("輸入的帳號或密碼為空");
        }

        Optional<Member> obj = this.memberRepo.findByUsername(member.getUsername());
        if (obj.isPresent()) {

            errorMessages.add("使用者名稱已存在");
            model.addAttribute("errorMessages", errorMessages);
            return new ModelAndView("sign", "command", new Member());
        }

        this.memberRepo.save(member);
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/logout")
    public ModelAndView logoutView(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }

}
