/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.sample.service;

import com.sun.xml.ws.sdo.sample.service.types.*;

import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.RequestWrapper;
import javax.xml.parsers.ParserConfigurationException;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebParam;
//import javax.wsdl.WSDLException;
import java.util.logging.Logger;
import java.util.*;
import java.math.BigInteger;
import java.io.IOException;

//import oracle.j2ee.ws.common.sdo.SchemaLocation;
//import oracle.j2ee.ws.common.sdo.SDOHelper;
//import commonj.sdo.helper.DataFactory;
import org.eclipse.persistence.sdo.SDOHelper;
import org.eclipse.persistence.sdo.helper.SDOHelperContext;
import org.eclipse.persistence.sdo.helper.SDOXSDHelper;

import commonj.sdo.helper.XMLHelper;
import org.xml.sax.SAXException;

import com.sun.xml.ws.db.sdo.SDOUtils;
import com.sun.xml.ws.db.sdo.SchemaInfo;

//@SchemaLocation(value = "data/sdo/HRAppService.wsdl")
@javax.jws.WebService(targetNamespace="http://sdo.sample.service/", name="HRAppService")
public class HRAppServiceImpl implements HRAppService {
    private static final Logger LOG = Logger.getLogger(HRAppServiceImpl.class.getName());

    private Map<BigInteger,Emp> employees = new HashMap<BigInteger,Emp>();

    // todo should I really have to do this?
    public HRAppServiceImpl() {
//        try {
            // todo SDOHelper.defineSchemas("data/sdo/HRAppService.wsdl",null);
//        } catch (IOException e) {
//            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
//        } catch (SAXException e) {
//            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
//        } catch (WSDLException e) {
//            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
//        }
    }
    public com.sun.xml.ws.sdo.sample.service.types.Emp createEmp( Emp emp) {
        employees.put(emp.getEmpno(),emp);
        return emp;
    }



    public void deleteEmp( Emp emp) {
        employees.remove(emp.getEmpno());
    }


    public List<Emp> findClerks(FindCriteria findCriteria,
                                FindControl findControl) {

        return null;  //Todo code me

    }


    public List<Dept> findDepts( FindCriteria findCriteria,
                                 FindControl findControl) {
        return null;  //Todo code me

    }


    public List<Emp> findEmps( FindCriteria findCriteria,
                               FindControl findControl) {

        return null; // todo code me
    }


    public List<Emp> findEmpsByJob(FindCriteria findCriteria,
                                   String job,
                                   FindControl findControl) {
        int fetchSize = findCriteria.getFetchSize();
        int fetchStart = findCriteria.getFetchStart();
        ViewCriteria filter = findCriteria.getFilter();
// todo this gives conversion error       String conjunction = filter.getConjunction();
        List group = filter.getGroup();

        // todo use the find criteria and control

        List<Emp> ret = new Vector<Emp>();
        int i = 0;
        Set<BigInteger> bigIntegers = employees.keySet();
        for (BigInteger id : bigIntegers){
            Emp emp = employees.get(id);
            if (job.equals(emp.getJob())){
                ret.add(emp);
            }
        }
        return ret;

    }


    public Dept getDept(@WebParam(mode = WebParam.Mode.IN,
                                  name = "deptno") BigInteger deptno) {

        //Dept dept =
                //(Dept) DataFactory.INSTANCE.create(Dept.class);
        Dept dept = (Dept) SDOHelperContext.getHelperContext().getDataFactory().create(Dept.class);
        dept.setDeptno(deptno);
        return dept;
    }


    public Emp getEmp(@WebParam(mode = WebParam.Mode.IN,
                                name = "empno") BigInteger empno) {
        return null;  //Todo code me

    }


    public List<Emp> getManagerAndPeers(@WebParam(mode = WebParam.Mode.IN,
                                                  name = "empno") BigInteger empno) {
        Emp ret;
        List<Emp> v = new Vector<Emp>();

        for (int i=0;i<3;i++){
            //ret = (Emp) DataFactory.INSTANCE.create(Emp.class);
            ret = (Emp) SDOHelperContext.getHelperContext().getDataFactory().create(Emp.class);
            ret.setEmpno(new BigInteger(""+i));
            ret.setEname("name"+i);
            ret.setJob("job"+i);
            v.add(ret);

        }
        return v;
    }


    public java.math.BigDecimal getTotalComp(@WebParam(mode = WebParam.Mode.IN,
                                        name = "empno") BigInteger empno) {
        return new java.math.BigDecimal(empno.toString());

    }


    public Emp mergeEmp(@WebParam(mode = WebParam.Mode.IN,
                                  name = "emp") Emp emp) {
        return null;  //Todo code me

    }



    public List<Emp> processEmps(@WebParam(mode = WebParam.Mode.IN,
                                           name = "changeOperation") String changeOperation,
                                 @WebParam(mode = WebParam.Mode.IN,
                                           name = "emp") List<Emp> emp, @WebParam(mode = WebParam.Mode.IN,
                                                                                  name = "processControl") ProcessControl processControl) {
        Emp ret;
        List<Emp> v = new Vector<Emp>();

        for (int i=0;i<emp.size();i++){
            //ret = (Emp) DataFactory.INSTANCE.create(Emp.class);
            ret = (Emp) SDOHelperContext.getHelperContext().getDataFactory().create(Emp.class);
            ret.setEname(emp.get(i).getEname());
            // todo do something with this to see if we get coversion exception again
          //  String returnMode = processControl.getReturnMode();
            ret.setJob(changeOperation);
            v.add(ret);

        }
        return v;
    }


    public Emp updateEmp(@WebParam(mode = WebParam.Mode.IN,
                                   name = "emp") Emp emp) {
        return null;  //Todo code me

    }
}
