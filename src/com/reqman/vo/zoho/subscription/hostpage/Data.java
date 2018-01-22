package com.reqman.vo.zoho.subscription.hostpage;

import java.io.Serializable;

public class Data implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -1430138746732940200L;
	private Subscription subscription;

  public Subscription getSubscription() { return this.subscription; }

  public void setSubscription(Subscription subscription) { this.subscription = subscription; }

/*  private Invoice invoice;

  public Invoice getInvoice() { return this.invoice; }

  public void setInvoice(Invoice invoice) { this.invoice = invoice; }
  */
}
