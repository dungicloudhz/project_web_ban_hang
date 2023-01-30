package com.web.shop.webbanhang.util;

public enum Pages {

    ADMIN_CATEGORY("admin/categories/list");
    private String uri;

    Pages(String uri){
        this.uri = uri;
    }

    public String uri(){
        return this.uri;
    }
}
