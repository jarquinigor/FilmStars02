package pe.edu.upc.spring.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.upc.spring.model.Users;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController {

	@Autowired
	private IUserService uService;

	@RequestMapping("/bienvenido")
	public String irPaginaBienvenida(Authentication auth, HttpSession session, HttpServletRequest request) {
		
		if (auth.getName().isEmpty() == false) { // SI SE ESTÁ AUTENTICADO
			
			session.setAttribute("userlogged", new Users());
			String username = auth.getName();
			Users user = uService.findByUsername(username);

			//System.out.println(user.getUsername());
			//System.out.println(user.getPassword());

			user.setPassword(null);
			session.setAttribute("userlogged", user);
			
			if (request.isUserInRole("ROLE_ADMIN")) {
	            return "welcomeAdmin";
	        }
			else { //ROLE_USER
				return "welcomeUser";
			}
		}  //NO ESTÁ AUTENTICADO
		else {
			return "welcome";
		}
	}

	@RequestMapping("/bienvenidoN")
	public String irPaginaBienvenidaN(Principal principal) {

		if (principal != null) { //ESTÁ LOGEADO O ESTÁ AUTENTICADO
			
			//VERIFICAR
			return "redirect:/welcome/bienvenido";
		}

		return "welcome";
	}

}
