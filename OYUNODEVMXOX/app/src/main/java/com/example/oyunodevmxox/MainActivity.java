package com.example.oyunodevmxox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView oyuncu1skor,oyuncu2skor,oyuncuDurumu;
    private Button[] buttons= new Button[9]; //9 tane XOX butonumuz old dizi olusturduk
    private Button sil,tekrarOyna;
    private int oyuncu1skorsayisi,oyuncu2skorsayisi;

    int round;

    boolean oyuncu1aktif; //hangi oyuncunun oynadiğini belirlemek icin

    //oyun durumunu belirlemek icin  oyuncu1=0 ,oyuncu2=1 ,bos ise 2 olsun

    int[] oyunDurumu={2,2,2,2,2,2,2,2,2}; //Tum butonların durumunu 2 atadık yani hepsi bos

    int[][] kazanmapozisyonlari= { {0,1,2},{3,4,5},{6,7,8},//satır
                          {0,3,6},{1,4,7},{2,5,8} ,//sutun
                            {0,4,8},{2,4,6}   };//capraz


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        oyuncu1skor=findViewById(R.id.skor_oyuncu1);
        oyuncu2skor=findViewById(R.id.skor_oyuncu2);
        oyuncuDurumu=findViewById(R.id.text_status);
        sil=findViewById(R.id.btn_sil);
        tekrarOyna=findViewById(R.id.btn_tekraroyna);

        buttons[0]=findViewById(R.id.btn0);
        buttons[1]=findViewById(R.id.btn1);
        buttons[2]=findViewById(R.id.btn2);
        buttons[3]=findViewById(R.id.btn3);
        buttons[4]=findViewById(R.id.btn4);
        buttons[5]=findViewById(R.id.btn5);
        buttons[6]=findViewById(R.id.btn6);
        buttons[7]=findViewById(R.id.btn7);
        buttons[8]=findViewById(R.id.btn8);

        for(int i=0;i<buttons.length;i++)  // button numarasına i deyip for a sokarak tum butonlar icin tek tek setOnClick yazmama gerek kalmadı
            //bununla ilgili sadece bie metot yazıp tüm butonlar icin kullanacagım
        {
            buttons[i].setOnClickListener(this);
        }


        oyuncu1skorsayisi=0;
        oyuncu2skorsayisi=0;
        oyuncu1aktif=true;
        round=0;


    }

    @Override
    public void onClick(View view) {

        if(!((Button)view).getText().toString().equals(""))//bu butona onceden basılmıs ise
        {
            return;
        }
        else if(kazanan_kisi())
        {
            return;
        }

        String buttonID= view.getResources().getResourceEntryName(view.getId());
        int oyunDurumPointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length()));//buton numaralarını sildik


        if(oyuncu1aktif)
        {
            ((Button)view).setText("X");
            oyunDurumu[oyunDurumPointer]=0;  //butona hangi oyuncunun bastigini belirlemek icin
        }
        else {
            ((Button)view).setText("O");
            oyunDurumu[oyunDurumPointer]=1;
        }

        round++; //hamle yapıldıkca round artar

        if(kazanan_kisi()){

            if(oyuncu1aktif)
            {
                oyuncu1skorsayisi ++;
                oyuncu_skor_guncelle();
                oyuncuDurumu.setText("Oyuncu 1 kazandı !");
            }
            else {
                oyuncu2skorsayisi++;
                oyuncu_skor_guncelle();
                oyuncuDurumu.setText("Oyuncu 2 kazandı !");
            }

        }

        else if(round==9)//hamle sayisi 9 olunca kazanan kimse yoktur
        {
            oyuncuDurumu.setText("Kazanan Yok!");
        }
        else {

            oyuncu1aktif=! oyuncu1aktif;
            //bu sekilde oynama sırasını oyuncu2 ye gecirdik
        }

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tekrar_oyna();  //metot
                oyuncu1skorsayisi=0;
                oyuncu2skorsayisi=0;
                oyuncu_skor_guncelle();

            }


        });

        tekrarOyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tekrar_oyna();



            }
        });

    }

    private boolean kazanan_kisi() {
        boolean kazanan_sonuc=false;
        for(int[] kazanmapozisyonlari: kazanmapozisyonlari )
        {
            if(oyunDurumu[kazanmapozisyonlari[0]]==oyunDurumu[kazanmapozisyonlari[1]] &&
                    oyunDurumu[kazanmapozisyonlari[1]]==oyunDurumu[kazanmapozisyonlari[2]] &&
                    oyunDurumu[kazanmapozisyonlari[0]]!=2)
            {
                kazanan_sonuc=true;
            }
        }
        return kazanan_sonuc;
    }

    private void oyuncu_skor_guncelle() {

        oyuncu1skor.setText(Integer.toString(oyuncu1skorsayisi));
        oyuncu2skor.setText(Integer.toString(oyuncu2skorsayisi));

    }
    private void tekrar_oyna() {

        round=0;
        oyuncu1aktif=true;
        for(int i=0;i<buttons.length;i++)
        {
            oyunDurumu[i]=2; //boyle tum butonların icini bosalttık
            buttons[i].setText("");

        }
        oyuncuDurumu.setText("Status"); // kutunun icindeki yazıyı ilk haline guncelledik

    }
}