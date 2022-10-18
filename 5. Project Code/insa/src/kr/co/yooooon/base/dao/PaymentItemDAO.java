package kr.co.yooooon.base.dao;

import java.util.*;

import kr.co.yooooon.base.to.*;

public interface PaymentItemDAO {
	public ArrayList<PaymentItemTO> findPaymentItemList(String salaryClassification, String paymentClassification, String year);
}
