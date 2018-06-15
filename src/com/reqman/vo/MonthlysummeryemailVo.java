package com.reqman.vo;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MonthlysummeryemailVo {
	
	private BigInteger countuser;
	private BigInteger totalrequestraise;
	private BigInteger totalrequestreceive;
	private BigDecimal feedbacktoreceive;
	private BigDecimal feedbacktoraise;

	
	
	
	
	
	public BigInteger getCountuser() {
		return countuser;
	}
	public void setCountuser(BigInteger countuser) {
		this.countuser = countuser;
	}
	public BigInteger getTotalrequestraise() {
		return totalrequestraise;
	}
	public void setTotalrequestraise(BigInteger totalrequestraise) {
		this.totalrequestraise = totalrequestraise;
	}
	public BigInteger getTotalrequestreceive() {
		return totalrequestreceive;
	}
	public void setTotalrequestreceive(BigInteger totalrequestreceive) {
		this.totalrequestreceive = totalrequestreceive;
	}
	public BigDecimal getFeedbacktoreceive() {
		return feedbacktoreceive;
	}
	public void setFeedbacktoreceive(BigDecimal feedbacktoreceive) {
		this.feedbacktoreceive = feedbacktoreceive;
	}
	public BigDecimal getFeedbacktoraise() {
		return feedbacktoraise;
	}
	public void setFeedbacktoraise(BigDecimal feedbacktoraise) {
		this.feedbacktoraise = feedbacktoraise;
	}

	
	
	
}
