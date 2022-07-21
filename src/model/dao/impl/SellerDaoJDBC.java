package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	// M�todos que devem ser implementados por que s�o declarados na interface
	@Override
	public void insert(Seller sel) {
		
		PreparedStatement st = null;
		
		try {
			
			st = conn.prepareStatement(
			 "INSERT INTO seller " +
		     "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +
		     "VALUES " + 
		     "(?, ?, ?, ?, ?)" ,
		     Statement.RETURN_GENERATED_KEYS); // Retornar o Id do vendedor inserido
		
			st.setString(1, sel.getName());
			st.setString(2, sel.getEmail());
			st.setDate(3, new java.sql.Date(sel.getBirthDate().getTime()));
			st.setDouble(4, sel.getBaseSalary());
			st.setInt(5, sel.getDepartment().getId());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) { // Verificar se n�o � nulo
					int id = rs.getInt(1);
					sel.setId(id);
					
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException ("Erro inesperado, nenhuma linha afetada");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
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
			
			st.setInt(1, id); // Interroga��o recebe o 'id' que chegou de parametro
			rs = st.executeQuery();
			
			if (rs.next()) { // Testar se veio algum resultado		
				// Colocando o resultSet que � gerado em forma de tabela, em colunas
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
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {	
			st = conn.prepareStatement(
				 "SELECT seller.*,department.Name as DepName " +
				 "FROM seller INNER JOIN department " +
				 "ON seller.DepartmentId = department.Id " +
				 "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List <Seller> list = new ArrayList<Seller>();
			// Criar um map para nao ficar criando mais de um departamento
			Map <Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // Testar se veio algum resultado	
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sel = instantiateSeller(rs, dep);			
				list.add(sel);		
			}
			return list;
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
	public List<Seller> findByDepartment(Department department) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {	
			st = conn.prepareStatement(
				 "SELECT seller.*,department.Name as DepName " +
				 "FROM seller INNER JOIN department " +
				 "ON seller.DepartmentId = department.Id " +
				 "WHERE DepartmentId = ? " +
				 "ORDER BY Name");
			
			st.setInt(1, department.getId()); // Interroga��o recebe o 'department' que chegou de parametro
			rs = st.executeQuery();
			
			List <Seller> list = new ArrayList<Seller>();
			// Criar um map para nao ficar criando mais de um departamento
			Map <Integer, Department> map = new HashMap<>();
			
			while (rs.next()) { // Testar se veio algum resultado	
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller sel = instantiateSeller(rs, dep);			
				list.add(sel);		
			}
			return list;
		}	
		catch(SQLException e){			
			throw new DbException(e.getMessage());			
		}
		finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	// M�todos para instanciar um objeto
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
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId")); // Acessar a coluna pelo resultSet
		dep.setName(rs.getString("DepName")); // Apelido que aparece na consulta com o nome do departmento
		return dep;
	}


}
