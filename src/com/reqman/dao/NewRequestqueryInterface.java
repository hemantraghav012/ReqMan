package com.reqman.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public interface NewRequestqueryInterface {
	
	public Map piechart(String userName)throws Exception;
	public Map<String, Double> barchart(String userName)throws Exception;
	public Map<String,BigDecimal> barchartforaverage(String userName)throws Exception;
	public Map<String, Double> barchartforcompleteddate(String userName)throws Exception;
}
