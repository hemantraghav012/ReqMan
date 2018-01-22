package com.reqman.vo.zoho.subscription.hostpage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Subscription implements Serializable
{

	private static final long serialVersionUID = -5975876355696605609L;
	
	private Date updated_time;
	
	private String reference_id;
	
	private String created_at;
	
	private String next_billing_at;
	
	private int payment_terms;

	private String last_billing_at;
	
	  private String currency_code;
	
	  private String subscription_id;
	
	  private String expires_at;
	
	  private String interval_unit;




	
	public Date getUpdatedTime() { return this.updated_time; }
	
	public void setUpdatedTime(Date updated_time) { this.updated_time = updated_time; }

/*  private ArrayList<Object> notes;

  public ArrayList<Object> getNotes() { return this.notes; }

  public void setNotes(ArrayList<Object> notes) { this.notes = notes; }
*/

  public String getReferenceId() { return this.reference_id; }

  public void setReferenceId(String reference_id) { this.reference_id = reference_id; }

/*  private ArrayList<Object> addons;

  public ArrayList<Object> getAddons() { return this.addons; }

  public void setAddons(ArrayList<Object> addons) { this.addons = addons; }
*/

  public String getCreatedAt() { return this.created_at; }

  public void setCreatedAt(String created_at) { this.created_at = created_at; }


  public String getNextBillingAt() { return this.next_billing_at; }

  public void setNextBillingAt(String next_billing_at) { this.next_billing_at = next_billing_at; }

/*  private ArrayList<Object> taxes;

  public ArrayList<Object> getTaxes() { return this.taxes; }

  public void setTaxes(ArrayList<Object> taxes) { this.taxes = taxes; }
*/

  public int getPaymentTerms() { return this.payment_terms; }

  public void setPaymentTerms(int payment_terms) { this.payment_terms = payment_terms; }


  public String getLastBillingAt() { return this.last_billing_at; }

  public void setLastBillingAt(String last_billing_at) { this.last_billing_at = last_billing_at; }

 
  public String getCurrencyCode() { return this.currency_code; }

  public void setCurrencyCode(String currency_code) { this.currency_code = currency_code; }

  public String getSubscriptionId() { return this.subscription_id; }

  public void setSubscriptionId(String subscription_id) { this.subscription_id = subscription_id; }

  public String getExpiresAt() { return this.expires_at; }

  public void setExpiresAt(String expires_at) { this.expires_at = expires_at; }

  public String getIntervalUnit() { return this.interval_unit; }

  public void setIntervalUnit(String interval_unit) { this.interval_unit = interval_unit; }

  private boolean end_of_term;

  public boolean getEndOfTerm() { return this.end_of_term; }

  public void setEndOfTerm(boolean end_of_term) { this.end_of_term = end_of_term; }

  private String product_id;

  public String getProductId() { return this.product_id; }

  public void setProductId(String product_id) { this.product_id = product_id; }

  private boolean can_add_bank_account;

  public boolean getCanAddBankAccount() { return this.can_add_bank_account; }

  public void setCanAddBankAccount(boolean can_add_bank_account) { this.can_add_bank_account = can_add_bank_account; }

  private Plan plan;

  public Plan getPlan() { return this.plan; }

  public void setPlan(Plan plan) { this.plan = plan; }

  private String pricebook_id;

  public String getPricebookId() { return this.pricebook_id; }

  public void setPricebookId(String pricebook_id) { this.pricebook_id = pricebook_id; }

  private ArrayList<PaymentGateway> payment_gateways;

  public ArrayList<PaymentGateway> getPaymentGateways() { return this.payment_gateways; }

  public void setPaymentGateways(ArrayList<PaymentGateway> payment_gateways) { this.payment_gateways = payment_gateways; }

  private Date created_time;

  public Date getCreatedTime() { return this.created_time; }

  public void setCreatedTime(Date created_time) { this.created_time = created_time; }

  private int amount;

  public int getAmount() { return this.amount; }

  public void setAmount(int amount) { this.amount = amount; }

  private int exchange_rate;

  public int getExchangeRate() { return this.exchange_rate; }

  public void setExchangeRate(int exchange_rate) { this.exchange_rate = exchange_rate; }

  private String currency_symbol;

  public String getCurrencySymbol() { return this.currency_symbol; }

  public void setCurrencySymbol(String currency_symbol) { this.currency_symbol = currency_symbol; }

  private boolean is_inclusive_tax;

  public boolean getIsInclusiveTax() { return this.is_inclusive_tax; }

  public void setIsInclusiveTax(boolean is_inclusive_tax) { this.is_inclusive_tax = is_inclusive_tax; }

  private String current_term_starts_at;

  public String getCurrentTermStartsAt() { return this.current_term_starts_at; }

  public void setCurrentTermStartsAt(String current_term_starts_at) { this.current_term_starts_at = current_term_starts_at; }

/*  private ArrayList<Object> custom_fields;

  public ArrayList<Object> getCustomFields() { return this.custom_fields; }

  public void setCustomFields(ArrayList<Object> custom_fields) { this.custom_fields = custom_fields; }
*/
  private String current_term_ends_at;

  public String getCurrentTermEndsAt() { return this.current_term_ends_at; }

  public void setCurrentTermEndsAt(String current_term_ends_at) { this.current_term_ends_at = current_term_ends_at; }

  private String product_name;

  public String getProductName() { return this.product_name; }

  public void setProductName(String product_name) { this.product_name = product_name; }

  private String activated_at;

  public String getActivatedAt() { return this.activated_at; }

  public void setActivatedAt(String activated_at) { this.activated_at = activated_at; }

  private String salesperson_name;

  public String getSalespersonName() { return this.salesperson_name; }

  public void setSalespersonName(String salesperson_name) { this.salesperson_name = salesperson_name; }

  private String salesperson_id;

  public String getSalespersonId() { return this.salesperson_id; }

  public void setSalespersonId(String salesperson_id) { this.salesperson_id = salesperson_id; }

  private String child_invoice_id;

  public String getChildInvoiceId() { return this.child_invoice_id; }

  public void setChildInvoiceId(String child_invoice_id) { this.child_invoice_id = child_invoice_id; }

  private ArrayList<Contactperson> contactpersons;

  public ArrayList<Contactperson> getContactpersons() { return this.contactpersons; }

  public void setContactpersons(ArrayList<Contactperson> contactpersons) { this.contactpersons = contactpersons; }

  private boolean auto_collect;

  public boolean getAutoCollect() { return this.auto_collect; }

  public void setAutoCollect(boolean auto_collect) { this.auto_collect = auto_collect; }

  private String name;

  public String getName() { return this.name; }

  public void setName(String name) { this.name = name; }

  private int sub_total;

  public int getSubTotal() { return this.sub_total; }

  public void setSubTotal(int sub_total) { this.sub_total = sub_total; }

  private int interval;

  public int getInterval() { return this.interval; }

  public void setInterval(int interval) { this.interval = interval; }

  private Card card;

  public Card getCard() { return this.card; }

  public void setCard(Card card) { this.card = card; }

  private String status;

  public String getStatus() { return this.status; }

  public void setStatus(String status) { this.status = status; }

  private String payment_terms_label;

  public String getPaymentTermsLabel() { return this.payment_terms_label; }

  public void setPaymentTermsLabel(String payment_terms_label) { this.payment_terms_label = payment_terms_label; }

  private Customer customer;

  public Customer getCustomer() { return this.customer; }

  public void setCustomer(Customer customer) { this.customer = customer; }
}


