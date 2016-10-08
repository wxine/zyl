package com.test;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.domain.Contact;
import com.test.repository.ContactDao;

@Controller
public class IndexController {

	@Resource
	private ContactDao contactdao;

	@RequestMapping("/index")
	public String index(Model m,Contact contact) {
		List<Contact> list = contactdao.findAll();
		m.addAttribute("list", list);
		return "index";
	}

	@RequestMapping(value="/save")
	public String Save(@Valid Contact contact, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		contact.setId(UUID.randomUUID().toString().replace("-", ""));
		contactdao.save(contact);
		return "redirect:/index";
	}

	@RequestMapping("/delete")
	public String Delete(String id) {
		Contact contact = contactdao.findById(id);
		contactdao.delete(contact);
		return "redirect:/index";
	}

	@RequestMapping("/update")
	public String Update(Model m, String id) {
		Contact contact = contactdao.findById(id);
		m.addAttribute("update", contact);
		return "update";
	}

	@RequestMapping("/updates")
	public String Merge(String id, String name, String phone, String address) {
		Contact contact = contactdao.findById(id);
		contact.setName(name);
		contact.setPhone(phone);
		contact.setAddress(address);
		contactdao.update(contact);
		return "redirect:/index";
	}

}
