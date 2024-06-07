package com.example.huffmanencoding;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Greeting {
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", defaultValue = "World", required = false) String name, Model model){
        model.addAttribute("name", name);
        return "greeting";
    }
}
