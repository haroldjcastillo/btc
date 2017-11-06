package com.github.haroldjcastillo.btc.dao;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.haroldjcastillo.btc.common.ObjectMapperFactory;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TradeResponse {
	
	private String success;
	
	private List<TradePayloadResponse> payload;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<TradePayloadResponse> getPayload() {
		return payload;
	}

	public void setPayload(List<TradePayloadResponse> payload) {
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		try {
			return ObjectMapperFactory.objectMapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
	
}
