package com.reqman.vo.zoho.subscription.hostpage;

public class Card
{
  private int expiry_month;

  public int getExpiryMonth() { return this.expiry_month; }

  public void setExpiryMonth(int expiry_month) { this.expiry_month = expiry_month; }

  private String payment_gateway;

  public String getPaymentGateway() { return this.payment_gateway; }

  public void setPaymentGateway(String payment_gateway) { this.payment_gateway = payment_gateway; }

  private String last_four_digits;

  public String getLastFourDigits() { return this.last_four_digits; }

  public void setLastFourDigits(String last_four_digits) { this.last_four_digits = last_four_digits; }

  private String card_id;

  public String getCardId() { return this.card_id; }

  public void setCardId(String card_id) { this.card_id = card_id; }

  private int expiry_year;

  public int getExpiryYear() { return this.expiry_year; }

  public void setExpiryYear(int expiry_year) { this.expiry_year = expiry_year; }
}

