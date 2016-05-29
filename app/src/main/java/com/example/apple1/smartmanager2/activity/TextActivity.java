package com.example.apple1.smartmanager2.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple1.smartmanager2.Application.ManagerData;
import com.example.apple1.smartmanager2.R;
import com.example.apple1.smartmanager2.adapter.RepairAdapter;
import com.example.apple1.smartmanager2.adapter.RepairAdapterSearch;
import com.example.apple1.smartmanager2.entity.RepairRecord;
import com.example.apple1.smartmanager2.entity.RepairRecordSearch;
import com.example.apple1.smartmanager2.net.NetThread;
import com.example.apple1.smartmanager2.net.Sql_URL;
import com.example.apple1.smartmanager2.tools.AutoString;
import com.example.apple1.smartmanager2.tools.WordTool;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TextActivity extends Activity implements View.OnClickListener {
    private RecognizerDialog iatDialog;
    RecognizerDialogListener mRecoListener;
    private Button searchBtn,nextBtn;
    private EditText searchEdit;
    private TextView textView;
    private ManagerData managerData;
    private ListView listView;
    private RepairAdapterSearch adapter;
    private ArrayList<RepairRecordSearch> repairRecordList;
    private Handler han;
    private String url= Sql_URL.my_url+"/sm/manager_search.php";
    private Context context;
    private Activity activity;
    private String content="jmsdjdhkajdhakh";
    private WordTool wordTool;
    private String nword="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        context=TextActivity.this;
        activity=TextActivity.this;
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=56e007dd");
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        init();
        iatDialog = new RecognizerDialog(this,null); //2.设置听写参数,同上节
        //3.设置回调接口
        iatDialog.setListener(mRecoListener);
    }

    private void init() {
        searchBtn= (Button) findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(this);
        nextBtn= (Button) findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(this);
        searchEdit= (EditText) findViewById(R.id.edit_search);
        textView= (TextView) findViewById(R.id.textview);
        //实例化repairRecordList
        repairRecordList=new ArrayList<RepairRecordSearch>();
        //获得manageData
        managerData= (ManagerData)this.getApplication();
        //获取自己写的WordTool的实例
        wordTool=new WordTool();
        listView= (ListView) findViewById(R.id.listview_search);
        han = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                String Jsmess =(String) msg.obj;
                Log.d("test", "Jsmess" + Jsmess);
                super.handleMessage(msg);
                if (Jsmess.equals("1")){
                    Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
                }else {
                    parseJSONWithJSONObject(Jsmess);
                }
            }

            private void parseJSONWithJSONObject(String Jsmess) {
                // TODO Auto-generated method stub
                JSONArray arr = null;
                try {
                    arr = new JSONArray(Jsmess);
                    Log.d("test", "arr" + arr);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject temp = (JSONObject) arr.getJSONObject(i);
                        String sId = temp.getString("s_id");
                        String phoneNumber=temp.getString("phonenumber");
                        String location = temp.getString("address");
                        String bug=temp.getString("origin");
                        String imageUrl=temp.getString("image_path");
                        String states=temp.getString("states");
                        //生成repairRecord
                        RepairRecordSearch repairRecord=new RepairRecordSearch(sId,location,phoneNumber,bug,imageUrl,states);
                        Log.d("test", " repairRecord.toString()" +"               "+ repairRecord.toString());
                        //加入列表
                        Log.d("test", "repairRecordList" + repairRecordList);
                        repairRecordList.add(repairRecord);

                    }

                    adapter=new RepairAdapterSearch(activity,R.layout.repair_item,repairRecordList,listView);
                    listView.setAdapter(adapter);
                    //Log.d("test", "exapmple" + example);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                            RepairRecordSearch repairRecord =repairRecordList.get(position);
                            Intent intent=null;
                            if (repairRecord.getStates().equals("1")){
                                intent = new Intent(activity, AllRepairBillActivity.class);
                            }else if (repairRecord.getStates().equals("2")){
                                intent=new Intent(activity,MyRepairBillActivity.class);
                            }else if (repairRecord.getStates().equals("3")){
                                intent=new Intent(activity,FinishedRepairBillActivity.class);
                            }
                            intent.putExtra("extra_data1", repairRecord.getS_id());
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        };

        //听写监听器
        mRecoListener = new RecognizerDialogListener(){
            //听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
            //一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
            //关于解析Json的代码可参见MscDemo中JsonParser类；
            //isLast等于true时会话结束。
            public void onResult(RecognizerResult results, boolean isLast) {
                Log.d("Result:", results.getResultString());
                String result=results.getResultString();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    Boolean b= (Boolean) jsonObject.get("ls");
                    Log.d("jssssss","b"+"......"+b);
                    if (!b){
                        Log.d("jssssss","进来了"+"......"+b);
                        JSONArray arr=new JSONArray(jsonObject.get("ws").toString());
                        JSONObject jsonObject1=new JSONObject(String.valueOf(arr.getJSONObject(0)));
                        String cw=jsonObject1.get("cw").toString();
                        Log.d("jssssss","cw"+"......"+cw);
                        JSONArray arr1=new JSONArray(cw.toString());
                        JSONObject jsonObject2=new JSONObject(String.valueOf(arr1.getJSONObject(0)));
                        content=jsonObject2.get("w").toString();
                        Log.d("jssssss", "jsonObject2.get(\"w\");" + "......" + jsonObject2.get("w"));
                        //获取数据
                        List<Term> parse1 = ToAnalysis.parse(content);
                        //测试自己写的WordTool
                        String state=wordTool.getStates(content);
                        String location=wordTool.getLocation(content);
                        if (!location.equals("")){
                            content =content.substring(0,wordTool.getLocationI1(content))+content.substring(wordTool.getLocationI2(content),content.length());
                        }

                        Log.d("test","111111111"+location);
                        List<Term> parse2 = ToAnalysis.parse(content);
                        for (int i=0;i<parse2.size();i++){
                            if (parse2.get(i).getNatureStr().equals("n")){
                                nword=parse2.get(i).getName();
                                break;
                            }
                        }
                        textView.setText(parse1 + "");

                        //添加关于POST的东西
                        AutoString autoString=new AutoString("m_id",2+"");
                        try {
                            nword= URLEncoder.encode(nword, "utf-8");
                            location= URLEncoder.encode(location, "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        //清空list
                        repairRecordList.clear();
                        autoString.addToResult("search_content", nword);
                        autoString.addToResult("state",state);
                        autoString.addToResult("location",location);
                        String params=autoString.getResult();
                        Log.d("test", "params" + "       " + params);
                        NetThread nt=new NetThread(han,url,params);
                        nt.start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

                //会话发生错误回调接口
            public void onError(SpeechError error) {
                error.getPlainDescription(true); //获取错误码描述
            }

        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                content=searchEdit.getText().toString();
                List<Term> parse1 = ToAnalysis.parse(content);
                //测试自己写的WordTool
                String state=wordTool.getStates(content);
                String location=wordTool.getLocation(content);
                if (!location.equals("")){
                    content =content.substring(0,wordTool.getLocationI1(content))+content.substring(wordTool.getLocationI2(content),content.length());
                }

               Log.d("test","11111111"+location);
                List<Term> parse2 = ToAnalysis.parse(content);
                for (int i=0;i<parse2.size();i++){
                    if (parse2.get(i).getNatureStr().equals("n")){
                        nword=parse2.get(i).getName();
                        break;
                    }
                }
                textView.setText(parse1 + "");

                //添加关于POST的东西
                AutoString autoString=new AutoString("m_id",2+"");
                try {
                    nword= URLEncoder.encode(nword, "utf-8");
                    location= URLEncoder.encode(location, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //清空list
                repairRecordList.clear();
                autoString.addToResult("search_content", nword);
                autoString.addToResult("state",state);
                autoString.addToResult("location",location);
                String params=autoString.getResult();
                Log.d("test", "params" + "       " + params);
                NetThread nt=new NetThread(han,url,params);
                nt.start();
                break;
            case R.id.btn_next:
//                Intent intent=new Intent(TextActivity.this,SearchActivity.class);
//                startActivity(intent);
                iatDialog.show();
                break;
        }

    }
}
