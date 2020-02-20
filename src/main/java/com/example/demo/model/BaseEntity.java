package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public abstract class BaseEntity<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 8925514045582235838L;
	private ID id;
	private Date createTime = new Date();
	@JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")  /*参考博客https://www.cnblogs.com/liukemng/p/3748137.html*/
	private Date updateTime = new Date();
}
