package uoc.quizz.db.model

class Question(
    var title: String,
    var example: String,
    var choices: Array<String>,
    var rigthChoice: Int) {

    constructor(title: String, example: String, choice1: String, choice2: String, rightChoice: Int) : this(title, example, arrayOf<String>(choice1, choice2), rightChoice) {}

    override fun toString(): String {
        return "[title: " + this.title + ", example: " + this.example + ", choice1: " + this.choices[0] +
                ", choice2: " + this.choices[1] + ", rightChoice:" + this.rigthChoice + "]"
    }

}
