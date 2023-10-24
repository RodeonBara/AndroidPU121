package step.learning.androidpu121;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.main_tv_title);
        Button btnViwes = findViewById(R.id.maim_btn_viwes);
        btnViwes.setOnClickListener(this::btnViewsClick);

        Button btnCalc = findViewById( R.id.main_calc );
        btnCalc.setOnClickListener( this::btnCalcClick );

        Button btnChat = findViewById( R.id.main_chat );
        btnChat.setOnClickListener( this::btnChatClick );
    }
    private void btnViewsClick(View view){
        Intent intent = new Intent(
                this.getApplicationContext(),
                ViewsActivity.class);
        startActivity(intent);
    }
    private void btnCalcClick( View view ) {  // view - sender
        Intent intent = new Intent(
                this.getApplicationContext(),
                CalcActivity.class ) ;
        startActivity( intent );
    }

    private void btnChatClick( View view ) {  // view - sender
        Intent intent = new Intent(
                this.getApplicationContext(),
                ChatActivity.class ) ;
        startActivity( intent );
    }


}