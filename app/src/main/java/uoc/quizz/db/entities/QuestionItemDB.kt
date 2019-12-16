package uoc.quizz.db.entities

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.util.Log

import java.util.ArrayList

import uoc.quizz.db.helper.QuizzesHelper
import uoc.quizz.db.model.Question

class QuestionItemDB// To prevent someone from accidentally instantiating the contract class,
// give it an empty constructor.
(context: Context) {

    private val dbHelper: QuizzesHelper

    /**
     * Method to get all the questions of the database
     *
     * @return Question List
     */
    // The table to query
    // The columns to return
    // The columns for the WHERE clause
    // The values for the WHERE clause
    // don't group the rows
    // don't filter by row groups
    // The sort order
    // make sure to close the cursor
    val allItems: List<Question>
        get() {

            val questions = ArrayList<Question>()

            val allColumns = arrayOf(BaseColumns._ID, QuestionEntry.COLUMN_NAME_TITLE, QuestionEntry.COLUMN_NAME_EXAMPLE, QuestionEntry.COLUMN_NAME_CHOICE1, QuestionEntry.COLUMN_NAME_CHOICE2, QuestionEntry.COLUMN_NAME_RIGHT_CHOICE)

            val cursor = dbHelper.readableDatabase.query(
                    QuestionEntry.TABLE_NAME,
                    allColumns, null, null, null, null, null
            )

            cursor.moveToFirst()

            while (!cursor.isAfterLast) {
                val question = Question(getItemTitle(cursor), getItemExample(cursor),
                        getItemChoice1(cursor), getItemChoice2(cursor), getItemRightChoice(cursor))
                questions.add(question)
                cursor.moveToNext()
            }
            cursor.close()
            dbHelper.readableDatabase.close()
            return questions
        }

    init {
        // Create new helper
        dbHelper = QuizzesHelper(context)
    }

    /* Inner class that defines the table contents */
    abstract class QuestionEntry : BaseColumns {
        companion object {
            internal val TABLE_NAME = "entry"
            internal val COLUMN_NAME_TITLE = "title"
            internal val COLUMN_NAME_EXAMPLE = "example"
            internal val COLUMN_NAME_CHOICE1 = "choice1"
            internal val COLUMN_NAME_CHOICE2 = "choice2"
            internal val COLUMN_NAME_RIGHT_CHOICE = "rightChoice"


            val CREATE_TABLE = "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    BaseColumns._ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_EXAMPLE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_CHOICE1 + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_CHOICE2 + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_RIGHT_CHOICE + INT_TYPE + " )"

            val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        }
    }

    /**
     * Method to create new element in the database
     *
     * @param question the question
     */
    fun insertElement(question: Question): Question {
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(QuestionEntry.COLUMN_NAME_TITLE, question.title)
        values.put(QuestionEntry.COLUMN_NAME_EXAMPLE, question.example)
        values.put(QuestionEntry.COLUMN_NAME_CHOICE1, question.choices[0])
        values.put(QuestionEntry.COLUMN_NAME_CHOICE2, question.choices[1])
        values.put(QuestionEntry.COLUMN_NAME_RIGHT_CHOICE, question.rigthChoice)


        // Insert the new row, returning the primary key value of the new row
        val id = dbHelper.writableDatabase.insert(
                QuestionEntry.TABLE_NAME, null,
                values)

        Log.i("QUIZZES", "ADD NEW ITEM$question")

        dbHelper.writableDatabase.close()
        return getItem(id)
    }

    /**
     * Method to get a shopping elements from id
     *
     * @param id the id of the product
     * @return the Question item
     */
    private fun getItem(id: Long): Question {
        val allColumns = arrayOf(BaseColumns._ID, QuestionEntry.COLUMN_NAME_TITLE, QuestionEntry.COLUMN_NAME_EXAMPLE, QuestionEntry.COLUMN_NAME_CHOICE1, QuestionEntry.COLUMN_NAME_CHOICE2, QuestionEntry.COLUMN_NAME_RIGHT_CHOICE)
        val selection = BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = dbHelper.readableDatabase.query(
                QuestionEntry.TABLE_NAME, // The table to query
                allColumns, // The columns to return
                selection, // The columns for the WHERE clause
                selectionArgs, null, null, null// The sort order
        )// The values for the WHERE clause
        // don't group the rows
        // don't filter by row groups

        cursor.moveToFirst()
        val question = Question(getItemTitle(cursor), getItemExample(cursor),
                getItemChoice1(cursor), getItemChoice2(cursor), getItemRightChoice(cursor))

        // make sure to close the cursor
        cursor.close()
        dbHelper.readableDatabase.close()
        return question
    }

    /**
     * Method to clear all the elements
     */
    fun clearAllItems() {
        dbHelper.writableDatabase.delete(QuestionEntry.TABLE_NAME, null, null)
        dbHelper.writableDatabase.close()
    }

    private fun getItemTitle(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_NAME_TITLE))
    }

    private fun getItemExample(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_NAME_EXAMPLE))
    }

    private fun getItemChoice1(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_NAME_CHOICE1))
    }

    private fun getItemChoice2(cursor: Cursor): String {
        return cursor.getString(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_NAME_CHOICE2))
    }


    private fun getItemRightChoice(cursor: Cursor): Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(QuestionEntry.COLUMN_NAME_RIGHT_CHOICE))
    }

    companion object {
        private val INT_TYPE = " INTEGER"
        private val TEXT_TYPE = " TEXT"
        private val COMMA_SEP = ","
    }
}

