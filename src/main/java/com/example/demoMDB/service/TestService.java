package com.example.demoMDB.service;

import com.example.demoMDB.config.MultiTenantManager;
import com.example.demoMDB.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class TestService{

    private TestDao testDao;
    private MultiTenantManager tenantManager;

    @Autowired
    public TestService(TestDao testDao,MultiTenantManager tenantManager){
        this.testDao = testDao;
        this.tenantManager = tenantManager;
    }

    public List<Object> getData(String databaseType) {
        String query = "";
        // Json Node
        if(databaseType.equals("MYSQL1")){
            try {
                tenantManager.addTenant(databaseType, "jdbc:mysql://localhost:3308/test_database",
                        "root", "","com.mysql.jdbc.Driver");
                tenantManager.setCurrentTenant(databaseType);
            }catch(SQLException e){
                e.printStackTrace();
            }
            query = "SELECT * FROM user";

        } else if(databaseType.equals("MYSQL2")){
            try {
                tenantManager.addTenant(databaseType, "jdbc:mysql://localhost:3308/test_db",
                        "root", "","com.mysql.jdbc.Driver");
                tenantManager.setCurrentTenant(databaseType);
            }catch(SQLException e){
                e.printStackTrace();
            }

            query = "SELECT * from db_obj";
        } else if(databaseType.equals("MYSQL3")){
            try {
                tenantManager.addTenant(databaseType, "jdbc:mysql://localhost:3308/tutor_point",
                        "root", "","com.mysql.jdbc.Driver");
                tenantManager.setCurrentTenant(databaseType);
            }catch(SQLException e){
                e.printStackTrace();
            }

            query = "SELECT * from user";
        }
        return  testDao.getResult(query);
    }
}
