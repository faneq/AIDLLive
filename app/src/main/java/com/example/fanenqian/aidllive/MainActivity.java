package com.example.fanenqian.aidllive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.startService).setOnClickListener(this);
        findViewById(R.id.stopService).setOnClickListener(this);
        findViewById(R.id.serviceIsRunning).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startService:
                startService(new Intent(this,HostService.class));
                startService(new Intent(this,LocalService.class));
                break;
            case R.id.stopService:
                stopService(new Intent(this,HostService.class));
                break;
            case R.id.serviceIsRunning:
                boolean  isRun = ServiceStateUtiles.isServiceProcessRunning(this,"com.example.fanenqian.aidllive");
                Toast.makeText(this,"服务运行状态="+isRun,Toast.LENGTH_SHORT).show();
                Log.i("TAG","服务运行状态="+isRun);
                break;
        }
    }
}
