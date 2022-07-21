package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	// Atributos
	private Connection conn;
	
	// Construtor
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	} 

	// Métodos que devem ser implementados por que são declarados na interface
	@Override
	public void insert(Seller obj) {
		
	}

	@Override
	public void update(Seller obj) {
		
	}

	@Override
	public void deleteById(Seller obj) {
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			
			st = conn.prepareStatement(
			     "SELECT seller.*,department.Name as DepName " +
				 "FROM seller INNER JOIN department " +
				 "ON seller.DepartmentId = department.Id " +
				 "WHERE seller.Id = ?");
			
			st.setInt(1, id); // Interrogação recebe o 'id' que chegou de parametro
			rs = st.executeQuery();
			
			if (rs.next()) { // Testar se veio algum resultado		
				// Colocando o resultSet que é gerado em forma de tabela, em colunas
				Department dep = instantiateDepartment(rs); 				
				Seller sel = instantiateSeller(rs, dep);			
				return sel;			
			}
			else {				
				return null;				
			}
		}	
		catch(SQLException e){			
			throw new DbException(e.getMessage());			
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	@Override
	public List<Seller> findAll() {
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller sel = new Seller();
		sel.setId(rs.getInt("Id"));
		sel.setName(rs.getString("Name"));
		sel.setEmail(rs.getString("Email"));
		sel.setBaseSalary(rs.getDouble("BaseSalary"));
		sel.setBirthDate(rs.getDate("BirthDate"));
		sel.setDepartment(dep);
		return sel;
	}


	// Métodos para instanciar um objeto
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); // Acessar a coluna pelo resultSet
		dep.setName(rs.getString("DepName")); // Apelido que aparece na consulta com o nome do departmento
		return dep;
	}
}
