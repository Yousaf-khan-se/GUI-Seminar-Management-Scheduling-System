package DataAccessLayer.DAOClasses;

import java.util.ArrayList;
import java.util.Objects;

public interface IDAO {
    boolean insert(ArrayList<Object> data);
    boolean update(ArrayList<Object> data, Object id, String idName);
    boolean delete(Object id, String idName);
    ArrayList<Object> load(Object id, String idName);
    ArrayList<ArrayList<Object>> load();
    int getRowCount();
}
