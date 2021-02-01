package com.catchex.models;

import java.io.Serializable;


public enum OperationType
    implements Serializable
{
    INCOME_TRANSFER, OUTGOING_TRANSFER, DEBIT_CARD_PAYMENT, MOBILE_CODE_PAYMENT, CASH_WITHDRAWAL, COMMISSION, NOT_RESOLVED, REFUND, STANDING_ORDER, NOT_APPLICABLE
}
