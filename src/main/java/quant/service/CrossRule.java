package quant.service;

import rx.Observable;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import quant.serviceImpl.RuleImplService;

public interface CrossRule extends Rule{
	
	public void setClose(Observable<Pair<DateTime,Double>> close);
	
	public void setBase(Observable<Pair<DateTime,Double>> base);
	
	public void setN(int n);
	
	public Observable<Pair<DateTime,Double>> runRule(RuleImplService ruleUtils);

	public Observable<Pair<DateTime, Double>> runRule2(RuleImplService ruleUtils);
}
