package com.example.calculateavaragefornotes


import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.new_layout.view.*


class MainActivity : AppCompatActivity() {

    private val lessons = arrayOf(
            "Matematik",
            "Edebiyat",
            "Fizik",
            "Tarih",
            "Biyoloji",
            "Coğrafya",
            "Kimya")
    private val allNotesArray = arrayOf("AA","BA","BB","CB","CC","DC","DD","FF")
    private val creditsArray = arrayOf(
            "1 Kredi",
            "2 Kredi",
            "3 Kredi",
            "4 Kredi",
            "5 Kredi",
            "6 Kredi",
            "7 Kredi",
            "8 Kredi",
            "9 Kredi",
            "10 Kredi",)
    private val allNotes: ArrayList<Lesson> = ArrayList(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val myEditText = findViewById<EditText>(R.id.autoEditText)
        myEditText.setTextColor(Color.WHITE)
        myEditText.setHintTextColor(Color.WHITE)
        val mySpinnerCredits = findViewById<Spinner>(R.id.spinnerCredits)
        val mySpinnerLetter = findViewById<Spinner>(R.id.spinnerLetter)
        val adapterSpinnerCredits = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, allNotesArray) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.setTextColor(Color.WHITE)
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.setTextColor(Color.WHITE)
                view.setBackgroundColor(Color.RED)
                return view
            }
        }
        val adapterSpinnerLetters = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, creditsArray) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.setTextColor(Color.WHITE)
                return view
            }
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                view.setBackgroundColor(Color.RED)
                textView.setTextColor(Color.WHITE)
                return view
            }
        }

        mySpinnerCredits.adapter = adapterSpinnerCredits
        mySpinnerLetter.adapter = adapterSpinnerLetters

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, lessons)
        autoEditText.setAdapter(adapter)
        if (rootLayout.childCount == 0){
            calculateButton.visibility = View.INVISIBLE
        }else {
            calculateButton.visibility = View.VISIBLE
        }
        button.setOnClickListener {
            if (!autoEditText.text.isNullOrEmpty()){
                val inflater = LayoutInflater.from(this)
                /*var inflater2 = layoutInflater
                var inflater3 = getSystemService(LAYOUT_INFLATER_SERVICE)*/
                val newLayoutView = inflater.inflate(R.layout.new_layout, null)
                /*newLayoutView.newAutoEditText*/
                val lessonName = autoEditText.text.toString()
                val credit = spinnerCredits.selectedItem.toString()
                val letter = spinnerLetter.selectedItem.toString()

                newLayoutView.textViewLesson.text = lessonName
                newLayoutView.textViewCredits.text = credit
                newLayoutView.textViewLetter.text = letter
                newLayoutView.newButton.setOnClickListener {
                    rootLayout.removeView(newLayoutView)
                    if (rootLayout.childCount == 0){
                        calculateButton.visibility = View.INVISIBLE
                    }else {
                        calculateButton.visibility = View.VISIBLE
                    }
                }
                rootLayout.addView(newLayoutView)
                calculateButton.visibility = View.VISIBLE
                restartView()
            }else{
                Toast.makeText(this, "Ders Adı Giriniz !", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun calculateAverage(view : View){
        var totalNotes = 0.0
        var totalCredit = 0.0
        for (i in 0 until rootLayout.childCount){
            val aLine = rootLayout.getChildAt(i)
            val templateLesson = Lesson(
                    aLine.textViewLesson.text.toString(),
                    aLine.textViewCredits.text.toString(),
                    aLine.textViewLetter.text.toString()
            )
            allNotes.add(templateLesson)
            println("LIST: $allNotes")
        }
        for (currentLesson in allNotes){
            totalNotes += lessonLetterToNumber(currentLesson.lessonLetter) * creditsToNumber(currentLesson.lessonCredit)
            totalCredit += (creditsToNumber(currentLesson.lessonCredit))
        }
        var average = totalNotes / totalCredit
        Toast.makeText(this, "ORTALAMA: ${average.changeNumber(2)}", Toast.LENGTH_LONG).show()
        allNotes.clear()
    }

    private fun Double.changeNumber(howManyLastDot: Int) = java.lang.String.format("%.${howManyLastDot}f", this)

    private fun lessonLetterToNumber(lessonLetter: String): Double{
        var value = 0.0
        when(lessonLetter){
            "AA" -> value = 4.0
            "BA" -> value = 3.5
            "BB" -> value = 3.0
            "CB" -> value = 2.5
            "CC" -> value = 2.0
            "DC" -> value = 1.5
            "DD" -> value = 1.0
            "FF" -> value = 0.0
        }
        return value
    }

    private fun creditsToNumber(credit: String): Double{
        var value = 0.0
        when(credit){
            "1 Kredi" -> value = 1.0
            "2 Kredi" -> value = 2.0
            "3 Kredi" -> value = 3.0
            "4 Kredi" -> value = 4.0
            "5 Kredi" -> value = 5.0
            "6 Kredi" -> value = 6.0
            "7 Kredi" -> value = 7.0
            "8 Kredi" -> value = 8.0
            "9 Kredi" -> value = 9.0
            "10 Kredi" -> value = 10.0
        }
        return value
    }

    private fun restartView(){
        autoEditText.setText("")
        spinnerLetter.setSelection(0)
        spinnerLetter.setSelection(0)
    }



}