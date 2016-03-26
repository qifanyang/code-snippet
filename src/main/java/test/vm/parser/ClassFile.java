package test.vm.parser;

import org.aopalliance.intercept.MethodInterceptor;
import test.vm.parser.cp.ConstantPoolInfo;

/**
 * Title:字节码数据结构
 * Description:
 * Copyright: Copyright (c) 2012
 * Company: shishike Technology(Beijing) Chengdu Co. Ltd.
 *
 * @author yangqf
 * @version 1.0 2016/3/26
 */
public class ClassFile {
    private int magic;
    private short minor_version;
    private short major_version;
    private short constant_pool_count;
    private ConstantPoolInfo constant_pool_info[];
    private short access_flags;
    private short this_class;
    private short super_class;
    private short interfaces_count;
    private short interfaces[];
    private short fields_count;
    private FieldInfo fields[];
    private short methods_count;
    private MethodInterceptor methods[];
    private short attributes_count;
    private AttributeInfo attributes[];

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public short getMinor_version() {
        return minor_version;
    }

    public void setMinor_version(short minor_version) {
        this.minor_version = minor_version;
    }

    public short getMajor_version() {
        return major_version;
    }

    public void setMajor_version(short major_version) {
        this.major_version = major_version;
    }

    public short getConstant_pool_count() {
        return constant_pool_count;
    }

    public void setConstant_pool_count(short constant_pool_count) {
        this.constant_pool_count = constant_pool_count;
    }

    public ConstantPoolInfo[] getConstant_pool_info() {
        return constant_pool_info;
    }

    public void setConstant_pool_info(ConstantPoolInfo[] cp_info) {
        this.constant_pool_info = cp_info;
    }

    public short getAccess_flags() {
        return access_flags;
    }

    public void setAccess_flags(short access_flags) {
        this.access_flags = access_flags;
    }

    public short getThis_class() {
        return this_class;
    }

    public void setThis_class(short this_class) {
        this.this_class = this_class;
    }

    public short getSuper_class() {
        return super_class;
    }

    public void setSuper_class(short super_class) {
        this.super_class = super_class;
    }

    public short getInterfaces_count() {
        return interfaces_count;
    }

    public void setInterfaces_count(short interfaces_count) {
        this.interfaces_count = interfaces_count;
    }

    public short[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(short[] interfaces) {
        this.interfaces = interfaces;
    }

    public short getFields_count() {
        return fields_count;
    }

    public void setFields_count(short fields_count) {
        this.fields_count = fields_count;
    }

    public FieldInfo[] getFields() {
        return fields;
    }

    public void setFields(FieldInfo[] fields) {
        this.fields = fields;
    }

    public short getMethods_count() {
        return methods_count;
    }

    public void setMethods_count(short methods_count) {
        this.methods_count = methods_count;
    }

    public MethodInterceptor[] getMethods() {
        return methods;
    }

    public void setMethods(MethodInterceptor[] methods) {
        this.methods = methods;
    }

    public short getAttributes_count() {
        return attributes_count;
    }

    public void setAttributes_count(short attributes_count) {
        this.attributes_count = attributes_count;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributeInfo[] attributes) {
        this.attributes = attributes;
    }
}
