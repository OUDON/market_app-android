package com.cookpad.android.marketapp.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by takahiro-tomita on 2016/08/15.
 */

@Table
public class CartItem {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(indexed = true)
    public int itemId;

    @Column
    public String name;

    @Column
    public int price;

    @Column
    public int count;

    public CartItem() {
    }

    public static CartItem createCartItem(Item item, int quanity) {
        CartItem res = new CartItem();
        res.itemId = item.getId();
        res.name = item.getName();
        res.price = item.getPrice();
        res.count = quanity;
        return res;
    }
}