package com.thesaifhusain.note.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class UtilsImpl @Inject constructor(
    private val context: Context
): Utils {
    private lateinit var textToSpeech: TextToSpeech
    override fun setTTS(speech: String) {
        textToSpeech = TextToSpeech(context){ statue->
            if (statue == TextToSpeech.SUCCESS){
                textToSpeech.setLanguage(Locale.getDefault())
                textToSpeech.setSpeechRate(0.8F)
                textToSpeech.setPitch(1.0F)
                textToSpeech.speak(speech,TextToSpeech.QUEUE_FLUSH,null,null)
            }else{
                Toast.makeText(context, "Unable to recognize language", Toast.LENGTH_SHORT).show()
            }
        }
    }
}