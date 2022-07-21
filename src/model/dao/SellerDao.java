package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {

	// Alterar no banco, o obj que enviar no parametro de entrada
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById (Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
	
}