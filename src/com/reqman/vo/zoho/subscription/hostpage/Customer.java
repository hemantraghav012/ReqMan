package com.reqman.vo.zoho.subscription.hostpage;

import java.io.Serializable;

public class Customer implements Serializable
{
 
  private static final long serialVersionUID = -4438600214736460730L;
  
  private String website;

  public String getWebsite() { return this.website; }

  public void setWebsite(String website) { this.website = website; }

  private String zcrm_account_id;

  public String getZcrmAccountId() { return this.zcrm_account_id; }

  public void setZcrmAccountId(String zcrm_account_id) { this.zcrm_account_id = zcrm_account_id; }

/*  private ArrayList<Object> custom_fields;

  public ArrayList<Object> getCustomFields() { return this.custom_fields; }

  public void setCustomFields(ArrayList<Object> custom_fields) { this.custom_fields = custom_fields; }
*/
  private String last_name;

  public String getLastName() { return this.last_name; }

  public void setLastName(String last_name) { this.last_name = last_name; }

  private BillingAddress billing_address;

  public BillingAddress getBillingAddress() { return this.billing_address; }

  public void setBillingAddress(BillingAddress billing_address) { this.billing_address = billing_address; }

  private String ip_address;

  public String getIpAddress() { return this.ip_address; }

  public void setIpAddress(String ip_address) { this.ip_address = ip_address; }

  private String display_name;

  public String getDisplayName() { return this.display_name; }

  public void setDisplayName(String display_name) { this.display_name = display_name; }

  private int payment_terms;

  public int getPaymentTerms() { return this.payment_terms; }

  public void setPaymentTerms(int payment_terms) { this.payment_terms = payment_terms; }

  private String zcrm_contact_id;

  public String getZcrmContactId() { return this.zcrm_contact_id; }

  public void setZcrmContactId(String zcrm_contact_id) { this.zcrm_contact_id = zcrm_contact_id; }

  private String company_name;

  public String getCompanyName() { return this.company_name; }

  public void setCompanyName(String company_name) { this.company_name = company_name; }

  private String salutation;

  public String getSalutation() { return this.salutation; }

  public void setSalutation(String salutation) { this.salutation = salutation; }

  private ShippingAddress shipping_address;

  public ShippingAddress getShippingAddress() { return this.shipping_address; }

  public void setShippingAddress(ShippingAddress shipping_address) { this.shipping_address = shipping_address; }

  private String customer_id;

  public String getCustomerId() { return this.customer_id; }

  public void setCustomerId(String customer_id) { this.customer_id = customer_id; }

  private String first_name;

  public String getFirstName() { return this.first_name; }

  public void setFirstName(String first_name) { this.first_name = first_name; }

  private String email;

  public String getEmail() { return this.email; }

  public void setEmail(String email) { this.email = email; }

  private String payment_terms_label;

  public String getPaymentTermsLabel() { return this.payment_terms_label; }

  public void setPaymentTermsLabel(String payment_terms_label) { this.payment_terms_label = payment_terms_label; }
}

