package quant.serviceImpl;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;


import quant.domain.*;

//import com.COMP313.ISP.domain.Instrument;
import quant.service.LoadService;

import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import rx.Observable;

public class LoadCSVService implements LoadService {

	private String histDataPath;

	private Map<String, SortedMap<DateTime, HisData>> dataset = new HashMap<String, SortedMap<DateTime, HisData>>();

	public void setDataPath(String histDataPath, String funDataPath) {
		this.histDataPath = histDataPath;
	}


	@Override
	public Observable<Pair<DateTime, HisData>> loadOneSymbol(String symbol) throws IOException {

		String current = new java.io.File(".").getCanonicalPath();


		SortedMap<DateTime, HisData> result = CSVReaderRunner.readCsvForOneSymbol(histDataPath, symbol);

		List<Pair<DateTime, HisData>> list = new ArrayList<Pair<DateTime, HisData>>();

		for (Iterator<DateTime> i = result.keySet().iterator(); i.hasNext();) {
			DateTime item = i.next();
			Pair<DateTime, HisData> p = new ImmutablePair(item, result.get(item));
			list.add(p);
		}
		return Observable.from(list);

	}
	
	/*
	 * 
	 * read data from REST API
	 * 
	 * 
	 * 
	 * */
	
	public Observable<Pair<DateTime, HisData>> loadOneSymbolFromREST(String symbol) throws IOException {

		String current = new java.io.File(".").getCanonicalPath();


		//SortedMap<DateTime, HistoricalData2> result = CSVReaderRunner.readCsvForOneSymbol(histDataPath, symbol);

		List<Pair<DateTime, HisData>> list = new ArrayList<Pair<DateTime, HisData>>();

//		for (Iterator<DateTime> i = result.keySet().iterator(); i.hasNext();) {
//			DateTime item = i.next();
//			
//			Pair<DateTime, HistoricalData2> p = new ImmutablePair(item, result.get(item));
//			list.add(p);
//		}
		return Observable.from(list);

	}

	public List<String> loadSymbols(String path) throws IOException{
		return CSVReaderRunner.loadSymbols(path);

	
		
	}


}
