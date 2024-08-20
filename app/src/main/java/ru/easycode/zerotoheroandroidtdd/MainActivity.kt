package ru.easycode.zerotoheroandroidtdd

import android.inputmethodservice.Keyboard.Key
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var state : State = State.Initial

    private lateinit var linearLayout: LinearLayout
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.titleTextView)
        val button: Button = findViewById(R.id.removeButton)
        linearLayout = findViewById(R.id.rootLayout)

        button.setOnClickListener {
            state = State.Remove
            state.apply(linearLayout, textView)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        state = savedInstanceState.getSerializable(KEY) as State
        state.apply(linearLayout, textView)
    }

    companion object {
        private const val KEY = "key"
    }
}
interface State : Serializable {

    fun apply(linearLayout: LinearLayout, textView: TextView)

    object Initial : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView) = Unit
    }

    object Remove : State {
        override fun apply(linearLayout: LinearLayout, textView: TextView) {
            linearLayout.removeView(textView)
        }
    }
}