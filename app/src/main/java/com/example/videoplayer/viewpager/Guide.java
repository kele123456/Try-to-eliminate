package com.example.videoplayer.viewpager;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  ���������ҳ	 
 * @author:	Vongvia  ��ӭ��λͯЬ������ ��441256563
 * @date:	2015.11.17
 * @version	V1.0   
 */
import java.util.ArrayList;
import java.util.List;



import android.R.interpolator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.videoplayer.R;
import com.example.videoplayer.acitivity.MainActivity;

public class Guide extends Activity implements OnPageChangeListener{
	
	
	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private ImageView[] dots;
	private int[] ids={R.id.iv1,R.id.iv2,R.id.iv3};
	private Button start_btn;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	   super.onCreate(savedInstanceState);
	   setContentView(com.example.videoplayer.R.layout.guide);
	   initViews();
	   initDots();
	
	}
	
	private void initViews()
	{
	 LayoutInflater inflater=LayoutInflater.from(this);
	 views=new ArrayList<View>();
	 views.add(inflater.inflate(R.layout.vager_one, null));
	 views.add(inflater.inflate(R.layout.vager_two, null));
	 views.add(inflater.inflate(R.layout.vager_three, null));
	 
	 vpAdapter=new ViewPagerAdapter(views,this);
	 vp=(ViewPager)findViewById(R.id.viewpager);
	 vp.setAdapter(vpAdapter);
	 
	 start_btn=(Button) views.get(2).findViewById(R.id.start_btn);
	 start_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(Guide.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	});
	 
	 vp.setOnPageChangeListener(this);
	}
	
  private void initDots()
  {
	  dots=new ImageView[views.size()];
	  for(int i=0;i<views.size();i++)
		  dots[i]=(ImageView) findViewById(ids[i]);
  }

@Override
public void onPageScrollStateChanged(int arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void onPageScrolled(int arg0, float arg1, int arg2) {
	// TODO Auto-generated method stub
	
}

@Override
public void onPageSelected(int arg0) {
	// TODO Auto-generated method stub
	for(int i=0;i<ids.length;i++)
	{
		if(arg0==i)
		{
			dots[i].setImageResource(R.drawable.login_point_selected);
		}
		else 
		{
			dots[i].setImageResource(R.drawable.login_point_selected);
		
		}
	}
	
}
}
