package yzuic.kevinchang.taoyuantravelinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnGo;
    private LinearLayout llMain;
    private TableLayout tlInput;
    private Spinner spDistrict;
    private Spinner spLandmark;
    private WebView wvOutput;
    private ProgressDialog pd;
    private Context ct;
    private TextView tvDistrict;
    private TextView tvLandmark;

    private String[] arrDistrict = {"選擇行政區", "桃園區", "中壢區", "平鎮區", "楊梅區", "八德區", "大溪區", "蘆竹區", "大園區",
            "龜山區", "龍潭區", "新屋區", "觀音區", "復興區"};
    private String[] arrLandmark = {"選擇景點"};

    private String[] arrTaoyuan = {"彩韻廣場", "忠烈祠暨桃園神社", "桃園藝文廣場", "虎頭山環保公園", "景福宮", "孔廟",
            "奧爾森林學堂", "桃園1-4號生態埤塘", "桃園火車站前商圈", "祥藝機器人夢工廠", "可口可樂世界"};
    private String[] arrGuiShan = {"眷村故事館", "龜山第一河濱公園", "龜山巖觀音寺", "台塑企業文物館", "桃園酒廠",
            "龜山楓樹坑步道", "楓樹坑", "南僑桃園觀光體驗工廠"};
    private String[] arrBaDe = {"八德三元宮", "楓樹腳公園", "洪雅巧克力共和國", "霄里大池", "八德埤塘自然生態公園",
            "大湳圖書館", "興仁夜市"};
    private String[] arrDaShi = {"金蘭醬油博物館", "東河音樂體驗館", "大溪埔頂公園", "百吉林蔭步道",
            "大溪老街", "原住民文化會館"};
    private String[] arrChungLi = {"中原夜市商圈", "老街溪河川教育中心", "黑松飲料博物館", "憶聲電子文物館",
            "大江國際購物中心", "中央大學", "中平路商圈", "中壢老街溪步道", "中壢六和商圈", "龍岡國旗屋", "新街國小",
            "日式宿舍群", "中壢仁海宮", "中壢中平故事館", "中壢觀光夜市", "華泰名品城", "江記豆腐乳文化館", "金車咖啡文教館",
            "羊世界牧場", "圓光禪寺", "養樂多工廠", "桃園國際棒球場", "馬祖新村文創基地", "台灣高鐵桃園站", "青塘園",
            "龍岡清真寺"};
    private String[] arrPingCheng = {"新勢公園", "原有咖啡文化館", "平鎮褒忠祠", "忠貞商圈"};
    private String[] arrLongTan = {"三坑老街", "三坑自然生態公園", "龍潭三水村茶園", "龍潭觀光大池", "桃園客家文化館", "歐萊德綠建築總部", "石門十一份", "龍潭區佳安村", "龍潭聖蹟亭", "福源茶廠", "龍元宮商圈", "石門山", "大瓶紅橋", "小人國", "小粗坑古道"};
    private String[] arrYangMei = {"雅聞魅力博覽館", "富岡老街", "郭元益糕餅博物館", "耀輝牧場", "秀才登山步道", "白木屋品牌探索館"};
    private String[] arrShingWu = {"范姜老屋群", "稻米博物館", "九斗村有機農場", "老k舒眠文化館", "新屋綠色走廊", "永安漁港", "太平洋自行車博物館", "新屋蓮園"};
    private String[] arrGuanIng = {"觀音濱海遊憩區", "林家古厝", "觀音自行車道", "白沙岬燈塔", "台中環境教育資源中心", "蓮荷園", "休閒農場GFun機能紡織生活館"};
    private String[] arrError = {"查無此項目 !"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (size.x) * 60 / 100;

        //init layout
        llMain = (LinearLayout) findViewById(R.id.mainLayout);
        llMain.setWeightSum(1);

        tlInput = new TableLayout(this);
        tlInput.setGravity(TableLayout.HORIZONTAL);

        //init textview
        tvDistrict = new TextView(this);
        tvDistrict.setText("請選擇行政區 :");
        tvDistrict.setTextSize(20);
        tvLandmark = new TextView(this);
        tvLandmark.setText("請選擇推薦景點 :");
        tvLandmark.setTextSize(20);

        //init spinner
        spDistrict = new Spinner(this);
        ArrayAdapter<String> districtList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrDistrict);
        //districtList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spDistrict.setDropDownHorizontalOffset(LinearLayout.LayoutParams.MATCH_PARENT);
        //spDistrict.setDropDownWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        spDistrict.setDropDownWidth(width);
        spDistrict.setAdapter(districtList);
        spLandmark = new Spinner(this);
        ArrayAdapter<String> landmarkList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrLandmark);
        landmarkList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLandmark.setDropDownWidth(width);
        spLandmark.setAdapter(landmarkList);
        spLandmark.setEnabled(false);

        //init button
        btnGo = new Button(this);
        btnGo.setText("查詢");

        //init context
        ct = this.getApplicationContext();

        //init webview
        wvOutput = new WebView(this);
        wvOutput.setWebViewClient(new mywebview());
        pd = new ProgressDialog(this);

        //add view
        TableRow trDistrict = new TableRow(this);
        trDistrict.addView(tvDistrict);
        trDistrict.addView(spDistrict);
        TableRow trLandMark = new TableRow(this);
        trLandMark.addView(tvLandmark);
        trLandMark.addView(spLandmark);
        tlInput.setColumnStretchable(1, true);
        tlInput.addView(trDistrict);
        tlInput.addView(trLandMark);
        tlInput.addView(btnGo);
        llMain.addView(tlInput, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 0.7f));
        llMain.addView(wvOutput, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.3f));

        //show context text
        Toast.makeText(ct, "歡迎使用 桃園旅遊資訊 APP !", Toast.LENGTH_SHORT).show();
        Toast.makeText(ct, "請選擇行政區及推薦旅遊景點 !", Toast.LENGTH_SHORT).show();

        //set button OnClickListener
        btnGo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spDistrict.getSelectedItem().toString() == "選擇行政區" || spLandmark.getSelectedItem().toString() == "選擇景點")
                    Toast.makeText(ct, "請選擇行政區及推薦旅遊景點 !", Toast.LENGTH_SHORT).show();
                wvOutput.loadUrl("");
            }
        });

        //set spinner OnItemSelectedListener
        spDistrict.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = spDistrict.getSelectedItemPosition();
                if (spDistrict.getSelectedItem().toString() == "選擇行政區") {
                    spLandmark.setEnabled(false);
                } else {
                    spLandmark.setEnabled(true);
                }

                switch (spDistrict.getSelectedItem().toString()) {
                    case "桃園區":
                        //Toast.makeText(ct, "桃園區 !", Toast.LENGTH_SHORT).show();
                        arrLandmark = arrTaoyuan;
                        break;
                    case "中壢區":
                        arrLandmark = arrChungLi;
                        break;
                    case "平鎮區":
                        arrLandmark = arrPingCheng;
                        break;
                    case "楊梅區":
                        arrLandmark = arrYangMei;
                        break;
                    case "八德區":
                        arrLandmark = arrBaDe;
                        break;
                    case "大溪區":
                        arrLandmark = arrDaShi;
                        break;
                    case "蘆竹區":
                        arrLandmark = arrError;
                        break;
                    case "大園區":
                        arrLandmark = arrError;
                        break;
                    case "龜山區":
                        arrLandmark = arrGuiShan;
                        break;
                    case "龍潭區":
                        arrLandmark = arrLongTan;
                        break;
                    case "新屋區":
                        arrLandmark = arrShingWu;
                        break;
                    case "觀音區":
                        arrLandmark = arrGuanIng;
                        break;
                    case "復興區":
                        arrLandmark = arrError;
                        break;
                }

                List<String> stringlist;
                ArrayAdapter<String> arrayadapter;
                stringlist = new ArrayList<>(Arrays.asList(arrLandmark));
                arrayadapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,stringlist );
                arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLandmark.setAdapter(arrayadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //spLandmark.setEnabled(false);
            }
        });
    }

    private class mywebview extends WebViewClient {
        public mywebview() {
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (pd.isShowing())
                pd.dismiss();
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Toast.makeText(view.getContext(), "Error", Toast.LENGTH_LONG).show();
            super.onReceivedError(view, request, error);
        }
    }
}
