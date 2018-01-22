package com.reqman.vo.zoho.subscription.hostpage;

import java.io.Serializable;

public class Data1 implements Serializable
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -1430138746732940200L;
	private Subscription1 subscription;

  public Subscription1 getSubscription() { return this.subscription; }

  public void setSubscription(Subscription1 subscription) { this.subscription = subscription; }

/*  private Invoice invoice;

  public Invoice getInvoice() { return this.invoice; }

  public void setInvoice(Invoice invoice) { this.invoice = invoice; }
  */
}
