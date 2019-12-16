package uoc.quizz.db.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


/**
 * Unit tests for the mocks Question
 */
@RunWith(MockitoJUnitRunner::class)

class QuestionTest {

    @Mock
    private lateinit var questions: ArrayList<Question>

    @Before
    fun setUp() {
        //initialize the questions list

        /***
         * Ejercicio 2 - Apartado a)

        this.questions = ArrayList()
        val choices1 = arrayOf("y = 7", "y = 23")
        val q1 = Question("What value of 'y' is a solution to this equation", "10 y = 70", choices1, 0)

        val choices2 = arrayOf("y = 2", "y = 5")
        val q2 = Question("What value of 'y' is a solution to this equation", "3 y = 15", choices2, 1)

        val choices3 = arrayOf("y = 3", "y = 9")
        val q3 = Question("What value of 'y' is a solution to this equation", "2 y = 6", choices3, 0)

        questions.add(q1);
        questions.add(q2);
        questions.add(q3);

        assertEquals(3, this.questions.size)

        */

        this.questions = mock(ArrayList<Question>()::class.java)

        val choices1 = arrayOf("y = 7", "y = 23")
        val mock_q1 = mock(Question("What value of 'y' is a solution to this equation", "10 y = 70", choices1, 0)::class.java)
        `when`(mock_q1.toString()).thenReturn("[title: What value of 'y' is a solution to this equation, example: 10 y = 70, choice1: y = 7, choice2: y = 23, rightChoice: 0]")

        val choices2 = arrayOf("y = 2", "y = 5")
        val mock_q2 = mock(Question("What value of 'y' is a solution to this equation", "3 y = 15", choices2, 1)::class.java)
        `when`(mock_q2.toString()).thenReturn("[title: What value of 'y' is a solution to this equation, example: 3 y = 15, choice1: y = 2, choice2: y = 5, rightChoice: 1]")

        val choices3 = arrayOf("y = 3", "y = 9")
        val mock_q3 = mock(Question("What value of 'y' is a solution to this equation", "2 y = 6", choices3, 0)::class.java)
        `when`(mock_q3.toString()).thenReturn("[title: What value of 'y' is a solution to this equation, example: 2 y = 6, choice1: y = 3, choice2: y = 9, rightChoice: 0]")

        verify(questions).add(mock_q1)
        verify(questions).add(mock_q2)
        verify(questions).add(mock_q3)

    }

    @Test
    fun getRigthChoice() {
        assertEquals(0, this.questions.get(0).rigthChoice) //q0
        assertEquals(1, this.questions.get(1).rigthChoice) //q1
        assertEquals(0, this.questions.get(2).rigthChoice) //q2
    }
}