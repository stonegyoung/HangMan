package com.example.hangman;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

public class Play extends Activity {
    String[] eng_dic = {"append", "hello", "dock", "extend", "arrange", "occupy", "examine", "sort", "stack", "dust",
            "facility", "estimate", "violation", "assign", "venue", "undergo", "vacate", "asset", "readily", "expressly",
            "charitable", "thorough", "branch", "readiness", "firm", "pottery", "bush", "dust", "stroll", "vendor"};

    Random random = new Random();
    int n = random.nextInt(eng_dic.length-1);

    String eng = eng_dic[n];
    int chance = 8;
    int r = 7, c = 4;
    Button button[][] = new Button[r][c];
    TextView rem;
    TextView res;
    LinearLayout MainLayout;
    ImageView img;
    String str = "" ;
    String show_str = "";
    Button replay ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        replay = findViewById(R.id.replay);

        MainLayout = findViewById(R.id.mainlayout);
        rem = findViewById(R.id.remain);
        res = findViewById(R.id.result);
        img = findViewById(R.id.image);
        replay = findViewById(R.id.replay);

        String packName = this.getPackageName();

        for (int i =0; i<eng.length(); i++){
            str += "_";
        }

        show_str = change_str(str);
        res.setText(show_str);


        TableRow.LayoutParams rowLayout = new TableRow.LayoutParams
                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f);

        int num = 97;

        TableLayout table1 = new TableLayout(this);
        for(int tr = 0; tr < 7; tr++) {
            TableRow row[] = new TableRow[7];
            row[tr] = new TableRow(this);
            for (int tc = 0; tc < 4; tc++) {
                button[tr][tc] = new Button(this);
                button[tr][tc].setLayoutParams(rowLayout);
                button[tr][tc].setText(String.valueOf((char)num));
                num++;
                button[tr][tc].setTextSize(20);
                if(num >123){
                    button[tr][tc].setVisibility(View.INVISIBLE);
                }

                row[tr].addView(button[tr][tc]);
            }
            table1.addView(row[tr]);
        }
        MainLayout.addView(table1);

        for (int tr = 0; tr < r; tr++) {
            for (int tc = 0; tc < c; tc++) {
                int pushr=tr, pushc=tc;
                button[tr][tc].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String a = button[pushr][pushc].getText().toString();

                        if (eng.indexOf(a) == -1){

                            chance -= 1;

                            String resName = "@drawable/d"+String.valueOf(8-chance);

                            int resID = getResources().getIdentifier(resName , "drawable", packName );

                            button[pushr][pushc].setTextColor(Color.RED);
                            rem.setText(String.valueOf(chance));
                            //이미지 바꾸기
                            img.setImageResource(resID);
                            show_str = change_str(str);
                            button[pushr][pushc].setClickable(false);
                        }
                        else{
                            int start = 0;
                            while (eng.indexOf(a,start) != -1){
                                int idx = eng.indexOf(a,start);
                                start = idx + 1;
                                // 바꾸기
                                str = str.substring(0, idx) + a + str.substring(idx + 1);
                                show_str = change_str(str);
                            }
                            button[pushr][pushc].setVisibility(View.INVISIBLE);
                        }
                        if (str.indexOf("_") == -1){
                            show_str = String.valueOf(8-chance)+"번만에 정답!";
                            //다시하기 버튼
                            replay.setVisibility(View.VISIBLE);
                            MainLayout.setVisibility(View.INVISIBLE);
                        }
                        if ( chance == 0){
                            show_str = "실패! 정답은 "+eng;
                            //다시하기 버튼
                            replay.setVisibility(View.VISIBLE);
                            MainLayout.setVisibility(View.INVISIBLE);
                        }
                        res.setText(show_str);
                    }
                });
            }
        }

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //글자 띄어주기
    public String change_str(String str){
        String show_str = "";
        for (int i = 0; i < str.length(); i++) {
            show_str = show_str + str.charAt(i) + " ";
        }

        return show_str;
    }
}
