package com.demo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 */
@Data
public class ResourceVo {
	private String resourceName;
	private Long resourceId;
	private String url;
	private List<ResourceVo> subs;
}
