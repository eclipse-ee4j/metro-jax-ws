/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;

import org.glassfish.jaxb.runtime.api.TypeReference;
import com.sun.xml.ws.api.model.JavaMethod;
import com.sun.xml.ws.api.model.Parameter;
import com.sun.xml.ws.api.model.ParameterBinding;
import com.sun.xml.ws.spi.db.RepeatedElementBridge;
import com.sun.xml.ws.spi.db.WrapperComposite;
import com.sun.xml.ws.spi.db.XMLBridge;
import com.sun.xml.ws.spi.db.TypeInfo;

import jakarta.jws.WebParam.Mode;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;
import java.util.List;

/**
 * runtime Parameter that abstracts the annotated java parameter
 *
 * <p>
 * A parameter may be bound to a header, a body, or an attachment.
 * Note that when it's bound to a body, it's bound to a body,
 * it binds to the whole payload.
 *
 * <p>
 * Sometimes multiple Java parameters are packed into the payload,
 * in which case the subclass {@link WrapperParameter} is used.
 *
 * @author Vivek Pandey
 */
public class ParameterImpl implements Parameter {

    private ParameterBinding binding;
    private ParameterBinding outBinding;
    private String partName;
    private final int index;
    private final Mode mode;
    /** @deprecated */
    @Deprecated
    private TypeReference typeReference;
    private TypeInfo typeInfo;
    private QName name;
    private final JavaMethodImpl parent;
    
    WrapperParameter wrapper;
    TypeInfo itemTypeInfo;

    public ParameterImpl(JavaMethodImpl parent, TypeInfo type, Mode mode, int index) {
        assert type != null;

        this.typeInfo = type;
        this.name = type.tagName;
        this.mode = mode;
        this.index = index;
        this.parent = parent;
    }

    @Override
    public AbstractSEIModelImpl getOwner() {
        return parent.owner;
    }

    @Override
    public JavaMethod getParent() {
        return parent;
    }

    /**
     * @return Returns the name.
     */
    @Override
    public QName getName() {
        return name;
    }
    
    public XMLBridge getXMLBridge() {
        return getOwner().getXMLBridge(typeInfo);
    }
    
    public XMLBridge getInlinedRepeatedElementBridge() {
        TypeInfo itemType = getItemType();
        if (itemType != null && itemType.getWrapperType() == null) {
            XMLBridge xb = getOwner().getXMLBridge(itemType);
            if (xb != null) return new RepeatedElementBridge(typeInfo, xb);
        }
        return null;
    }
    
    public TypeInfo getItemType() {
        if (itemTypeInfo != null) return itemTypeInfo;
        //RpcLit cannot inline repeated element in wrapper
        if (parent.getBinding().isRpcLit() || wrapper == null) return null;
        //InlinedRepeatedElementBridge is only used for dynamic wrapper (no wrapper class) 
        if (!WrapperComposite.class.equals(wrapper.getTypeInfo().type)) return null;
        if (!getBinding().isBody()) return null;
        itemTypeInfo = typeInfo.getItemType();
        return  itemTypeInfo;
    }

    /**
     * TODO: once the model gets JAXBContext, shouldn't {@code Bridge}s
     * be made available from model objects?
     * @deprecated use getTypeInfo
     * @return Returns the TypeReference associated with this Parameter
     */
    @Deprecated
    public TypeReference getTypeReference() {
        return typeReference;
    }
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * Sometimes we need to overwrite the typeReferenc, such as during patching for rpclit
     * @see AbstractSEIModelImpl#applyRpcLitParamBinding(JavaMethodImpl, WrapperParameter, WSDLBoundPortType, WebParam.Mode)
     * @deprecated 
     */
    @Deprecated
    void setTypeReference(TypeReference type){
        typeReference = type;
        name = type.tagName;
    }


    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public int getIndex() {
        return index;
    }

    /**
     * @return true if {@code this instanceof} {@link WrapperParameter}.
     */
    @Override
    public boolean isWrapperStyle() {
        return false;
    }

    @Override
    public boolean isReturnValue() {
        return index==-1;
    }

    /**
     * @return the Binding for this Parameter
     */
    @Override
    public ParameterBinding getBinding() {
        if(binding == null)
            return ParameterBinding.BODY;
        return binding;
    }

    /**
     */
    public void setBinding(ParameterBinding binding) {
        this.binding = binding;
    }

    public void setInBinding(ParameterBinding binding){
        this.binding = binding;
    }

    public void setOutBinding(ParameterBinding binding){
        this.outBinding = binding;
    }

    @Override
    public ParameterBinding getInBinding(){
        return binding;
    }

    @Override
    public ParameterBinding getOutBinding(){
        if(outBinding == null)
            return binding;
        return outBinding;
    }

    @Override
    public boolean isIN() {
        return mode==Mode.IN;
    }

    @Override
    public boolean isOUT() {
        return mode==Mode.OUT;
    }

    @Override
    public boolean isINOUT() {
        return mode==Mode.INOUT;
    }

    /**
     * If true, this parameter maps to the return value of a method invocation.
     *
     * <p>
     * {@link JavaMethodImpl#getResponseParameters()} is guaranteed to have
     * at most one such {@link ParameterImpl}. Note that there coule be none,
     * in which case the method returns {@code void}.
     */
    @Override
    public boolean isResponse() {
        return index == -1;
    }


    /**
     * Gets the holder value if applicable. To be called for inbound client side
     * message.
     * 
     * @return the holder value if applicable.
     */
    @Override
    public Object getHolderValue(Object obj) {
        if (obj instanceof Holder)
            return ((Holder) obj).value;
        return obj;
    }

    @Override
    public String getPartName() {
        if(partName == null)
            return name.getLocalPart();
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    void fillTypes(List<TypeInfo> types) {
        TypeInfo itemType = getItemType();
        if (itemType != null) {
            types.add(itemType);
            if (itemType.getWrapperType() != null) types.add(getTypeInfo());
        } else {
            types.add(getTypeInfo());
        }
    }
}
