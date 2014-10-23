package com.android.ytoi.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class ReUseMethod {

	private  Act_Main act_BackmainAct;
	
	public void displayAlertDialog(final String actValue,String title,String msg,String firstBtn,String secondBtn,final int flag,Act_Main act_mainF) {

		AlertDialog.Builder alertDialogBuilder = null;


		alertDialogBuilder = new AlertDialog.Builder(act_mainF);
		alertDialogBuilder.setTitle(title);

		alertDialogBuilder.setMessage(msg);

		alertDialogBuilder.setCancelable(false);

		alertDialogBuilder.setPositiveButton(firstBtn,new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				
				
				dialog.dismiss();


			}
		});


		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}



}
