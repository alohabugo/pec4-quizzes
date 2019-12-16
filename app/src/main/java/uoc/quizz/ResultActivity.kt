package uoc.quizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    private var currentQuestionId: Int = 0
    private var len: Int = 0
    private val rigth = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val bundle = intent.extras

        var result = bundle!!.getString("result")
        this.currentQuestionId = bundle.getInt("currentQuestionId")
        this.len = bundle.getInt("len")

        Log.d("QUIZZ", "len " + this.len + " current" + this.currentQuestionId)
        if (this.currentQuestionId >= this.len - 1) {
            resultLabel.setText(getString(R.string.congrats))
            againBtn.setText(getString(R.string.startAgain))
            this.currentQuestionId = 0
        } else if (result!!.startsWith("RIGHT")) {
            resultLabel.setText(result)
            againBtn.setText( getString(R.string.next))
            this.currentQuestionId++
        } else {
            resultLabel.setText(result)
            againBtn.setText (getString(R.string.tryAgain))
        }

        againBtn.setOnClickListener {
            val i = Intent(this@ResultActivity, MainActivity::class.java)
            i.putExtra("currentQuestionId", this@ResultActivity.currentQuestionId)

            startActivity(i)
        }
    }
}
