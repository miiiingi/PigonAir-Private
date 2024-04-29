package com.example.pigonair.global.config.jmeter;

import org.springframework.stereotype.Service;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JmeterService {

	public void setTransactionNameBasedOnJMeterTag(HttpServletRequest request) {
		Transaction transaction = ElasticApm.currentTransaction();
		String threadGroupName = request.getHeader("X-ThreadGroup-Name");
		if (threadGroupName != null && !threadGroupName.isEmpty()) {
			transaction.setName("Transaction-" + threadGroupName);
			transaction.addLabel("ThreadGroup", threadGroupName);
		}
	}
}
