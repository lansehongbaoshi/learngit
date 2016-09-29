package com.chsi.knowledge.robot.intent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chsi.framework.remote.RemoteCallRs;
import com.chsi.ipsrv.client.IpServiceClient;
import com.chsi.ipsrv.client.IpServiceClientFactory;
import com.chsi.ipsrv.client.vo.IpVo;
import com.chsi.knowledge.dic.IntentType;
import com.chsi.knowledge.pojo.QASessionData;
import com.chsi.knowledge.pojo.WeatherCodeData;
import com.chsi.knowledge.robot.question.vo.Time;
import com.chsi.knowledge.service.RobotService;
import com.chsi.knowledge.service.ServiceFactory;
import com.chsi.knowledge.util.DateUtil;
import com.chsi.search.client.SearchServiceClient;
import com.chsi.search.client.SearchServiceClientFactory;

public class Intent {
    
    public IntentType intentType;
    private String question;
    private String sessionId;
    /*分析后的结果*/
    List<String> result;
    
    public Intent(){
        
    }
    public Intent(String question){
        this.question = question;
    }
    public Intent(String question,String sessionId){
        this.question = question;
        this.sessionId = sessionId;
    }
    
    public boolean isExist(){
        /*
         * 今天天气怎么样！
         * 现在几点了
         * 
         */
        SearchServiceClient searchClient = SearchServiceClientFactory.getSearchServiceClient();
        result = searchClient.getAnalysis(getQuestion());
        //询问日期的意图
        if(((result.contains("星期") || result.contains("周")) && result.contains("几")) ||  result.contains("几号")){
            intentType = IntentType.date;
            return true;
        }
        //询问时间
        if(result.contains("几点") || (result.contains("时间")) && result.contains("现在")){
            intentType = IntentType.time;
            return true;
        }
        //询问天气的意图
        if(result.contains("天气") || result.contains("今天天气")){
            intentType = IntentType.weather;
            return true;
        }
        
        System.out.println(result);
        return false;
        
    }
    
    public String getContent(){
        String content = null;
        if(IntentType.date.toString().equals(this.intentType.toString())){
            Time time = new Time();
            for(String prop : result){
                if(time.dateTypes.containsKey(prop)){
                    //获取日期类型
                    time.dateType = time.dateTypes.get(prop);
                    content = DateUtil.getDateWeek(time.dateType);
                }
            }
        }else if(IntentType.time.toString().equals(this.intentType.toString())){
            content = DateUtil.getDateWeek() + DateUtil.getTime();
        }else if(IntentType.weather.toString().equals(this.intentType.toString())){
            Time time = new Time();
            List<String> addrs = new ArrayList<String>();
            if(result.indexOf("今天天气")!=-1){
                int end = result.indexOf("今天天气");
                time.dateType = 0;
                for(int i=0;i<end;i++){
                    addrs.add(result.get(i));
                }
                
            }else if(result.indexOf("天气")!=-1){
                int end = result.indexOf("天气");
                int start = -1;
                for(String prop : result){
                    if(time.dateTypes.containsKey(prop)){
                        //获取日期类型
                        time.dateType = time.dateTypes.get(prop);
                        start = result.indexOf(prop);
                        
                    }
                }
                
                if(start<0){
                    //默认时间为今天
                    time.dateType = 0;
                    start=-1;
                }
                if(start+1==end){
                    for(int i=0;i<start;i++){
                        addrs.add(result.get(i));
                    }
                }else{
                    for(int i=start+1;i<end;i++){
                        addrs.add(result.get(i));
                    }
                }
                
            }
            
            if(addrs.size()==0){
                RobotService robotService = ServiceFactory.getRobotService();
                QASessionData qaSession = robotService.getQASessionDataById(sessionId);
                IpServiceClient ipServiceClient = IpServiceClientFactory.getIpServiceClient();
                RemoteCallRs<IpVo> remoteCallRs = ipServiceClient.getIp(qaSession.getQUserIp());
                if(null!=remoteCallRs.getValue() && (!"".equals(remoteCallRs.getValue().getArea()) && null!=remoteCallRs.getValue().getArea())){
                    
                    WeatherCodeData weatherCode = robotService.getWeatherCode(remoteCallRs.getValue().getArea());
                    if(weatherCode == null){
                        content = "缺少输入的地址或者您输入的语句不通顺。";
                    }else{
                        JSONObject weather = getWeather(weatherCode);
                        content = DateUtil.getDateWeek(time.dateType)+" "+weather.getString("temp"+(time.dateType+1))+" "+weather.getString("weather"+(time.dateType+1));
                    }
                    
                }else{
                    content = "缺少输入的地址或者您输入的语句不通顺。";
                }
                
            }else if(addrs.size()==1){
                RobotService robotService = ServiceFactory.getRobotService();
                WeatherCodeData weatherCode = robotService.getWeatherCode(addrs.get(0));
                if(weatherCode == null){
                    content = "抱歉，您输入的地址没有在中央气象局公布的地址名单中！";
                }else{
                    
                    JSONObject weather = getWeather(weatherCode);
                    content = DateUtil.getDateWeek(time.dateType)+" "+weather.getString("temp"+(time.dateType+1))+" "+weather.getString("weather"+(time.dateType+1));
                }
            }else {
                RobotService robotService = ServiceFactory.getRobotService();
                WeatherCodeData weatherCode = robotService.getWeatherCode(addrs);
                if(weatherCode == null){
                    content = "抱歉，您输入的地址没有在中央气象局公布的地址名单中！";
                }else{
                    JSONObject weather = getWeather(weatherCode);
                    content = DateUtil.getDateWeek(time.dateType)+" "+weather.getString("temp"+(time.dateType+1))+" "+weather.getString("weather"+(time.dateType+1));
                }
            }
        }
        return content;
        
    }
    
    public JSONObject getWeather(WeatherCodeData weatherCode){
        
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httget = new HttpGet("http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode="+weatherCode.getWeatherCode()+"&weatherType=0");
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String result = null;
        String responseBody = null;
        try {
            responseBody = httpclient.execute(httget, responseHandler);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject json = JSONObject.fromObject(responseBody);
        JSONObject jsonInfo = JSONObject.fromObject(json.get("weatherinfo"));
        return jsonInfo;
        
    }
    

    public IntentType getIntentType() {
        return intentType;
    }

    public void setIntentType(IntentType intentType) {
        this.intentType = intentType;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
