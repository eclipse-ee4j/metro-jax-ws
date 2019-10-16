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

import java.util.List;
import javax.jws.WebService;

@WebService
    public class WarehouseImpl {
    
    /**
     * @param PurchaseOrder
     * @throws InvalidPOException
     *             if the item list in the PurchaseOrder is null
     */
    public ShipmentNotice submitPO (PurchaseOrder po) throws InvalidPOException {
        if (po.getItemList () == null) {
            throw new InvalidPOException ("Invalid Item List");
        }
        
        // warehouse processes the PurchaseOrder
        // and prepares a list of items that are shipped back
        
        ShipmentNotice shipmentNotice = new ShipmentNotice ();
        shipmentNotice.setOrderNumber (po.getOrderNumber ());
        shipmentNotice.setCustomerNumber (po.getCustomerNumber ());
        shipmentNotice.setShipmentNumber ("ABC-12345-XYZ");
        List<Item> itemList = shipmentNotice.getItemList ();
        
        for (Item item : po.getItemList ()) {
            itemList.add (item);
        }
        
        return shipmentNotice;
    }
}


