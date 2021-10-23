package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.User;

public interface IUserService {
	public boolean save(User user);
	public void delete(int idUser);
	public List<User> findAll();
	public List<User> findAllSortAsc();
	public Optional<User>findById(int idUser);
	public List<User> findByName(String nameUser);
}
