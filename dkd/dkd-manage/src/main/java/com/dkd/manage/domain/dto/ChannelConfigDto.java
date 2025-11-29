package com.dkd.manage.domain.dto;


import lombok.Data;

import java.util.List;

@Data
public class ChannelConfigDto {

	private String channelCode; // 货道编号
	private List<ChannelSkuDto> channelList; // 货道dto集合
}
