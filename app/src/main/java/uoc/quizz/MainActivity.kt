package uoc.quizz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


import uoc.quizz.db.entities.QuestionItemDB
import uoc.quizz.db.model.Question

class MainActivity : AppCompatActivity() {

    internal var currentQuestion: Question? = null
    internal var currentQuestionId = 0
    internal var questions: List<Question>? = null

    private var questionItemDB: QuestionItemDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras

        if (bundle != null) this.currentQuestionId = bundle.getInt("currentQuestionId", 0)
        Log.d("QUIZZ.KT", "$currentQuestionId")

        this.questionItemDB = QuestionItemDB(this)
        Log.d("QUIZZ", "The Question: "+theQuestion.text);

        this.initView()


        button?.setOnClickListener{attemptSend()};
    }


    private fun attemptSend() {
        Log.d("QUIZZ", "attemptSend")
            var go = true
            var result = getString(R.string.failed)

            val selectedId = this@MainActivity.radioGroup?.checkedRadioButtonId

            if (selectedId == this@MainActivity.radioButton1?.id) {
                result = this@MainActivity.getResult(0)

            } else if (selectedId == this@MainActivity.radioButton2?.id) {
                result = this@MainActivity.getResult(1)
            } else {
                Toast.makeText(applicationContext, R.string.SELECT_ONE_ANSWER,
                        Toast.LENGTH_SHORT).show()
                go = false
            }

            if (go) {


                startActivity(Intent(this@MainActivity, ResultActivity::class.java).apply {
                    putExtra("result", result)
                    putExtra("currentQuestionId", this@MainActivity.currentQuestionId)
                    putExtra("len", this@MainActivity.questions!!.size)
                })

            }
    };


    private fun isTheRigthChoice(i: Int): Boolean {
        return i == currentQuestion?.rigthChoice
    }

    private fun getResult(i: Int): String {
        var result: String? = null

        if (isTheRigthChoice(i)) {
            result = getString(R.string.rigth)
        } else {
            result = getString(R.string.failed)
        }

        return result
    }

    private fun initView() {
        initQuestions()
        currentQuestion = this.getQuestion(this.currentQuestionId)

        Log.d("QUIZZ.KT", currentQuestion!!.title);

        val id = currentQuestionId+1
        val total = this.questions!!.size;
        index.setText("$id / $total");
        theQuestion.setText(currentQuestion?.title)
        choice1.setText(currentQuestion?.choices?.get(0))
        choice2.setText(currentQuestion?.choices!![1])
        example.setText(currentQuestion?.example)
    }

    fun getQuestion(i: Int): Question {
        return this.questions!![i]
    }

    fun initQuestions() {
        questions =  mutableListOf<Question>()
        val choices1 = arrayOf("y = 7", "y = 23")
        val q1 = Question("What value of 'y' is a solution to this equation", "10 y = 70", choices1, 0)

        val choices2 = arrayOf("y = 2", "y = 5")
        val q2 = Question("What value of 'y' is a solution to this equation", "3 y = 15", choices2, 1)

        val choices3 = arrayOf("y = 3", "y = 9")
        val q3 = Question("What value of 'y' is a solution to this equation", "2 y = 6", choices3, 0)


        questions = questionItemDB!!.allItems


        if (this.questions!!.size == 0) {
            Log.i("QUIZZ.KT", "INIT DB")
            questionItemDB!!.insertElement(q1)
            questionItemDB!!.insertElement(q2)
            questionItemDB!!.insertElement(q3)
            this.questions = questionItemDB!!.allItems
        } else {
            Log.i("QUIZZ.KT", "DATA FROM DB")
        }

    }
}
