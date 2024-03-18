package database;


import database.util.DatabaseUtils;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.sql.SQLException;

public class Hooks {

        @Before()
        public void setScenario(Scenario scenario) throws IOException, SQLException {
            System.out.println("Escenario");
            Logger.getRootLogger().info("-----------" + scenario.getName() + "-----------");
        }

        @After()
        public void dismissAll(Scenario scenario) throws SQLException, IOException {
           Logger.getRootLogger().info(" ending -----------" + scenario.getName() + "-----------");
           DatabaseUtils.closeConnection();
        }

}
