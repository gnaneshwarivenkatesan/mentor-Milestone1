package com.example.mentorOnDemand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.mentorOnDemand.dao.MentorDao;
import com.example.mentorOnDemand.model.Mentor;
import com.example.mentorOnDemand.model.User;
import com.example.mentorOnDemand.service.UserService;
@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private MentorDao mentorDao;

	@RequestMapping(path="/userdb")
	public ModelAndView getUserList() throws Exception {
	     ModelAndView mv=new ModelAndView();
	     mv.setViewName("userdb");
	     mv.addObject("userdb",userService.getUserList());
	     return mv;
	}

	@RequestMapping(path="/mentordb")
	public ModelAndView getmentorList() throws Exception {
	     ModelAndView mv=new ModelAndView();
	     mv.setViewName("mentordb");
	     mv.addObject("mentordb",userService.getMentorList());
	     return mv;
	}
	@RequestMapping(value = "/blockuser", method = RequestMethod.GET)
	public String blockUser(ModelMap model, @RequestParam("reg_code") int regCode,
			@ModelAttribute("userdb") User user) {
		boolean a=user.isActive();
		if(a==false)
		{
			user.setActive(true);
		}
		return "redirect:/userdb";
	}
	@RequestMapping(value = "/blockmentor")
	public String blockMentor(ModelMap model, @RequestParam("id") int regCode,
			@ModelAttribute("mentordb") Mentor mentor) {
		System.out.println(regCode);
		boolean a=mentor.isActive();
		System.out.println(a);
		if(a==false)
		{		
			mentorDao.blockById(regCode);
		}
		System.out.println(a);
		return "redirect:/mentordb";
	}
	@RequestMapping(value = "/Unblockmentor")
	public String unblockMentor(ModelMap model, @RequestParam("id") int regCode,
			@ModelAttribute("mentordb") Mentor mentor) {
		System.out.println(regCode);
		boolean a=mentor.isActive();	
		System.out.println(a);
		a=true;
		if(a==true)
		{				

			mentorDao.unblockById(regCode);
		}
		System.out.println(a);
		return "redirect:/mentordb";
	}
	

}
