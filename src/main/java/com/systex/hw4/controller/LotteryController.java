package com.systex.hw4.controller;

import com.systex.hw4.service.LotteryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;

@Controller
@RequestMapping("/lottery")
public class LotteryController {

    @GetMapping("/main")
    public String getMainPage() {
        return "lottery/main";
    }

    @PostMapping("/lotteryController.do")
    public String generateLottery(@RequestParam("group") Optional<String> groupParam, @RequestParam("exclude") Optional<String> excludeNum, Model model) {
        LinkedList<String> errorMessages = new LinkedList<>();
        model.addAttribute("errorMessages", errorMessages);
        HashSet<Integer> excludeNumberSet = new HashSet<>();
        int group = 0;
        ArrayList[] lotterys;

        if (groupParam.isPresent() && !groupParam.get().isBlank()) {
            try {
                if (Integer.parseInt(groupParam.get().trim()) < 0) {
                    errorMessages.add("您所輸入的組數為負數");
                } else {
                    group = Integer.parseInt(groupParam.get());
                }
            } catch (NumberFormatException e) {
                errorMessages.add("您所輸入的組數並非為有效數字");
            }
        } else {
            errorMessages.add("您所輸入的組數為空");
        }

        if (excludeNum.isPresent() && !excludeNum.get().isEmpty()) {
            try {
                LotteryService.parseExcludeNum(excludeNum.get(), excludeNumberSet);
            } catch (Exception e) {
                errorMessages.add(e.getMessage());
            }

        }

        if (!errorMessages.isEmpty()) {
            return "lottery/main";
        }

        lotterys = new ArrayList[group];
        for (int i = 0; i < group; i++) {
            lotterys[i] = LotteryService.generateLotteryNum(excludeNumberSet);
        }
        model.addAttribute("lotterys", lotterys);
        model.addAttribute("excludeNumber", excludeNumberSet);
        return "lottery/result";
    }
}
