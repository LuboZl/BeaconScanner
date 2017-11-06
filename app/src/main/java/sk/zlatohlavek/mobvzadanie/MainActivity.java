package sk.zlatohlavek.mobvzadanie;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.d("test", "started");

                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
                Log.d("test", "finished");

            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                //bluetooth device found
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("test", "found device");
                if(device!=null) {
                    Log.d("test", device.getName()+" - RSSI :"+intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);
        adapter.startDiscovery();
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }

}
