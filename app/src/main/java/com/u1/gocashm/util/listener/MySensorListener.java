package com.u1.gocashm.util.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class MySensorListener implements SensorEventListener {

    private long timestamp;
    private float anglex;
    private float angley;
    private float anglez;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float x;
    private float y;
    private float z;
    private float x1;
    private float y1;
    private float z1;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == null) {    //如果没有传感器事件则返回
            return;
        }
        //新建加速度计变化事件
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {//传感器发生改变获取加速度计传感器的数据
            if (timestamp != 0) {
                // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
                final float dT = (sensorEvent.timestamp - timestamp) * NS2S;
                // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
                x1 = sensorEvent.values[0];
                y1 = sensorEvent.values[1];
                z1 = sensorEvent.values[2];

                x += sensorEvent.values[0] * dT;
                y += sensorEvent.values[1] * dT;
                z += sensorEvent.values[2] * dT;

                // 将弧度转化为角度
                anglex = (float) Math.toDegrees(x);
                angley = (float) Math.toDegrees(y);
                anglez = (float) Math.toDegrees(z);
            }
            //将当前时间赋值给timestamp
            timestamp = sensorEvent.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public String getAnglex() {
        return String.valueOf(x1 + "," + anglex);
    }

    public String getAngley() {
        return String.valueOf(y1 + "," + angley);
    }

    public String getAnglez() {
        return String.valueOf(z1 + "," + anglez);
    }
}
