package com.android.ytoi.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FontText extends TextView{
	

		public FontText(Context context) {
		    super(context);
		    init();
		}

		public FontText(Context context, int defStyle) {
		    super(context);
		    init(defStyle);
		}
		
		public FontText(Context context, AttributeSet attrs, int defStyle) {
		    super(context, attrs, defStyle);
		    init();
		}
		public FontText(Context context, AttributeSet attrs) {
		    super(context, attrs);
		    init();
		}
		void init() {
		   this.setTextSize(20);
		}
		void init(int size) {
			   this.setTextSize(size);
			}

//		// method to change font settings
//		void setFont(TypeFace tf){
//		   this.setTypeFace(tf);
//		}
		//add whatever method you want
}
