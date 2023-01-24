package com.playwrighthybrid.tests;

import com.playwrighthybrid.utils.DataHandlers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;

public class LoginTests extends BaseTest {
    JSONObject login = null;
    @BeforeTest
    public void dataInit() {
        try {
            login = new DataHandlers().getJsonObject(pf.getTestDataPath() + "testData.json", "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void login() throws InterruptedException {
        homepage = loginPage.performLogin(login.getString("username"), login.getString("password"));
    }

    @Test
    public void verifyHomePageElements() throws InterruptedException {
        homepage = loginPage.performLogin(login.getString("username"), login.getString("password"));
        Assert.assertTrue(homepage.getMenuIcon());
        Assert.assertTrue(homepage.getCartIcon());
        Assert.assertTrue(homepage.getSortFilter());
        homepage.clickOnMenu();
    }
}
