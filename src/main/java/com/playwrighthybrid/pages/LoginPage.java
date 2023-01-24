package com.playwrighthybrid.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
   private Page page;

    //Locators
    private String userNameFld = "input#user-name";
    private String passwordFld = "input#password";
    private String loginBtn = "input#login-button";

    //Page Home Constructors
    public LoginPage(Page page) {
        this.page = page;
    }

    public String getPageTitle() {
        return page.title();
    }

    public String getPageUrl() {
        return page.url();
    }

    public String getEleText(String eleLocator){
        return page.textContent(eleLocator);
    }
    public HomePage performLogin(String userName, String userPassword) {
        page.click(userNameFld);
        page.fill(userNameFld, userName);
        page.click(passwordFld);
        page.fill(passwordFld, userPassword);
        page.click(loginBtn);
        return new HomePage(page);
    }
}
