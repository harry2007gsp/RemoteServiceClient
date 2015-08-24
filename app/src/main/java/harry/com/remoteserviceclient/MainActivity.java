package harry.com.remoteserviceclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    harry.com.remoteserviceserver.MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void connect(View view) {
        Intent intent = new Intent();
        //here is the twist that how we connect and find the remote component
        intent.setClassName("harry.com.remoteserviceserver", "harry.com.remoteserviceserver.MyServiceImplementation");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void use(View view) {
        try {
            Log.d("test", myService.returnString("mister"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(View view) {
        unbindService(serviceConnection);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("test", "client connected");
//            remoteService = IMyRemoteService.Stub.asInterface((IBinder)boundService);
            myService = harry.com.remoteserviceserver.MyService.Stub.asInterface((IBinder)service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("test", "client disconnected");
        }
    };


}
