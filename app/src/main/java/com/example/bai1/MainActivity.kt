package com.example.bai1

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bai1.AddressHelper
class MainActivity : AppCompatActivity() {

    private lateinit var edtMSSV: EditText
    private lateinit var edtHoTen: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPhone: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var btnShowCalendar: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spinnerWard: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerProvince: Spinner
    private lateinit var chkSports: CheckBox
    private lateinit var chkMovies: CheckBox
    private lateinit var chkMusic: CheckBox
    private lateinit var chkAgreeTerms: CheckBox
    private lateinit var btnSubmit: Button
    private lateinit var addressHelper: AddressHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtMSSV = findViewById(R.id.edtMSSV)
        edtHoTen = findViewById(R.id.edtHoTen)
        edtEmail = findViewById(R.id.edtEmail)
        edtPhone = findViewById(R.id.edtPhone)
        radioGroupGender = findViewById(R.id.radioGroupGender)
        btnShowCalendar = findViewById(R.id.btnShowCalendar)
        calendarView = findViewById(R.id.calendarView)
        spinnerWard = findViewById(R.id.spinnerWard)
        spinnerDistrict = findViewById(R.id.spinnerDistrict)
        spinnerProvince = findViewById(R.id.spinnerProvince)
        chkSports = findViewById(R.id.chkSports)
        chkMovies = findViewById(R.id.chkMovies)
        chkMusic = findViewById(R.id.chkMusic)
        chkAgreeTerms = findViewById(R.id.chkAgreeTerms)
        btnSubmit = findViewById(R.id.btnSubmit)
        addressHelper = AddressHelper(resources)

        val provinces = addressHelper.getProvinces()
        val provinceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerProvince.adapter = provinceAdapter

        spinnerProvince.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = provinces[position]
                val districts = addressHelper.getDistricts(selectedProvince)
                val districtAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, districts)
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerDistrict.adapter = districtAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        spinnerDistrict.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedProvince = spinnerProvince.selectedItem as String
                val selectedDistrict = spinnerDistrict.selectedItem as String
                val wards = addressHelper.getWards(selectedProvince, selectedDistrict)
                val wardAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, wards)
                wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerWard.adapter = wardAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        })

        btnShowCalendar.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnSubmit.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(this, "Form đã được điền đầy đủ!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (edtMSSV.text.isBlank()) {
            edtMSSV.error = "Vui lòng nhập MSSV"
            return false
        }
        if (edtHoTen.text.isBlank()) {
            edtHoTen.error = "Vui lòng nhập Họ tên"
            return false
        }
        if (edtEmail.text.isBlank()) {
            edtEmail.error = "Vui lòng nhập Email"
            return false
        }
        if (edtPhone.text.isBlank()) {
            edtPhone.error = "Vui lòng nhập Số điện thoại"
            return false
        }
        if (radioGroupGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Vui lòng chọn Giới tính", Toast.LENGTH_SHORT).show()
            return false
        }
        if (spinnerWard.selectedItemPosition == 0 || spinnerDistrict.selectedItemPosition == 0 || spinnerProvince.selectedItemPosition == 0) {
            Toast.makeText(this, "Vui lòng chọn Nơi ở hiện tại", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!chkAgreeTerms.isChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với các điều khoản", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
