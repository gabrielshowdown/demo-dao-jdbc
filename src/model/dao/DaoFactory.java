package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	// Operações estaticas para instaciar os Dao, 
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection()); // Macete para nao precisar expor a implementeção, declarando apenas a interface
	}

}
