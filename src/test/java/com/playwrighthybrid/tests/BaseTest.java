package com.playwrighthybrid.tests;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.playwrighthybrid.libraries.PlaywrightFactory;
import com.playwrighthybrid.pages.HomePage;
import com.playwrighthybrid.pages.LoginPage;
import com.playwrighthybrid.utils.DataHandlers;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class BaseTest {


    PlaywrightFactory pf;
    Page page;
    LoginPage loginPage;
    HomePage homepage;
    JSONObject config;

    @BeforeTest
    public void setup() {
        pf = new PlaywrightFactory();
        page = pf.initBrowser("chromium");
        loginPage = new LoginPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }

    @BeforeMethod
    public void setTracing() {

        try {
            config = new DataHandlers().getJsonObject(pf.getConfigPath() + "runner.json", "configs");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if ((config.getString("tracing")).toLowerCase().equalsIgnoreCase("yes")) {
            pf.getBrowserContext().tracing()
                    .start(new Tracing.StartOptions().setScreenshots(true).setSnapshots(true));
        }
    }

    @AfterMethod
    public void relaunch(ITestResult result) {

        if ((config.getString("tracing")).toLowerCase().equalsIgnoreCase("yes")) {
            String path = System.getProperty("user.dir") + File.separator + "tracing" + File.separator + result.getName() + File.separator + new DataHandlers().getDateTime() + ".zip";
            pf.getBrowserContext().tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get(path)));

        }
        pf.launchApp();
    }
}
