package br.api.spark.dao.impl;

import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import br.api.spark.dao.RoleDao;
import br.api.spark.model.Role;

public class Sql2oRoleDao implements RoleDao {

	private final Sql2o sql2o;
	
    public Sql2oRoleDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(Role role) {
    	
        String sql = "INSERT INTO roles (name) VALUES (:name)";
        
		try (Connection con = sql2o.open()) {
			int id = (int) con.createQuery(sql, true).bind(role).executeUpdate().getKey();
			role.setId(id);
			
		} catch (Sql2oException ex) {
			System.out.println(ex);
		}
		
    }

    @Override
    public List<Role> getAll() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM roles")
                    .executeAndFetch(Role.class);
        }
    }

    @Override
    public void deleteById(int id) {
    	
    		String sql = "DELETE from roles WHERE id=:id";
        
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

	@Override
	public Role findById(int id) {
		try(Connection con = sql2o.open()){
          return con.createQuery("SELECT * FROM roles WHERE id = :id")
                  .addParameter("id", id)
                  .executeAndFetchFirst(Role.class);
		}
	}

}
