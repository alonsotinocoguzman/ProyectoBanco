package com.project.bank.ProjectBank.Utils;

public class UIUtils {
  public static final String BASEURL = "/customer";
  public static final String CUSTOMER_ALL = "/find-all";
  public static final String CUSTOMER_ID = "/find-id/{customerId}";
  public static final String CUSTOMER_INS = "save";
  public static final String CUSTOMER_UPD = "update";
  public static final String CUSTOMER_DEL = "delete/{customerId}";

  public static final String BANKPRODUCT_BASEURL = "productbank";
  public static final String BANKPRODUCT_INS = "save";
  public static final String BANKPRODUCT_UPD = "update";
  public static final String BANKPRODUCT_DEL = "delete/{bankProductId}";
  public static final String BANKPRODUCT_ALL = "find-all";
  public static final String BANKPRODUCT_ID = "find-id/{bankProductId}";

  public static final String CUSTOMERTYPE_BASEURL = "customertype";
  public static final String CUSTOMERTYPE_INS = "save";
  public static final String CUSTOMERTYPE_UPD = "update";
  public static final String CUSTOMERTYPE_DEL = "delete/{customerTypeId}";
  public static final String CUSTOMERTYPE_ALL = "find-all";
  public static final String CUSTOMERTYPE_ID = "find-id/{customerTypeId}";
}