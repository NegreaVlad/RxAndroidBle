package com.polidea.rxandroidble2.internal.operations;

import android.bluetooth.BluetoothGatt;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.polidea.rxandroidble2.exceptions.BleGattOperationType;
import com.polidea.rxandroidble2.internal.SingleResponseOperation;
import com.polidea.rxandroidble2.internal.connection.RxBleGattCallback;

import bleshadow.javax.inject.Inject;
import io.reactivex.Single;

@RequiresApi(21 /* Build.VERSION_CODES.LOLLIPOP */)
public class PhyUpdateOperation extends SingleResponseOperation<Boolean> {

    private final int txPhy;
    private final int rxPhy;
    private final int phyOptions;

    @Inject
    PhyUpdateOperation(
            RxBleGattCallback rxBleGattCallback,
            BluetoothGatt bluetoothGatt,
            TimeoutConfiguration timeoutConfiguration, int txPhy, int rxPhy, int phyOptions) {
        super(bluetoothGatt, rxBleGattCallback, BleGattOperationType.ON_MTU_CHANGED, timeoutConfiguration);
        this.txPhy = txPhy;
        this.rxPhy = rxPhy;
        this.phyOptions = phyOptions;
    }

    @Override
    protected Single<Boolean> getCallback(RxBleGattCallback rxBleGattCallback) {
        return rxBleGattCallback.getOnPhyUpdate().firstOrError();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected boolean startOperation(BluetoothGatt bluetoothGatt) {
        bluetoothGatt.setPreferredPhy(txPhy, rxPhy, phyOptions);
        return true;
    }

    @Override
    public String toString() {
        return "PhyUpdateOperation{"
                + "txPhy=" + txPhy
                + ", rxPhy=" + rxPhy
                + ", phyOptions=" + phyOptions
                + '}';
    }
}
