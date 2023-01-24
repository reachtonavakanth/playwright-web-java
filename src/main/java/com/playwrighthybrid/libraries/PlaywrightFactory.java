package com.playwrighthybrid.libraries;

import com.microsoft.playwright.*;
import com.playwrighthybrid.utils.DataHandlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;

public class PlaywrightFactory {
    private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();
    private static ThreadLocal<String> testResourcePath = new ThreadLocal<>();
    private static ThreadLocal<String> configPath = new ThreadLocal<>();
    private static ThreadLocal<String> messagesPath = new ThreadLocal<>();
    private static ThreadLocal<String> testDataPath = new ThreadLocal<>();

    public Playwright getPlayWright() {
        return playwright.get();
    }

    public Browser getBrowser() {
        return browser.get();
    }

    public BrowserContext getBrowserContext() {
        return browserContext.get();
    }

    public Page getPage() {
        return page.get();
    }

    public String getTestResourcePath() {
        return testResourcePath.get();
    }

    public String getConfigPath() {
        return configPath.get();
    }

    public String getMessagesPath() {
        return messagesPath.get();
    }

    public String getTestDataPath() {
        return testDataPath.get();
    }

    public void setTestResourcePath() {
        testResourcePath.set(System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "test" + File.separator + "resources" + File.separator);
    }

    public void setConfigPath() {
        configPath.set(getTestResourcePath() + "config" + File.separator);
    }

    public void setMessagesPath() {
        messagesPath.set(getTestResourcePath() + "messages" + File.separator);
    }

    public void setTestDataPath() {
        testDataPath.set(getTestResourcePath() + "testData" + File.separator);
    }

    public Page initBrowser(String browserName) {
        System.out.println("browser name is : " + browserName);
        setTestResourcePath();
        setConfigPath();
        setMessagesPath();
        setTestDataPath();

        playwright.set(Playwright.create());
        switch (browserName.toLowerCase()) {
            case "chromium":
                browser.set(getPlayWright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "firefox":
                browser.set(getPlayWright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "safari":
                browser.set(getPlayWright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
                break;
            case "chrome":
                browser.set(getPlayWright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)));
                break;
            case "edge":
                browser.set(getPlayWright().chromium().launch(new BrowserType.LaunchOptions().setChannel("msedge").setHeadless(false)));
                break;
            default:
                System.out.println("please pass the right browser name......");
                break;
        }
        browserContext.set(getBrowser().newContext());
        page.set(getBrowserContext().newPage());
        launchApp();
        return getPage();
    }

    public void launchApp() {
        try {
            getPage().navigate(new DataHandlers().getJsonObject(getConfigPath() + "runner.json", "appInfo").getString("url"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String takeScreenshot(String path) {
        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);
        return base64Path;
    }

}

