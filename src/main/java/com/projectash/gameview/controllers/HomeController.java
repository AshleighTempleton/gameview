package com.projectash.gameview.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.security.Principal;
import org.springframework.stereotype.Controller;


@Controller
public class HomeController {

	@GetMapping("/")
	public String index(Model model, Principal principal){
		if(principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "index";
	}

	@GetMapping("/loginToGame")
	public String login(){
		return "loginToGame";
	}

}
