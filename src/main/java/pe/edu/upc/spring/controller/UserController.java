package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sun.el.parser.ParseException;

import pe.edu.upc.spring.model.User;
import pe.edu.upc.spring.service.IUserService;

@Controller
@RequestMapping("/usuario")
public class UserController {
	@Autowired
	private IUserService uService;
	
	@RequestMapping("/bienvenido")
	public String goWelcomePage() {
		return "welcome"; 
	}
	
	@RequestMapping("/bienvenidoUsuario")
	public String goWelcomeUserPage() {
		return "welcomeUser"; 
	}
	
	@RequestMapping("/bienvenidoAdmin")
	public String goWelcomeAdminPage() {
		return "welcomeAdmin"; 
	}
	
	@RequestMapping("/cerrarSesion")
	public String logOut(HttpSession session){
		session.setAttribute("userlogged", null);
		return "redirect:/usuario/bienvenido";
	}
	
	@RequestMapping("/login")
	public String goLoginPage(Model model) {
		model.addAttribute("userlogin", new User());
		return "login";
	}
	
	@RequestMapping("/logear")
	public String logged(@ModelAttribute("userlogin") User objUser, HttpSession session) {	
		if(uService.findByEmail(objUser.getEmailUser()).isEmpty()) {
			return "redirect:/usuario/login";
		}
		else {
			User user = uService.findByEmail(objUser.getEmailUser()).get(0);
			if(user.getPasswordUser().equals(objUser.getPasswordUser()) && user.getEmailUser().equals(objUser.getEmailUser())) {
				if(objUser.getPasswordUser().equals("admin") && objUser.getEmailUser().equals("admin@gmail.com")) {
					session.setAttribute("userlogged",user);
					return "redirect:/usuario/bienvenidoAdmin";
				}
				else {
					session.setAttribute("userlogged",uService.findByEmail(objUser.getEmailUser()).get(0));
					return "redirect:/usuario/bienvenidoUsuario";
				}
			}
			else {
				return "redirect:/usuario/login";
			}
		}
	}
	
	@RequestMapping("/registro")
	public String goRegisterPage(Model model) {
		model.addAttribute("user",new User());
		return "register"; 
	}
	
	@RequestMapping("/registrar")
	public String register(@ModelAttribute("user") User objUser, BindingResult binRes, Model model, HttpSession session) 
		throws ParseException
	{
		if (binRes.hasErrors())
			return "user";
		else {
			boolean flag = uService.save(objUser);
			if(flag)
			{
				session.setAttribute("userlogged", objUser);
				return "redirect:/usuario/bienvenidoUsuario";
			}
			else {
				model.addAttribute("mensaje", "Ocurrio un rochezaso, LUZ ROJA");
				return "redirect:/usuario/irRegistrar";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<User> objUser = uService.findById(id);
		if (objUser == null) {
			objRedir.addFlashAttribute("mensaje", "Ocurrio un roche, LUZ ROJA");
			return "redirect:/usuario/listar";
		}
		else {
			model.addAttribute("user", objUser);
			model.addAttribute("userbusqueda", new User());
			model.addAttribute("listUsers",uService.findAllSortAsc());
			return "listUser";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				uService.delete(id);
				model.put("user",new User()); //importante
				model.put("userbusqueda", new User()); //importante
				model.put("listUsers", uService.findAllSortAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listUsers", uService.findAll());
		}
		return "listUser";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listUsers", uService.findAllSortAsc());
		model.put("user",new User());
		model.put("userbusqueda", new User()); 
		return "listUser";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("userbusqueda") User User) 
		throws ParseException
	{
		List<User>listUsers;
		listUsers = uService.findByName(User.getNameUser());
		
		model.put("user", new User());
		model.put("listUsers", listUsers);
		
		return "listUser";
	}
}
