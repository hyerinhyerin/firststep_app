package com.example.firststep;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Question extends AppCompatActivity {
    DBSuppormer dbSuppormer;
    RecyclerView recyclerView;
    ArrayList<String> categoryList;
    ArrayList<String> answerList;
    ArrayList<String> anotherList;


    Intent intent;
    String categoryname;
    int total_number = 0;
    int num = 1;
    List<String> answer_another = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        dbSuppormer = new DBSuppormer(Question.this);

        Button backButton = (Button) findViewById(R.id.button2);
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Class_question_list.class);
                startActivity(intent);
            }
        });


        //해당 카테고리 이름 불러오기
        intent = getIntent();
        categoryname = intent.getStringExtra("해당 카테고리 이름");

        //카테고리 이름 출력
        TextView category = (TextView) findViewById(R.id.textView3);
        category.setText(String.valueOf(categoryname));



        recyclerList();
        Total_num();
        PrintValues();
    }

    private void PrintValues(){
        //number 출력
        TextView number = (TextView) findViewById(R.id.textView4);
        number.setText(String.valueOf(num)+".");
        if(num > total_number)
            num = 0;

        //이미지 출력
        List value_1 = dbSuppormer.getValue(categoryname);


        ImageView image = (ImageView) findViewById(R.id.imageView2);
        image.setImageBitmap((Bitmap) value_1.get(2));      //2번이 이미지

        //정답, 선택지 answer_another 랜덤으로 삽입
        anotherAnswer((String) value_1.get(3));     //3번이 정답

        //선택지 출력
        TextView choice1 = (TextView) findViewById(R.id.choice1);
        choice1.setText((CharSequence) answer_another.get(0));
        TextView choice2 = (TextView) findViewById(R.id.choice2);
        choice2.setText((CharSequence) answer_another.get(1));
        TextView choice3 = (TextView) findViewById(R.id.choice3);
        choice3.setText((CharSequence) answer_another.get(2));

        num++;

        int id = (int)value_1.get(0);
        while ((int)value_1.get(0) == id){
            value_1 = dbSuppormer.getValue(categoryname);
        }
    }

    private void Total_num(){

        total_number = dbSuppormer.getNumberCategoryN(String.valueOf(categoryname));
        Log.i("의사소통 개수", String.valueOf(total_number));


    }

    private void anotherAnswer(String answer_real){

        List answer = dbSuppormer.getResultCategoryAnswer();
        //정답 외에 문제 선택지 List 생성

        //랜덤
        Random random = new Random();

            answer_another.clear();
            //중복 방지
            String another_1 = answer_real;

            for (int j = 0; j < answer.size(); j++) {
                int randomIndex = random.nextInt(answer.size());        //random index
                String string_another = String.valueOf(answer.get(randomIndex));
                Log.i("의사소통 another", string_another);

                //정답과 another이 다르면서 저장된 another과 다르면 저장
                if (!answer_real.equals(string_another) && !another_1.equals(string_another)) {
                    answer_another.add(string_another);
                    another_1 = string_another;
                    //2개만 받고 break;
                    if(answer_another.size() == 2){
                        answer_another.add(answer_real);
                        Collections.shuffle(answer_another);
                        Log.i("의사소통 answer_another", String.valueOf(answer_another.get(0)));
                        Log.i("의사소통 answer_another", String.valueOf(answer_another.get(1)));
                        Log.i("의사소통 answer_another", String.valueOf(answer_another.get(2)));
                        break;
                    }
                }
            }
        }


    @SuppressLint("LongLogTag")
    private void recyclerList() {
        // recycler view
        recyclerView = findViewById(R.id.recyclerView);
        // List item 생성
        categoryList = new ArrayList<String>();
        // List - 정답 item 생성
        answerList = new ArrayList<String>();

        List list = dbSuppormer.getResultCategoryN();
        List answer = dbSuppormer.getResultCategoryAnswer();
        List<String> list1 = new ArrayList<>();
        //정답 외에 문제 선택지 List 생성
        List<String> answer_another = new ArrayList<>();
        //랜덤
        Random random = new Random();


        for (int i = 0; i < list.size(); i++) {
        categoryList.add(String.valueOf(list.get(i)));
        list1.add(String.valueOf(list.get(i)));
        }

        for(int i = 0; i< answer.size(); i++) {
        answerList.add(String.valueOf(answer.get(i)));
        }





        }
}
//package com.example.firststep;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//public class Question extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.question);
//
//        ImageView Button = (ImageView) findViewById(R.id.imageView2);
//        Button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Question_result.class);
//                startActivity(intent);
//            }
//        });
//    }
//}