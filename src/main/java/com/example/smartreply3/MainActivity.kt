package com.example.smartreply3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var conversation= ArrayList<TextMessage>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameText.setOnClickListener {


        }

        massageText.setOnClickListener{

        }
        HintButton.setOnClickListener {
            getHints()
        }
        SendButton.setOnClickListener{
        addConviresion(massageText.text.toString())
            closeKeyBoard()

        }
        ClearButton.setOnClickListener {
        conversation= ArrayList()
            hint2Button.visibility= View.GONE
            hint1Button.visibility= View.GONE
            hint0Button.visibility= View.GONE
            errorText.text=""
        }
        hint2Button.setOnClickListener {
        addConviresion(hint2Button.text.toString())
        }
        hint0Button.setOnClickListener {
        addConviresion(hint0Button.text.toString())
        }
        hint1Button.setOnClickListener {
        addConviresion(hint1Button.text.toString())
        }

    }
   private  fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

  private  fun addConviresion(text:String){
      conversation.add(TextMessage.createForRemoteUser(
         text, System.currentTimeMillis(), nameText.text.toString()))
    }
private fun getHints(){
    val smartReplyGenerator = SmartReply.getClient()
    smartReplyGenerator.suggestReplies(conversation)
        .addOnSuccessListener { result ->
            if (result.getStatus() == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                errorText.text="language not supportive"

            } else if (result.getStatus() == SmartReplySuggestionResult.STATUS_SUCCESS) {
               hint0Button.text=result.suggestions[0].text
               hint1Button.text=result.suggestions[1].text
                hint2Button.text=result.suggestions[2].text

                hint2Button.visibility= View.VISIBLE
                hint1Button.visibility= View.VISIBLE
                hint0Button.visibility= View.VISIBLE

            }
        }
        .addOnFailureListener {
        errorText.text=it.toString()
        }



}
}