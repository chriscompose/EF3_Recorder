package com.example.ef3grabadora

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    // Ha tenido que poner "lateinit" en la variable "grabadora" para limpiar un error.
    lateinit var grabadora: MediaRecorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dirGrabacion = Environment.getStorageDirectory().toString()+"grabacion.3gp"

        // Declaramos los botones para grabar y parar grabación mediante findViewById:
        val btnRecord = findViewById<Button>(R.id.btn_grabar)
        val btnStop = findViewById<Button>(R.id.btn_parar)
        val btnPlay = findViewById<Button>(R.id.btn_play)

        // btnRecord.isEnabled = false
        btnStop.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 111)

            // btnRecord.isEnabled = true

            // BOTÓN GRABAR - Incluye configuración de la grabadora (MediaRecorder)
            btnRecord.setOnClickListener {

                // Configuramos la grabadora (MediaRecorder): dispositivo de entrada para la gracación de audio, formato y códec de grabación y la ruta en la que se grabará el archivo.
                grabadora.setAudioSource(MediaRecorder.AudioSource.MIC)
                grabadora.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                grabadora.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                grabadora.setOutputFile(dirGrabacion)

                //
                grabadora.prepare()
                grabadora.start()
                btnStop.isEnabled = true
                btnRecord.isEnabled = false
            }

            // BOTÓN PARAR
            btnStop.setOnClickListener {
                grabadora.stop()
                btnRecord.isEnabled = true

            }

            // BOTÓN REPRODUCIR
            btnPlay.setOnClickListener {
                val reproductorMedia = MediaPlayer()
                reproductorMedia.setDataSource(dirGrabacion)
                reproductorMedia.prepare()
                reproductorMedia.start()

            }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
            println("Grabando")
            // val btnRecord = findViewById<Button>(R.id.btn_play)
            //btnRecord.isEnabled = true
        }
    }

}