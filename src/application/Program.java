package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		Department obj = new Department (1 , "Books");
		Seller seller = new Seller(21, "Bob", "bob@gmail", new Date(), 3000.00, obj);
		SellerDao sellerDao = DaoFactory.createSellerDao(); // Programa principal conhece só a interface, nao o programa principal
		
		System.out.println(obj);
		System.out.println(seller);
		
	}

}
