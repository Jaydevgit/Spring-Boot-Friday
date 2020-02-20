package com.example.demo.base.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: Jay
 * Date: 2019/12/13 16:28
 */
@Data
public class PageTableRequest implements Serializable {
    private Integer page;   /*总共有几页*/
    private Integer limit;  /*每页有多少条记录*/
    private Integer offset;  /*一次查询的记录数*/

    public void countOffset(){  /*返回实际offset有多少值*/
        if (null==this.limit||null==this.page){
            this.offset=0;
            return;
        }
        this.offset=(this.page-1)*limit;
    }

}
