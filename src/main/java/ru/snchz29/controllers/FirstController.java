package ru.snchz29.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("")
public class FirstController {
    @GetMapping("")
    public String helloPage() {
        return "hello_world";
    }

    @GetMapping("/bye")
    public String goodbyePage() {
        return "first/goodbye";
    }

    @GetMapping("/calc")
    public String calc(@RequestParam(value = "a") int a,
                       @RequestParam(value = "b") int b,
                       @RequestParam(value = "action") String action,
                       Model model) {
        double result = 0;
        String act = "...";
        switch (action) {
            case "multiplication":
                act = "*";
                result = a * b;
                break;
            case "division":
                act = "/";
                result = a / (double)b;
                break;
            case "addition":
                act = "+";
                result = a + b;
                break;
            case "subtraction":
                act = "-";
                result = a - b;
                break;
        }
        model.addAttribute("result", "" + a + act + b + "=" + result);
        return "first/calc";
    }
}
