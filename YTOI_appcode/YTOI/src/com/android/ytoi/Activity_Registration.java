package com.android.ytoi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ytoi.utils.Act_Main;
import com.android.ytoi.utils.ConstantValue;
import com.android.ytoi.web.HttpHelper;

public class Activity_Registration extends Act_Main implements OnClickListener {

	private Context context = this;
	// For Date
	private int year;
	private int month;
	private int day;
	private Calendar c;
	static final int DATE_DIALOG_ID = 999;
	private EditText nameEDT, passEDT, rePassEDT, emailEDT;
	private String nameSTR, passSTR, rePassSTR, emailSTR, dateSTR;
	private Button submitBTN;
	private TextView dateTXT;
	Typeface type_BOLD, type_BOOK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_main);
		init();
		dateTXT.setOnClickListener(this);
		submitBTN.setOnClickListener(this);
	}

	public void init() {

		nameEDT = (EditText) findViewById(R.id.registration_EditText_name);
		passEDT = (EditText) findViewById(R.id.registration_EditText_password);
		rePassEDT = (EditText) findViewById(R.id.registration_EditText_confPassword);
		dateTXT = (TextView) findViewById(R.id.registration_EditText_date);
		emailEDT = (EditText) findViewById(R.id.registration_EditText_email);
		submitBTN = (Button) findViewById(R.id.registration_Button_Submit);
		type_BOLD = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Bold.otf");
		type_BOOK = Typeface.createFromAsset(getAssets(),
				"fonts/Novecentowide-Book.otf");

		c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		set_TypeFace_Control();
	}
	public void set_TypeFace_Control()
	{
		nameEDT.setTypeface(type_BOOK);
		passEDT.setTypeface(type_BOOK);
		rePassEDT.setTypeface(type_BOOK);
		dateTXT.setTypeface(type_BOOK);
		emailEDT.setTypeface(type_BOOK);
		submitBTN.setTypeface(type_BOOK);
	}
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registration_EditText_date:
			ConstantValue.hideSoftKeyboard(this);
			showDialog(DATE_DIALOG_ID);
			break;
		case R.id.registration_Button_Submit:
			nameSTR = nameEDT.getText().toString().trim();
			emailSTR = emailEDT.getText().toString().trim();

			passSTR = passEDT.getText().toString().trim();
			rePassSTR = rePassEDT.getText().toString().trim();
			if (!(nameSTR.equals("") && emailSTR.equals("")
					&& dateSTR.equals("") && passSTR.equals("") && rePassSTR
					.equals(""))) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");

					SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

					dateSTR = df2.format(format.parse(dateTXT.getText()
							.toString().trim()));

				} catch (java.text.ParseException e) {

					e.printStackTrace();
				}

				System.out.println("Date is :-" + dateSTR);
				if (ConstantValue.checkEmail(emailSTR)) {
					if (passSTR.equals(rePassSTR)) {

						if (isOnline()) // connected
						{
							String uri = null;
							uri = String.format(ConstantValue.URL_REGISTRATION);
							if (uri != null) {
								uri = uri.replace(" ", "%20");


								Bundle bundel = new Bundle();

								bundel.putString("email", emailSTR);
								bundel.putString("first_name",nameSTR);
								bundel.putString("last_name", "");
								bundel.putString("birth_date",dateSTR);
								bundel.putString("mobile_number", "");
								bundel.putString("street", "");
								bundel.putString("city","");
								bundel.putString("state", "");
								bundel.putString("country", "");
								bundel.putString("zipcode", "");
								bundel.putString("gender","");
								bundel.putString("fb_userid", "");
								bundel.putString("gmail_userid","");
								bundel.putString("password",passSTR);



								HttpHelper httpHelper = new HttpHelper(
										ConstantValue.REQUESTCODE_REGISTRATION,
										Activity_Registration.this,
										"Loading...", bundel,null);
								httpHelper.execute(uri);
							}
							nameEDT.setText("");
							emailEDT.setText("");
							dateTXT.setText("");
							passEDT.setText("");
							rePassEDT.setText("");

						}
					} else
						Toast.makeText(context, "Confirm password not match",
								Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(context, "Enter correct email",
							Toast.LENGTH_SHORT).show();

			} else
				Toast.makeText(context, "Must fill all Information",
						Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			setCurrentDateOnView();
		}
	};

	public void setCurrentDateOnView() {
		dateTXT.setText(new StringBuilder()
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));
	}


	public void setBackApiResponse(int requestcode, Object obj1, Object obj2) {

		if (requestcode == ConstantValue.REQUESTCODE_REGISTRATION) {
			if (((String)obj1).equalsIgnoreCase("1")) {
				startActivity(new Intent(context, Activity_Login.class));
				finish();
			} else
				Toast.makeText(context, obj2.toString(), Toast.LENGTH_SHORT)
						.show();
		}
	}

}
