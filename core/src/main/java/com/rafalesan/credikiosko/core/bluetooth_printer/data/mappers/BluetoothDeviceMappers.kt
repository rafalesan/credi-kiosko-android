package com.rafalesan.credikiosko.core.bluetooth_printer.data.mappers

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.BluetoothDevice
import com.rafalesan.credikiosko.core.bluetooth_printer.domain.entity.DeviceType
import com.rafalesan.credikiosko.core.room.entity.BluetoothPrinterEntity
import android.bluetooth.BluetoothDevice as AndroidBluetoothDevice

@SuppressLint("MissingPermission")
fun AndroidBluetoothDevice.toBluetoothDeviceDomain(): BluetoothDevice {
    return BluetoothDevice(
        name = name,
        macAddress = address,
        type = getBluetoothDeviceType(bluetoothClass)
    )
}

fun BluetoothDevice.toBluetoothPrinterEntity(): BluetoothPrinterEntity {
    return BluetoothPrinterEntity(
        name = name,
        macAddress = macAddress
    )
}

fun getBluetoothDeviceType(bluetoothClass: BluetoothClass): DeviceType {

    if (bluetoothClass.deviceClass == 1664 &&
        bluetoothClass.majorDeviceClass == 1536
    ) {
        return DeviceType.Printer
    }

    if (
        bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.PERIPHERAL &&
        bluetoothClass.deviceClass == 1288
    ) {
        return DeviceType.Controller
    }

    return when(bluetoothClass.deviceClass) {
        BluetoothClass.Device.COMPUTER_LAPTOP,
        BluetoothClass.Device.COMPUTER_DESKTOP -> DeviceType.Computer
        BluetoothClass.Device.Major.PHONE -> DeviceType.Phone
        BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES,
        BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER,
        BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> DeviceType.Headphones
        BluetoothClass.Device.WEARABLE_WRIST_WATCH -> DeviceType.Watch
        BluetoothClass.Device.TOY_CONTROLLER,
        BluetoothClass.Device.TOY_GAME -> DeviceType.Controller
        BluetoothClass.Device.PERIPHERAL_KEYBOARD -> DeviceType.Keyboard
        BluetoothClass.Device.PERIPHERAL_POINTING -> DeviceType.Mouse
        else -> DeviceType.Unknown
    }
}