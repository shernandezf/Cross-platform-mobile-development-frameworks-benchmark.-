package com.shernandezf.kotlintesisapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class ProximitySensor(context: Context, private val onProximityData: (Float) -> Unit) : SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    fun startListening() {
        sensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            onProximityData(it.values[0])
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle changes here if necessary
    }
}

class Accelerometer(context: Context, private val onSensorValues: (FloatArray) -> Unit) : SensorEventListener {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometer: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val threshold = 2.0f  // Threshold for significant movement
    private var lastValues = FloatArray(3) { 0f }

    init {
        accelerometer?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { event ->
            if (abs(event.values[0] - lastValues[0]) > threshold ||
                abs(event.values[1] - lastValues[1]) > threshold ||
                abs(event.values[2] - lastValues[2]) > threshold) {
                lastValues[0] = event.values[0]
                lastValues[1] = event.values[1]
                lastValues[2] = event.values[2]
                onSensorValues(lastValues)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if needed
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }
}