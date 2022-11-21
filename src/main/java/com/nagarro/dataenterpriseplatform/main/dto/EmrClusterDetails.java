package com.nagarro.dataenterpriseplatform.main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmrClusterDetails {

	private String clusterId;
	private String clusterName;
	private String status;
}
