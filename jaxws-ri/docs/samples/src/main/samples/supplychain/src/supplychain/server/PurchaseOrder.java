/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package supplychain.server;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;
import java.util.ArrayList;

public class PurchaseOrder {
    String orderNumber;
    String customerNumber;
    @XmlElement (nillable = true)
    List<Item> itemList;
    
    public String getOrderNumber () { return orderNumber; }
    public void setOrderNumber (String orderNumber) { this.orderNumber = orderNumber; }
    
    public String getCustomerNumber () { return customerNumber; }
    public void setCustomerNumber (String customerNumber) { this.customerNumber = customerNumber; }
    
    public List<Item> getItemList () {
        if (itemList == null)
            itemList = new ArrayList<Item>();
        
        return itemList;
    }
}
