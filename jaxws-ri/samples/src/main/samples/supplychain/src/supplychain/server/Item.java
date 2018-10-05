/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package supplychain.server;

public class Item {
    String name;
    int itemID;
    int quantity;
    float price;
    
    public String getName () { return name; }
    public void setName (String name) { this.name = name; }
    
    public int getItemID () { return itemID; }
    public void setItemID (int itemID) { this.itemID = itemID; }
    
    public int getQuantity () { return quantity; }
    public void setQuantity (int quantity) { this.quantity = quantity; }
    
    public float getPrice () { return price; }
    public void setPrice (float price) { this.price = price; }
}
