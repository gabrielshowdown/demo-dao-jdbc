package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {
	
	// Alterar no banco, o obj que enviar no parametro de entrada
	void insert(Department obj);
	void update(Department obj);
	void deleteById(Department obj);
	Department findById (Integer id);
	List<Department> findAll();
	
}
