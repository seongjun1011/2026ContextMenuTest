package kr.ac.kopo.contextmenutest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnRotation, btnZoomin;
    LinearLayout linear;
    float rotationDegree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. 위젯 연결(findViewById)을 가장 먼저 수행합니다.
        btnRotation = findViewById(R.id.btn_bg);
        btnZoomin = findViewById(R.id.btn_chande);
        linear = findViewById(R.id.main);
        Button btnAlert = findViewById(R.id.btn_alert);

        // 2. linear가 초기화된 후 WindowInsets 패딩을 설정합니다.
        if (linear != null) {
            ViewCompat.setOnApplyWindowInsetsListener(linear, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // 3. 알림창 버튼 클릭 이벤트 설정
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("대화상자연습");
                dialog.setMessage("대화상자 내의 내용 부분이에요.");
                dialog.setIcon(R.drawable.icon);

                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "확인 버튼을 클릭했어요.", Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("취소", null); // 보통 부정을 '취소'로 많이 씁니다.
                dialog.show(); // 세미콜론(;) 추가 완료
            }
        });

        // 4. 컨텍스트 메뉴 등록
        registerForContextMenu(btnRotation);
        registerForContextMenu(btnZoomin);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();

        if(v == btnRotation){
            menu.setHeaderTitle("배경색 변경");
            menuInflater.inflate(R.menu.context_menu1, menu);
        }
        if(v == btnZoomin){
            menu.setHeaderTitle("버튼 변경");
            menuInflater.inflate(R.menu.context_menu2, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_bg_orange){
            linear.setBackgroundColor(Color.rgb(255, 165, 0));
            return true;
        } else if(item.getItemId() == R.id.item_bg_yellow){
            linear.setBackgroundColor(Color.YELLOW);
            return true;
        } else if(item.getItemId() == R.id.item_bg_red){
            linear.setBackgroundColor(Color.RED);
            return true;
        } else if(item.getItemId() == R.id.item_btn_rotation){
            rotationDegree += 45;
            btnZoomin.setRotation(rotationDegree);
            return true;
        } else if(item.getItemId() == R.id.item_btn_zoomin){
            btnZoomin.setScaleX(2);
            btnZoomin.setScaleY(2);
            return true;
        }

        return false;
    }
}