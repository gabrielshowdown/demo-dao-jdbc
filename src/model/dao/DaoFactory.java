package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// Opera��es estaticas para instaciar os Dao, 
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(); // Macete para nao precisar expor a implemente��o, declarando apenas a interface
	}

}
