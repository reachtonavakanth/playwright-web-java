package com.playwrighthybrid.pages;

import com.microsoft.playwright.Page;

public class HomePage {
   private Page page;

    //Locators
    private String menuIcon = "id=react-burger-menu-btn";
    private String cartIcon = "id=shopping_cart_container";
    private String sortFilter = ".select_containerr";

    //Page Home Constructors
    public HomePage(Page page) {
        this.page = page;
    }

    public boolean getMenuIcon() {
      return page.isVisible(menuIcon);
    }

    public boolean getCartIcon() {
        return page.isVisible(cartIcon);
    }
    public boolean getSortFilter() {
        return page.isVisible(sortFilter);
    }
    public void clickOnMenu(){
        page.click(menuIcon);
    }
}
