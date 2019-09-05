package com.example.mentorOnDemand.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.mentorOnDemand.dao.MentorDao;
import com.example.mentorOnDemand.model.Mentor;
import com.example.mentorOnDemand.model.User;

@Controller
public class MentorController {
	@Autowired
	private MentorDao mentorDao;

	@RequestMapping(value = "/mentorRegister", method = RequestMethod.GET)
	public String insert(ModelMap model) {
		Mentor e = new Mentor();
		model.addAttribute("mentor", e);
		return "mentorSignup";

	}

	@RequestMapping(value = "/mentorRegister", method = RequestMethod.POST)
	public String formHandler(@Valid @ModelAttribute("mentor") Mentor mentor, BindingResult result, Model model)
			throws SQLException {
		System.out.println(mentor);
		if (result.hasErrors()) {
			System.out.println("errors");
			System.out.println(result.getAllErrors());
			model.addAttribute("mentor", mentor);
			return "mentorSignup";
		}
		mentorDao.save(mentor);
		// model.addAttribute("name", employee.getName());
		return "redirect:/mentorLogin";
	}

	@RequestMapping(value = "/mentorLogin", method = RequestMethod.GET)
	public String login(ModelMap model) {
		System.out.println("logged in");
		Mentor e = new Mentor();
		model.addAttribute("mentorL", e);
		return "mentorLogin";

	}

	@RequestMapping(path = "/mentorLogin1", method = RequestMethod.POST)
	public ModelAndView mentorUser( @ModelAttribute("mentorL") Mentor mentor, ModelMap model) throws Exception {

		ModelAndView mav = null;
		System.out.println(mentor);
		String email = mentor.getEmail();

		List<Mentor> user1 = mentorDao.findByemail(email);
		if (user1.isEmpty()) 
		{
			mav = new ModelAndView("mentorLogin", "message", "Invalid Username or Password :(");
		} 
		else 
		{
			Mentor user2 = user1.get(0);
			boolean b=user2.isActive();boolean a=true;
			if(a==b)
			{
				System.out.println("blocked");
				mav = new ModelAndView("mentorLogin", "message", "Account is Blocked :(");
			}
			else if ((mentor.getEmail().equals(user2.getEmail())) && (mentor.getPassword().equals(user2.getPassword())))
			{
				
				mav = new ModelAndView("userLandingPage");
			} 
			else {
				System.out.println("pswd incorrect");
			 	mav = new ModelAndView("mentorLogin", "message", "Invalid Username or Password :(");
			     }
		}

		return mav;

	}

}
