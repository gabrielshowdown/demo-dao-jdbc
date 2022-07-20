package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// Operações estaticas para instaciar os Dao, 
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(); // Macete para nao precisar expor a implementeção, declarando apenas a interface
	}

}
