package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.FormInfo;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/public")
public class PublicController {
    
    @GetMapping({"","/","/index","/home"})
	public String showHome(Model model){ 
		return "homeView";
	}

    @GetMapping({"/quienessomos"}) 
	public String showQuienesSomos(){
		return "aboutUsView";	
	}
	@GetMapping({"/contacto"}) 
	public String showContacta(Model model){
		model.addAttribute("formInfo", new FormInfo());
		return "contactView";	
	}

	@PostMapping("/contacto/submit")
    public String showMyformSubmit(@Valid @ModelAttribute FormInfo formInfo, 
														  BindingResult bindingResult, 
														  Model model) {
		if(bindingResult.hasErrors()) return "redirect:/public/contacto";
		String x = null;
		switch(formInfo.getMotivo()){
            case 1 -> x = "Solicitude de alta como Productor";
            case 2 -> x = "Solicitude de alta como Xestor";
            case 3 -> x = "Solicitude de acceso á API";
			case 4 -> x = "Outros";
        }
		model.addAttribute("motivoElegido", x);
        return "formSubmitView";
    }

		//Login personalizado
		@GetMapping("/signin")
		public String showLogin() {
			return "signinView";
		}
		
		//Logout personalizado
		@GetMapping("/signout")
		public String showLogout() {
			return "signoutView";
		}
	

}
