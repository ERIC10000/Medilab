package com.modcom.medilabmemberapp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.modcom.medilabmemberapp.constants.Constants
import com.modcom.medilabmemberapp.helpers.ApiHelper
import com.modcom.medilabmemberapp.helpers.PrefsHelper
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class AddDependantFragment : Fragment() {

    private lateinit var btnDatePicker : Button
    private lateinit var editTextDate: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_dependant, container, false)


        // Lets find surname and others editTexts, and get their values(.text)
        // === surname
        val etSurname : EditText = view.findViewById(R.id.editSurname)
        val surname = etSurname.text

        // === others
        val etOthers : EditText = view.findViewById(R.id.editOthers)
        val others = etOthers.text

        // == find DatePicker Button
        btnDatePicker = view.findViewById(R.id.buttonDatePicker)
        editTextDate = view.findViewById(R.id.editTextDate)

        btnDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        // Getting the Member ID:Logged in Member(Prefs)
        // Check if Member is Logged in After AddDependant Btn Clicked!
        val btnAddDependant : Button = view.findViewById(R.id.buttonAddDependant)
        btnAddDependant.setOnClickListener {

            val token = PrefsHelper.getPrefs(requireContext(), "refresh_token")
            if(token.isEmpty()){
                Toast.makeText(requireContext(), "Not Logged In", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            else{
                Toast.makeText(requireContext(), "LoggedIn!!!", Toast.LENGTH_SHORT).show()
                val member_id = PrefsHelper.getPrefs(requireContext(), "LoggedMember_id")
                val dob = PrefsHelper.getPrefs(requireContext(), "dep_dob")

                // SENDING: surname, others, member_id and dob as a JSONObject PayLoad
                val api = Constants.BASE_URL + "/add_dependant"
                val payload = JSONObject()
                payload.put("member_id", member_id)
                payload.put("surname", surname)
                payload.put("others", others)
                payload.put("dob", dob)


                val apiHelper = ApiHelper(requireContext())
                apiHelper.post(api, payload, object: ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {
                        TODO("Not yet implemented")
                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(requireContext(), "${result.toString()}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(requireContext(), "${result.toString()}", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }

        return view
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
                // Handle the selected date
                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                editTextDate.setText(selectedDate)

                PrefsHelper.savePrefs(requireContext(), "dep_dob", selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

}