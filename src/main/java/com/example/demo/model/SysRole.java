package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Author: Jay
 * Date: 2019/12/13 9:48
 */
@Data
@EqualsAndHashCode(callSuper = true)  /*使它能够使用父类中定义的属性和方法*/
public class SysRole extends BaseEntity<Integer> {

    private static final long serialVersionUID = -6525908145032868837L;

    private String name;
    private String description;

    @Override
    public String toString(){
        return "SysRole{'"+
                "name="+name+'\''+
                ",description='"+description+'\''+
                '}';
    }
}
