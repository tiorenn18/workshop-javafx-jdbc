package workshop.javafx.jdbc.model.service;

import java.util.List;

import workshop.javafx.jdbc.model.dao.DaoFactory;
import workshop.javafx.jdbc.model.dao.DepartmentDao;
import workshop.javafx.jdbc.model.entities.Department;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
            
        } else {
            dao.update(obj);
        }
    }
}
