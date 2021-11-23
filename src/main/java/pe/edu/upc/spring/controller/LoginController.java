package pe.edu.upc.spring.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.model.Role;
import pe.edu.upc.spring.service.IUserService;

@Controller
public class LoginController {

	@Autowired
	private IUserService uService;
	
	@GetMapping(value = { "/login", "/"})
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model, Principal principal) {
		
		if (principal != null) { // Si está autenticado		
			return "redirect:/welcome/bienvenido";
		}
		
		if (error != null) {
			model.addAttribute("error",
					"Nombre de usuario o contraseña incorrecta, por favor vuelva a intentarlo!");
		}
		if (logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}

		return "login";
	}
	
	@GetMapping("/auth/registro")
	public String registroForm(Model model) {
		model.addAttribute("user", new Users());
		return "register";
	}
	
	@PostMapping("/auth/registro")
	public String registro(@Validated @ModelAttribute Users objUser, BindingResult result, Model model) {
		if(result.hasErrors()) {
			
			return "redirect:/auth/registro";
		}
		else {
			objUser.setEnabled(true);
			//A�ADIREMOS ROLES
			List<Role>roles = new ArrayList<Role>();
			Role role = new Role();
			role.setAuthority("ROLE_USER");
			roles.add(role);
			objUser.setRoles(roles);
			
			model.addAttribute("usuario", uService.registrar(objUser)); //SE REGISTRA
			
			return "redirect:/login";
		}
	}
}
