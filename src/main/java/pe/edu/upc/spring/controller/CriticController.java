package pe.edu.upc.spring.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import pe.edu.upc.spring.model.Critic;
import pe.edu.upc.spring.service.ICriticService;

@Controller
@RequestMapping("/critico")
public class CriticController {
	@Autowired
	private ICriticService cService;
	
	@RequestMapping("/registrar")
	public String register(@Valid @ModelAttribute("critic") Critic objCritic, BindingResult binRes, Model model) 
		throws Exception
	{
		if (binRes.hasErrors())
		{	model.addAttribute("listCritics", cService.findAllSortIdAsc());
			model.addAttribute("criticbusqueda", new Critic());
			return "listCritic";
		}
		else {
			int rpta = cService.save(objCritic);
			if(rpta > 0)
			{
				model.addAttribute("mensaje", "Ya existe este crítico");
				model.addAttribute("listCritics", cService.findAllSortIdAsc());
				model.addAttribute("critic",new Critic());
				model.addAttribute("criticbusqueda", new Critic());
				return "listCritic";
			}	
			else {
				model.addAttribute("mensaje", "Se registro un crítico correctamente");
				model.addAttribute("listCritics", cService.findAllSortIdAsc());
				model.addAttribute("critic",new Critic());
				model.addAttribute("criticbusqueda", new Critic());
				return "listCritic";
			}
		}
	}
	
	@RequestMapping("/modificar/{id}")
	public String modify(@PathVariable int id, Model model, RedirectAttributes objRedir) 
		throws ParseException
	{
		Optional<Critic> objCritic = cService.findById(id);
		if (objCritic == null) {
			objRedir.addFlashAttribute("mensaje", "Rochezaso");
			return "redirect:/critic/listar";
		}
		else {
			model.addAttribute("critic", objCritic);
			model.addAttribute("criticbusqueda", new Critic());
			model.addAttribute("listCritics", cService.findAllSortIdAsc());
			return "listCritic";                   
		}
	}
	
	@RequestMapping("/eliminar")
	public String delete(Map<String, Object> model, @RequestParam(value="id") Integer id) {
		try {
			if(id!=null && id>0) {
				cService.delete(id);
				model.put("critic",new Critic());
				model.put("criticbusqueda", new Critic());
				model.put("listCritics", cService.findAllSortIdAsc());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.put("Mensaje", "Ocurrio un error");
			model.put("listCritics", cService.findAllSortIdAsc());
			model.put("critic", new Critic());
			model.put("criticbusqueda", new Critic());
		}
		
		return "listCritic";
	}
	
	@RequestMapping("/listar")
	public String list(Map<String, Object> model) {
		model.put("listCritics", cService.findAllSortIdAsc());
		model.put("critic",new Critic());
		model.put("criticbusqueda", new Critic());
		
		return "listCritic";
	}
	
	@RequestMapping("/buscar")
	public String buscar(Map<String, Object> model,@ModelAttribute("criticbusqueda") Critic critic) 
		throws ParseException
	{
		List<Critic>listCritics;
		listCritics = cService.findByName(critic.getNameCritic());
		
		model.put("critic", new Critic());
		model.put("listCritics", listCritics);
		
		return "listCritic";
	}
}
