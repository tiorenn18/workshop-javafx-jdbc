package workshop.javafx.jdbc.model.service;

import java.util.List;
import workshop.javafx.jdbc.model.dao.DaoFactory;
import workshop.javafx.jdbc.model.dao.SellerDao;
import workshop.javafx.jdbc.model.entities.Seller;

public class SellerService {
    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll() {
        return dao.findAll();
    }

    public void saveOrUpdate(Seller obj) {
        if (obj.getId() == null) {
            dao.insert(obj);
            
        } else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}

