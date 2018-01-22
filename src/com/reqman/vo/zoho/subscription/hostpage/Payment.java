package com.reqman.vo.zoho.subscription.hostpage;

public class Payment
{
  private String date;

  public String getDate() { return this.date; }

  public void setDate(String date) { this.date = date; }

  private String payment_mode;

  public String getPaymentMode() { return this.payment_mode; }

  public void setPaymentMode(String payment_mode) { this.payment_mode = payment_mode; }

  private int amount;

  public int getAmount() { return this.amount; }

  public void setAmount(int amount) { this.amount = amount; }

  private int exchange_rate;

  public int getExchangeRate() { return this.exchange_rate; }

  public void setExchangeRate(int exchange_rate) { this.exchange_rate = exchange_rate; }

  private String description;

  public String getDescription() { return this.description; }

  public void setDescription(String description) { this.description = description; }

  private String invoice_payment_id;

  public String getInvoicePaymentId() { return this.invoice_payment_id; }

  public void setInvoicePaymentId(String invoice_payment_id) { this.invoice_payment_id = invoice_payment_id; }

  private String card_type;

  public String getCardType() { return this.card_type; }

  public void setCardType(String card_type) { this.card_type = card_type; }

  private String reference_number;

  public String getReferenceNumber() { return this.reference_number; }

  public void setReferenceNumber(String reference_number) { this.reference_number = reference_number; }

  private int amount_refunded;

  public int getAmountRefunded() { return this.amount_refunded; }

  public void setAmountRefunded(int amount_refunded) { this.amount_refunded = amount_refunded; }

  private String gateway_transaction_id;

  public String getGatewayTransactionId() { return this.gateway_transaction_id; }

  public void setGatewayTransactionId(String gateway_transaction_id) { this.gateway_transaction_id = gateway_transaction_id; }

  private int bank_charges;

  public int getBankCharges() { return this.bank_charges; }

  public void setBankCharges(int bank_charges) { this.bank_charges = bank_charges; }

  private String payment_id;

  public String getPaymentId() { return this.payment_id; }

  public void setPaymentId(String payment_id) { this.payment_id = payment_id; }

  private String last_four_digits;

  public String getLastFourDigits() { return this.last_four_digits; }

  public void setLastFourDigits(String last_four_digits) { this.last_four_digits = last_four_digits; }
}


