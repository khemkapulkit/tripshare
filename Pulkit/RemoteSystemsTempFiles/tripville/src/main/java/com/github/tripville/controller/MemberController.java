package com.github.tripville.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.tripville.model.Member;
import com.github.tripville.model.MemberLogin;
import com.github.tripville.service.StudentService;

@Controller
@SessionAttributes("student")
public class MemberController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private MemberLogin studentLogin;
	
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signup(Model model) {
		Member student = new Member();		
		model.addAttribute("student", student);		
		return "signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("student") Member student, BindingResult result, Model model) {		
		if(result.hasErrors()) {
			return "signup";
		} else if(studentService.findByUserName(student.getUserName())) {
			model.addAttribute("message", "User Name exists. Try another user name");
			return "signup";
		} else {
			studentService.save(student);
			model.addAttribute("message", "Saved member details");
			model.addAttribute("userName",student.getUserName());
			return "redirect:carregistration.html";
		}
	}

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model) {			
		studentLogin = new MemberLogin();		
		model.addAttribute("studentLogin", studentLogin);
		return "login";
	}
	
	@RequestMapping(value="/login")
	public ModelAndView login(@Valid @ModelAttribute("studentLogin") MemberLogin studentLogin, BindingResult result, Model model, HttpSession session) {
		
		ModelAndView modelAndView = new ModelAndView();  
        if (result.hasErrors()) {
			modelAndView.setViewName("login");
			return modelAndView;
		} else {
			boolean found = studentService.findByLogin(studentLogin.getUserName(), studentLogin.getPassword());
			if (found) {
				modelAndView.setViewName("redirect:/home.html");
				modelAndView.addObject("student", studentLogin);
				session.setAttribute("student", studentLogin);
				MemberLogin user = (MemberLogin) session.getAttribute("student");
				return modelAndView;
			} else {				
				modelAndView.setViewName("login");
				return modelAndView;
			}
		}
		
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public ModelAndView home(@Valid @ModelAttribute("student") MemberLogin studentLogin, HttpSession session) {			
		
		try{
			ModelAndView modelAndView = new ModelAndView("home", "student", studentLogin);
			modelAndView.setViewName("home");
			MemberLogin user = (MemberLogin) session.getAttribute("student");
			return modelAndView;
		}
		catch(Exception e){
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("index");
			return modelAndView;
		}
		
		
	}
	@RequestMapping(value="/home", method=RequestMethod.POST)
	public ModelAndView home(@Valid @ModelAttribute("student") MemberLogin studentLogin, BindingResult result, Model model, HttpSession session, @RequestParam("btnClk") String request) {			
		ModelAndView modelAndView = new ModelAndView();  
		if (request.contains("Add")) {
			modelAndView.setViewName("redirect:/addtrip.html");
		} else if (request.contains("Search")) {
			modelAndView.setViewName("redirect:/searchtrip.html");
		}
		modelAndView.addObject("student", studentLogin);
		session.setAttribute("student", studentLogin);
		MemberLogin user = (MemberLogin) session.getAttribute("student");
		return modelAndView;
		
		
		
	}
}
