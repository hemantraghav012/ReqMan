package com.reqman.vo.zoho.subscription.hostpage;

import java.util.ArrayList;
import java.util.Date;

public class Invoice
{
  private String updated_time;

  public String getUpdatedTime() { return this.updated_time; }

  public void setUpdatedTime(String updated_time) { this.updated_time = updated_time; }

  private ArrayList<Subscription2> subscriptions;

  public ArrayList<Subscription2> getSubscriptions() { return this.subscriptions; }

  public void setSubscriptions(ArrayList<Subscription2> subscriptions) { this.subscriptions = subscriptions; }

  private String notes;

  public String getNotes() { return this.notes; }

  public void setNotes(String notes) { this.notes = notes; }

  private String client_viewed_time;

  public String getClientViewedTime() { return this.client_viewed_time; }

  public void setClientViewedTime(String client_viewed_time) { this.client_viewed_time = client_viewed_time; }

  private ArrayList<Object> taxes;

  public ArrayList<Object> getTaxes() { return this.taxes; }

  public void setTaxes(ArrayList<Object> taxes) { this.taxes = taxes; }

  private BillingAddress2 billing_address;

  public BillingAddress2 getBillingAddress() { return this.billing_address; }

  public void setBillingAddress(BillingAddress2 billing_address) { this.billing_address = billing_address; }

  private String number;

  public String getNumber() { return this.number; }

  public void setNumber(String number) { this.number = number; }

  private int balance;

  public int getBalance() { return this.balance; }

  public void setBalance(int balance) { this.balance = balance; }

  private ArrayList<Object> coupons;

  public ArrayList<Object> getCoupons() { return this.coupons; }

  public void setCoupons(ArrayList<Object> coupons) { this.coupons = coupons; }

  private String terms;

  public String getTerms() { return this.terms; }

  public void setTerms(String terms) { this.terms = terms; }

  private int credits_applied;

  public int getCreditsApplied() { return this.credits_applied; }

  public void setCreditsApplied(int credits_applied) { this.credits_applied = credits_applied; }

  private String invoice_id;

  public String getInvoiceId() { return this.invoice_id; }

  public void setInvoiceId(String invoice_id) { this.invoice_id = invoice_id; }

  private String template_type;

  public String getTemplateType() { return this.template_type; }

  public void setTemplateType(String template_type) { this.template_type = template_type; }

  private boolean stop_reminder_until_payment_expected_date;

  public boolean getStopReminderUntilPaymentExpectedDate() { return this.stop_reminder_until_payment_expected_date; }

  public void setStopReminderUntilPaymentExpectedDate(boolean stop_reminder_until_payment_expected_date) { this.stop_reminder_until_payment_expected_date = stop_reminder_until_payment_expected_date; }

  private Date created_time;

  public Date getCreatedTime() { return this.created_time; }

  public void setCreatedTime(Date created_time) { this.created_time = created_time; }

  private boolean is_inclusive_tax;

  public boolean getIsInclusiveTax() { return this.is_inclusive_tax; }

  public void setIsInclusiveTax(boolean is_inclusive_tax) { this.is_inclusive_tax = is_inclusive_tax; }

  private ArrayList<Object> custom_fields;

  public ArrayList<Object> getCustomFields() { return this.custom_fields; }

  public void setCustomFields(ArrayList<Object> custom_fields) { this.custom_fields = custom_fields; }

  private ArrayList<Object> customer_custom_fields;

  public ArrayList<Object> getCustomerCustomFields() { return this.customer_custom_fields; }

  public void setCustomerCustomFields(ArrayList<Object> customer_custom_fields) { this.customer_custom_fields = customer_custom_fields; }

  private String transaction_type;

  public String getTransactionType() { return this.transaction_type; }

  public void setTransactionType(String transaction_type) { this.transaction_type = transaction_type; }

  private int price_precision;

  public int getPricePrecision() { return this.price_precision; }

  public void setPricePrecision(int price_precision) { this.price_precision = price_precision; }

  private String status;

  public String getStatus() { return this.status; }

  public void setStatus(String status) { this.status = status; }

  private int tax_total;

  public int getTaxTotal() { return this.tax_total; }

  public void setTaxTotal(int tax_total) { this.tax_total = tax_total; }

  private ArrayList<InvoiceItem> invoice_items;

  public ArrayList<InvoiceItem> getInvoiceItems() { return this.invoice_items; }

  public void setInvoiceItems(ArrayList<InvoiceItem> invoice_items) { this.invoice_items = invoice_items; }

  private boolean is_viewed_by_client;

  public boolean getIsViewedByClient() { return this.is_viewed_by_client; }

  public void setIsViewedByClient(boolean is_viewed_by_client) { this.is_viewed_by_client = is_viewed_by_client; }

  private int write_off_amount;

  public int getWriteOffAmount() { return this.write_off_amount; }

  public void setWriteOffAmount(int write_off_amount) { this.write_off_amount = write_off_amount; }

  private ArrayList<Payment> payments;

  public ArrayList<Payment> getPayments() { return this.payments; }

  public void setPayments(ArrayList<Payment> payments) { this.payments = payments; }

  private boolean ach_payment_initiated;

  public boolean getAchPaymentInitiated() { return this.ach_payment_initiated; }

  public void setAchPaymentInitiated(boolean ach_payment_initiated) { this.ach_payment_initiated = ach_payment_initiated; }

  private int payment_terms;

  public int getPaymentTerms() { return this.payment_terms; }

  public void setPaymentTerms(int payment_terms) { this.payment_terms = payment_terms; }

  private String currency_code;

  public String getCurrencyCode() { return this.currency_code; }

  public void setCurrencyCode(String currency_code) { this.currency_code = currency_code; }

  private String page_width;

  public String getPageWidth() { return this.page_width; }

  public void setPageWidth(String page_width) { this.page_width = page_width; }

  private String invoice_date;

  public String getInvoiceDate() { return this.invoice_date; }

  public void setInvoiceDate(String invoice_date) { this.invoice_date = invoice_date; }

  private int total;

  public int getTotal() { return this.total; }

  public void setTotal(int total) { this.total = total; }

  private ArrayList<Object> credits;

  public ArrayList<Object> getCredits() { return this.credits; }

  public void setCredits(ArrayList<Object> credits) { this.credits = credits; }

  private CustomFieldHash custom_field_hash;

  public CustomFieldHash getCustomFieldHash() { return this.custom_field_hash; }

  public void setCustomFieldHash(CustomFieldHash custom_field_hash) { this.custom_field_hash = custom_field_hash; }

  private boolean auto_reminders_configured;

  public boolean getAutoRemindersConfigured() { return this.auto_reminders_configured; }

  public void setAutoRemindersConfigured(boolean auto_reminders_configured) { this.auto_reminders_configured = auto_reminders_configured; }

  private ShippingAddress2 shipping_address;

  public ShippingAddress2 getShippingAddress() { return this.shipping_address; }

  public void setShippingAddress(ShippingAddress2 shipping_address) { this.shipping_address = shipping_address; }

  private String email;

  public String getEmail() { return this.email; }

  public void setEmail(String email) { this.email = email; }

  private ArrayList<PaymentGateway2> payment_gateways;

  public ArrayList<PaymentGateway2> getPaymentGateways() { return this.payment_gateways; }

  public void setPaymentGateways(ArrayList<PaymentGateway2> payment_gateways) { this.payment_gateways = payment_gateways; }

  private ArrayList<Comment> comments;

  public ArrayList<Comment> getComments() { return this.comments; }

  public void setComments(ArrayList<Comment> comments) { this.comments = comments; }

  private String currency_symbol;

  public String getCurrencySymbol() { return this.currency_symbol; }

  public void setCurrencySymbol(String currency_symbol) { this.currency_symbol = currency_symbol; }

  private String due_date;

  public String getDueDate() { return this.due_date; }

  public void setDueDate(String due_date) { this.due_date = due_date; }

  private int no_of_copies;

  public int getNoOfCopies() { return this.no_of_copies; }

  public void setNoOfCopies(int no_of_copies) { this.no_of_copies = no_of_copies; }

  private String payment_expected_date;

  public String getPaymentExpectedDate() { return this.payment_expected_date; }

  public void setPaymentExpectedDate(String payment_expected_date) { this.payment_expected_date = payment_expected_date; }

  private String salesperson_name;

  public String getSalespersonName() { return this.salesperson_name; }

  public void setSalespersonName(String salesperson_name) { this.salesperson_name = salesperson_name; }

  private String salesperson_id;

  public String getSalespersonId() { return this.salesperson_id; }

  public void setSalespersonId(String salesperson_id) { this.salesperson_id = salesperson_id; }

  private String template_name;

  public String getTemplateName() { return this.template_name; }

  public void setTemplateName(String template_name) { this.template_name = template_name; }

  private int payment_made;

  public int getPaymentMade() { return this.payment_made; }

  public void setPaymentMade(int payment_made) { this.payment_made = payment_made; }

  private ArrayList<Contactperson2> contactpersons;

  public ArrayList<Contactperson2> getContactpersons() { return this.contactpersons; }

  public void setContactpersons(ArrayList<Contactperson2> contactpersons) { this.contactpersons = contactpersons; }

  private int sub_total;

  public int getSubTotal() { return this.sub_total; }

  public void setSubTotal(int sub_total) { this.sub_total = sub_total; }

  private String template_id;

  public String getTemplateId() { return this.template_id; }

  public void setTemplateId(String template_id) { this.template_id = template_id; }

  private String customer_name;

  public String getCustomerName() { return this.customer_name; }

  public void setCustomerName(String customer_name) { this.customer_name = customer_name; }

  private String customer_id;

  public String getCustomerId() { return this.customer_id; }

  public void setCustomerId(String customer_id) { this.customer_id = customer_id; }

  private String invoice_url;

  public String getInvoiceUrl() { return this.invoice_url; }

  public void setInvoiceUrl(String invoice_url) { this.invoice_url = invoice_url; }

  private boolean show_no_of_copies;

  public boolean getShowNoOfCopies() { return this.show_no_of_copies; }

  public void setShowNoOfCopies(boolean show_no_of_copies) { this.show_no_of_copies = show_no_of_copies; }

  private boolean payment_reminder_enabled;

  public boolean getPaymentReminderEnabled() { return this.payment_reminder_enabled; }

  public void setPaymentReminderEnabled(boolean payment_reminder_enabled) { this.payment_reminder_enabled = payment_reminder_enabled; }

  private String payment_terms_label;

  public String getPaymentTermsLabel() { return this.payment_terms_label; }

  public void setPaymentTermsLabel(String payment_terms_label) { this.payment_terms_label = payment_terms_label; }
}

