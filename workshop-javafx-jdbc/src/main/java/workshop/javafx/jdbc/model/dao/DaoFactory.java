package workshop.javafx.jdbc.model.dao;

import workshop.javafx.jdbc.db.DB;
import workshop.javafx.jdbc.model.dao.impl.DepartmentDaojdbc;
import workshop.javafx.jdbc.model.dao.impl.SellerDaoJdbc;

public class DaoFactory {
    
	public static SellerDao createSellerDao() {
		return new SellerDaoJdbc(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaojdbc(DB.getConnection());
	}
}
