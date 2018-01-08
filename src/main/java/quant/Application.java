package quant;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import quant.config.InputParameterSet;
import quant.serviceImpl.LargerThanRule;
import quant.serviceImpl.MathOperatorService2;
import quant.serviceImpl.RESTdataReader;
import quant.serviceImpl.RESTdataReaderV;
import quant.serviceImpl.RuleImplService;
import rx.Observable;
import rx.functions.Action1;
import quant.config.InputParameterSet;
import quant.domain.HisData;
import quant.serviceImpl.CrossDownRule;
import quant.serviceImpl.CrossUpRule;

import quant.serviceImpl.RuleImplService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import quant.service.CrossRule;
import quant.service.LoadService;
import quant.config.MyConfiguration;

@SpringBootApplication
@Import(MyConfiguration.class)
@RestController
public class Application {
	
	
	@Autowired
	private CrossRule getCrossUPRule;
	
	@Autowired
	private CrossRule getCrossDownRule;

	@Autowired
	private RuleImplService getRuleImplService;


	@Autowired
	private MathOperatorService2 getMathOperatorService;

	@Autowired
	private InputParameterSet getInputParameterSet;

	@Autowired
	private Map<String, Map<String, Map<String, String>>> initData;
	
	@Autowired
	private Map<String,Map<String,List<String>>> finalData;

	@Autowired
	private DateTimeFormatter getDateTimeFormatter;

	@Autowired
	private LargerThanRule getLargerThanRule;

	@Autowired
	private List<String> getSymbols;

	@Autowired
	private DateFormat getDateFormat;
	
	@Autowired
	private LoadService loadservice;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/GOOG/SMA")
	public String signal() {
    	
    		Gson gson = new Gson();
    		Map<String, String> data = initData.get("GOOG").get("SMASIG");
    		String json = gson.toJson(data);
    		return json;
    	
	}
    
    @RequestMapping("/GOOG/VMA")
   	public String vma() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("VMASIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    
    @RequestMapping("/GOOG/RSI")
	public String rsi() {
    	
    		Gson gson = new Gson();
    		Map<String, String> data = initData.get("GOOG").get("RSISIG");
    		String json = gson.toJson(data);
    		return json;
    	
	}
    
    @RequestMapping("/GOOG/KDJ")
	public String KDJ() {
    	
    		Gson gson = new Gson();
    		Map<String, String> data = initData.get("GOOG").get("KDJSIG");
    		String json = gson.toJson(data);
    		return json;
    	
	}
    
    @RequestMapping("/GOOG/WILLIAM")
   	public String WILLIAM() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("WILLIAMSIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    
    @RequestMapping("/GOOG/BOLL")
   	public String BOLLSIG() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("BOLLSIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    
    @RequestMapping("/GOOG/MACD")
   	public String MACDSIG() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("MACDSIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    
    @RequestMapping("/GOOG/HIGH")
   	public String HIGHSIG() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("HIGHSIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    
    @RequestMapping("/GOOG/REX")
   	public String REXSIG() {
       	
       		Gson gson = new Gson();
       		Map<String, String> data = initData.get("GOOG").get("REXSIG");
       		String json = gson.toJson(data);
       		return json;
       	
   	}
    @CrossOrigin
    @RequestMapping("/indicators")
   	public String indicators() {
    	InputParameterSet params = getInputParameterSet;
    	String date = params.getIndicatorDate();
    	
    	Map<String,List<String>> indictors = finalData.get(date);
    	indictors.put("\"SMA\"", indictors.get("SMASIG"));
    	indictors.put("\"MACD\"", indictors.get("MACDSIG"));
    	indictors.put("\"BOLL\"", indictors.get("BOLLSIG"));
    	indictors.put("\"RSI\"", indictors.get("RSISIG"));
    	indictors.put("\"KDJ\"", indictors.get("KDJSIG"));
    	indictors.put("\"REX\"", indictors.get("REXSIG"));
    	indictors.put("\"VMA\"", indictors.get("VMASIG"));
    	indictors.put("\"HIGH\"", indictors.get("HIGHSIG"));
//    	Map<String,String> indictors2 = new HashMap<String,String>();
//    	indictors2.put("SMA","A");
    	
    	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//		Map<String, String> data = service.getOne(symbol);
//		System.out.println(data);
    	System.out.println(indictors.toString());
		String json = gson.toJson(indictors.toString().replace("=", ":"));
		return indictors.toString().replace("=", ":");
       	
   	}
    
    public Map<String,List<String>> getIndictors(String date)
    {
    	if(finalData.containsKey(date))
    	{
    		return finalData.get(date);
    	}
    	else{
    		return null;
    	}
    	
    }
    
    
    
    //the key is the date
    public void transform()
    {
    	for(String symbol: initData.keySet())
    	{
    		//name: time , value
    		Map<String, Map<String, String>> signals  = initData.get(symbol);
    		
    		for(String indicator: signals.keySet())
    		{
    			Map<String, String> timeMap = signals.get(indicator);
    			
    			for(String t:timeMap.keySet())
    			{
    				if(!finalData.containsKey(t))
    				{
    					Map<String,List<String>> one = new HashMap<String,List<String>>();
    					List<String> symbolList = new ArrayList<String>();
    					symbolList.add("\""+symbol+"\"");
    					one.put(indicator, symbolList);
    					finalData.put(t, one);
    				}
    				else
    				{
    					//Map<String,Map<String,List<String>>> final
    					Map<String,List<String>> ind = finalData.get(t);
    					if(!ind.containsKey(indicator))
    					{
    						List<String> symbolList = new ArrayList<String>();
    						symbolList.add("\""+symbol+"\"");
    						ind.put(indicator, symbolList);
    					}
    					else
    					{
    						List<String> symbolList  = ind.get(indicator);
    						symbolList.add("\""+symbol+"\"");
    						
    						ind.put(indicator, symbolList);
    					}
    					
    					
    				}
    			}
    		}
    		
    	}
    	
    }
    
    @Bean
	InitializingBean compute() {
		return () -> {
			DateFormat df = getDateFormat;
			List<String> symbols = getSymbols;
//			List<String> symbols = new ArrayList<String>();
//			symbols.add("GOOG");
			InputParameterSet params = getInputParameterSet;
			DateTimeFormatter fmt = getDateTimeFormatter;
			RuleImplService util = getRuleImplService;
			//read data from network api call
			//RESTdataReaderV reader = new RESTdataReaderV();
	    	//Observable<Pair<DateTime, HisData>> data = reader.readOneSymbol("http://localhost:8090/api/stockhis", "TK");
	    	
			
			MathOperatorService2 mathService = getMathOperatorService;

//			long startTime_ = System.nanoTime();
//
			for (String symbol : symbols) {
				long startTime = System.nanoTime();
				try {
					Observable<Pair<DateTime, HisData>> data = loadservice.loadOneSymbol(symbol);
					Observable<Pair<DateTime, Double>> close = mathService.CLOSE(data).takeLast(params.getTake_N());
					
//					close.subscribe(new Action1<Pair<DateTime, Double>>() {
//
//			            @Override
//			            public void call(Pair<DateTime, Double> s) {
//			                System.out.println("close " + s.getKey().toString() + ":        "+s.getValue().toString());
//			                //n+=1;
//			            }
//
//			        });
					
					
					Observable<Pair<DateTime, Double>> vma = mathService.VMA(close,params.getVMA_N(),params.getVMA_VI());
					
					Observable<Pair<DateTime, Double>> high = mathService.HIGH(data).takeLast(params.getTake_N());

					Observable<Pair<DateTime, Double>> low = mathService.LOW(data).takeLast(params.getTake_N());

					Observable<Pair<DateTime, Double>> vol = mathService.VOLUME(data).takeLast(params.getTake_N());
					Observable<Pair<DateTime, Double>> open = mathService.OPEN(data).takeLast(params.getTake_N());

					Observable<Pair<DateTime, Double>> hoh = mathService.HOH(high, params.getWILL_N())
							.takeLast(params.getTake_N());

					Observable<Pair<DateTime, Double>> lol = mathService.LOL(low, params.getWILL_N())
							.takeLast(params.getTake_N());

					
					
					Observable<Pair<DateTime, Double>> sma = mathService.SMA(close, params.getSMA_N()).takeLast(params.getTake_N());
//					
//					
					Observable<Pair<DateTime, Double>> ema = mathService.EMA(close, params.getSMA_N()).takeLast(params.getTake_N());
					Observable<Pair<DateTime, Double>> rsi = mathService.RSI(close, params.getRSI_N())
							.takeLast(params.getTake_N());
//
					Observable<Pair<DateTime, Triple<Double, Double, Double>>> boll = mathService.BOLL(close,
							params.getBBAND_N(), params.getBBAND_A());
//
					Observable<Pair<DateTime, Double>> boll_down = boll
							.map(x -> new ImmutablePair(x.getLeft(), x.getRight().getLeft()));
//
					Observable<Pair<DateTime, Double>> william = mathService
							.WILLIAM(close, hoh, lol, params.getWILL_N()).takeLast(params.getTake_N());
//
					Observable<Triple<DateTime, Double, Double>> kdj = mathService.KDJ(close, high, low,
							params.getKDJ_N(), params.getKDJ_A());
					
					
					
                    //William signal is good.
					LargerThanRule williamRule = new LargerThanRule();
//
					williamRule.setClose(william);
//
					williamRule.setLine1(params.getWillLn1());
//
					williamRule.setLine2(params.getWillLn2());
					
					williamRule.setN(params.getWILL_N());
					
					Observable<Pair<DateTime, Double>> williamSignal = williamRule.run2Rule(util);
					
					List<Pair<DateTime, Double>> williamSignalList = williamSignal.toList().toBlocking().single();
					
					Map<String, String> williamCrossUpMap2 = new TreeMap<String, String>();
					
					for (int i = 0; i < williamSignalList.size(); i++) {
						Pair<DateTime, Double> pair = williamSignalList.get(i);
						williamCrossUpMap2.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					
					//William ends
					
					
					//KDJ indicator is good
					CrossRule kDrule = new CrossUpRule();
					kDrule.setN(params.getKDJ_A());
					
    				kDrule.setClose(kdj.map(x -> new ImmutablePair(x.getLeft(), x.getMiddle())));
    				
    				kDrule.setBase(kdj.map(x -> new ImmutablePair(x.getLeft(), x.getRight())));
    				
					Observable<Pair<DateTime, Double>> kdjSignal = kDrule.runRule(util);
					
					List<Pair<DateTime, Double>> kdjSignalList = kdjSignal.toList().toBlocking().single();
					
					Map<String, String> kdjCrossMap = new TreeMap<String, String>();
					
					for (int i = 0; i < kdjSignalList.size(); i++) {
						Pair<DateTime, Double> pair = kdjSignalList.get(i);
						kdjCrossMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					//KDJ Ends
//

					//RSI is good
					LargerThanRule rsiRule = new LargerThanRule();

					rsiRule.setClose(rsi);

					rsiRule.setLine1(params.getRSI_Line1());

					rsiRule.setLine2(params.getRSI_Line2());
					
					rsiRule.setN(params.getRSI_N());
					
					Observable<Pair<DateTime, Double>> rsiSignal = rsiRule.run2Rule(util);
					
					List<Pair<DateTime, Double>> rsiSignalList = rsiSignal.toList().toBlocking().single();
					
					Map<String, String> rsiSigMap = new TreeMap<String, String>();
					
					for (int i = 0; i < rsiSignalList.size(); i++) {
						Pair<DateTime, Double> pair = rsiSignalList.get(i);
						rsiSigMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
				}
					
					
					//Boll is good
					CrossRule bollRule = new CrossDownRule();
     				bollRule.setClose(close.skip(params.getBBAND_N() - 1));
					bollRule.setBase(boll_down);
					
					Observable<Pair<DateTime, Double>> bollSignal = bollRule.runRule(util);
					
					List<Pair<DateTime, Double>> bollSignalList = bollSignal.toList().toBlocking().single();
					
					Map<String, String> bollCrossDownMap2 = new TreeMap<String, String>();
					
					for (int i = 0; i < bollSignalList.size(); i++) {
						Pair<DateTime, Double> pair = bollSignalList.get(i);
						bollCrossDownMap2.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					//Boll ends

					//MACD is good
					Observable<Pair<DateTime, Triple<Double, Double, Double>>> macd = MathOperatorService2.MACD(close,
							params.getMACDN1(), params.getMACDN2(), params.getMACDS());
//
					LargerThanRule macdRule = new LargerThanRule();

					macdRule.setClose(macd.map(x -> new ImmutablePair(x.getLeft(), x.getRight().getRight())));

					macdRule.setLine1(params.getMacdLine1());
					
					macdRule.setN(1);
					
					Observable<Pair<DateTime, Double>> macdSignal = macdRule.runRule(util);
//
					List<Pair<DateTime, Double>> macdSignalList = macdSignal.toList().toBlocking().single();
					
					Map<String, String> macdSigMap = new TreeMap<String, String>();
					
					for (int i = 0; i < macdSignalList.size(); i++) {
						Pair<DateTime, Double> pair = macdSignalList.get(i);
						macdSigMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					//MACD ends
					
					//SMA is good
					CrossUpRule smaRule = new CrossUpRule();

					smaRule.setClose(close);
					smaRule.setBase(sma);
					smaRule.setN(params.getSMA_N());

					Observable<Pair<DateTime, Double>> smaSignal = smaRule.runRule2(util );
//
					List<Pair<DateTime, Double>> smaSignalList = smaSignal.toList().toBlocking().single();
					
					Map<String, String> SMACrossUpMap = new TreeMap<String, String>();
					for (int i = 0; i < smaSignalList.size(); i++) {
						Pair<DateTime, Double> pair = smaSignalList.get(i);
						SMACrossUpMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					
					//VMA is good
					CrossRule emarule = new CrossUpRule();
					emarule.setN(params.getEMA_N());
					
					emarule.setClose(ema);
    				
					emarule.setBase(close);
    				
					Observable<Pair<DateTime, Double>> emaSignal = emarule.runRule(util);
					
					List<Pair<DateTime, Double>> emaSignalList = emaSignal.toList().toBlocking().single();
					
					Map<String, String> emaCrossMap = new TreeMap<String, String>();
					
					for (int i = 0; i < emaSignalList.size(); i++) {
						Pair<DateTime, Double> pair = emaSignalList.get(i);
						emaCrossMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					// VMA is good
					
					//HOH is good
					CrossRule highrule = new CrossUpRule();
					highrule.setN(1);
					
					highrule.setClose(close.skip(11));
    				
					highrule.setBase(mathService.SHIFT_N(hoh, 1));
    				
					Observable<Pair<DateTime, Double>> highruleSignal = highrule.runRule(util);
					Observable<Pair<DateTime, Double>> hoh1 = mathService.SHIFT_N(hoh, 1);
//					Observable<Pair<DateTime, Double>> substract = mathService.SUBSTRACT(close.skip(11), hoh1);
//					highruleSignal.subscribe(new Action1<Pair<DateTime, Double>>() {
//						
//									            @Override
//									            public void call(Pair<DateTime, Double> s) {
//									                System.out.println("highruleSignal " + s.getKey().toString() + ":        "+s.getValue().toString());
//									                //n+=1;
//									            }
//						
//									        });
//					System.out.println("  *************************************************");
//					
					List<Pair<DateTime, Double>> highSignalList = highruleSignal.toList().toBlocking().single();
					
					Map<String, String> highCrossMap = new TreeMap<String, String>();
					
					for (int i = 0; i < highSignalList.size(); i++) {
						Pair<DateTime, Double> pair = highSignalList.get(i);
						highCrossMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					//high ends
					
					//REX good
					
					Observable<Pair<DateTime, Double>> rex = mathService.REX(high,low,close,open,params.getREX_N1());
					Observable<Pair<DateTime, Double>> emaRex = mathService.EMA(rex,params.getREX_N2());
					CrossRule rexrule = new CrossUpRule();
					rexrule.setN(params.getREX_N2());
					
					rexrule.setClose(rex);
    				
					rexrule.setBase(emaRex);
					Observable<Pair<DateTime, Double>> rexruleSignal = highrule.runRule(util);
					Map<String, String> rexCrossMap = new TreeMap<String, String>();
					
					List<Pair<DateTime, Double>> rexruleSignalList = rexruleSignal.toList().toBlocking().single();
					
					for (int i = 0; i < rexruleSignalList.size(); i++) {
						Pair<DateTime, Double> pair = rexruleSignalList.get(i);
						rexCrossMap.put(pair.getKey().toString(fmt), String.format("%.2f", pair.getValue()));
					}
					
					
					
					
//
					Map<String, Map<String, String>> ruleMap = new HashMap<String, Map<String, String>>();
//
//
					ruleMap.put("SMASIG", SMACrossUpMap);
					ruleMap.put("VMASIG", emaCrossMap);
					ruleMap.put("WILLIAMSIG", williamCrossUpMap2);
					ruleMap.put("BOLLSIG", bollCrossDownMap2);
					ruleMap.put("HIGHSIG", highCrossMap);
					ruleMap.put("MACDSIG", macdSigMap);
					ruleMap.put("KDJSIG", kdjCrossMap);
					ruleMap.put("RSISIG", rsiSigMap);
					ruleMap.put("REXSIG", rexCrossMap);
//
					initData.put(symbol, ruleMap);

				} catch (Exception e) {
					//e.printStackTrace();
				}
				;

				long endtime = System.nanoTime();
				System.out.println(symbol + " run with " + (endtime - startTime));

			}
			transform();
			//System.out.println(finalData);
			
			Map<String,List<String>> result = getIndictors("2017-02-03");
			
			//System.out.println(result);
			System.out.println("done");
		};
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }

}