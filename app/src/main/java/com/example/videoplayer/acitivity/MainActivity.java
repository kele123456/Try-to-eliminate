package com.example.videoplayer.acitivity;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  ������������Լ�������	 
 * @author:	Vongvia  ��ӭ��λͯЬ������ ��441256563
 * @date:	2015.11.17
 * @version	V1.0   
 */
import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;



import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.*;

import com.example.videoplayer.R;
import com.example.videoplayer.anim.ExplosionField;

public class MainActivity extends Activity
{

	private ExplosionField mExplosionField;
	// ͼƬ�ؼ�����
	static ImageView[] PictureBoxs = new ImageView[12];
	// ͼƬ����
	static int[] Index = new int[12];

	// �жϵ���Ĵ��������Ϊ2
	private int Count = 0;
	// �洢���ͼƬ����������
	private int[] ClickID = new int[2];

	// ���������������Ϊ3
	int wrongnum = 0;
	// ����Ƿ��Ѿ������Ϸ��������ʾ�û���һ�ε������Ϸ����ܵ�����ƣ�
	private int alstart = 0;
	// ��Ϸ��ʱ��
	int alltime = 0;
	private TextView tvShow;// ��ʾʱ��
	private TextView TV_num;// ��ʾ�������
	private int socore = 0; // ����
	int setWrongnum = 3;
	int settime = 60;
	int memtime = 3;
	Boolean canTouch = false;
	Message msg;

	// ���ڱ��� ������Ϣ
	SharedPreferences preferences;
	SharedPreferences.Editor sp_edit;
	// ����ͼƬ��Դ
	public static int[] ImageSource =
	{ R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,
			R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11,
			R.drawable.image12, R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16,
			R.drawable.image17, R.drawable.image18, R.drawable.image19, R.drawable.image20, R.drawable.image21,
			R.drawable.image22, R.drawable.image23, R.drawable.image24, R.drawable.image25, R.drawable.image26,
			R.drawable.image27, R.drawable.image28, R.drawable.image29, R.drawable.image30, R.drawable.image31,
			R.drawable.image32, R.drawable.image33, R.drawable.image34, R.drawable.image35, R.drawable.image36,
			R.drawable.image36 };

	void init()
	{

		// ��ImageView���ְ�ID
		PictureBoxs[0] = (ImageView) findViewById(R.id.ImageView00);
		PictureBoxs[1] = (ImageView) findViewById(R.id.ImageView01);
		PictureBoxs[2] = (ImageView) findViewById(R.id.ImageView02);
		PictureBoxs[3] = (ImageView) findViewById(R.id.ImageView03);
		PictureBoxs[4] = (ImageView) findViewById(R.id.ImageView04);
		PictureBoxs[5] = (ImageView) findViewById(R.id.ImageView05);
		PictureBoxs[6] = (ImageView) findViewById(R.id.ImageView06);
		PictureBoxs[7] = (ImageView) findViewById(R.id.ImageView07);
		PictureBoxs[8] = (ImageView) findViewById(R.id.ImageView08);
		PictureBoxs[9] = (ImageView) findViewById(R.id.ImageView09);
		PictureBoxs[10] = (ImageView) findViewById(R.id.ImageView10);
		PictureBoxs[11] = (ImageView) findViewById(R.id.ImageView11);
	}

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn_exit = (Button) findViewById(R.id.Button_Exit);
		Button btn_about = (Button) findViewById(R.id.Button_About);
		Button btn_newgame = (Button) findViewById(R.id.Button_NewGame);

		/*
		 * Inflater inflater=new Inflater();
		 * 
		 * final LinearLayout rootView=(LinearLayout)inflater.inflate(buf,
		 * offset, byteCount) ScaleAnimation sAnimation=new ScaleAnimation(0,
		 * 1,0,1); sAnimation.setDuration(5000);
		 * 
		 * LayoutAnimationController la=new
		 * LayoutAnimationController(sAnimation, 0.5f);
		 */
		preferences = getSharedPreferences("CardsGame", MODE_PRIVATE);
		sp_edit = preferences.edit();

		setWrongnum = preferences.getInt("Wrongnum", 3);
		settime = preferences.getInt("gametime", 100);
		memtime = preferences.getInt("memtime", 3);

		tvShow = (TextView) findViewById(R.id.tv_Time);
		TV_num = (TextView) findViewById(R.id.IV_num);

		mExplosionField = ExplosionField.attach2Window(this);// ������Ч�����

		init();

		// ���������Ϸ��
		btn_newgame.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				alltime = 0; // ʱ����0
				wrongnum = 0; // ���������0
				socore = 0; // ������0
                canTouch=false;
				TV_num.setText(" " + wrongnum);
				Index = GetIndex.Getnum();// ��������

				if (alstart != 0) // ����Ѿ���ʼ����Ϸ��˵���п������������ˣ����ͽ��Ѿ���������������ʾ����
				{
					reset(findViewById(R.id.IV_Group));
					Ahandler.removeCallbacks(myRunnable);// ע����ʱ
				}
				// ��ͼƬ�ؼ�������Դ
				for (int i = 0; i < 12; i++)
				{
					PictureBoxs[i].setImageResource(ImageSource[Index[i]]);
				}
				// ��ʼ��ʱ
				Ahandler.post(myRunnable);

				// ��ʼ���ű�������
				Intent intent = new Intent(MainActivity.this, SoundService.class);
				intent.putExtra("playing", true);
				startService(intent);

				// ����Ѿ���ʼ����Ϸ
				alstart++;

			}
		});

		// ����˳���ť
		btn_exit.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Ahandler.removeCallbacks(myRunnable);// ֹͣ��ʱ
				stopmusic();
				finish();

			}
		});

		// �������
		btn_about.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).setTitle("关于")
						.setMessage("无")
						.setNegativeButton("退出",null)
						.create();
				alertDialog.show();

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			// �����˳��Ի���
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// ���öԻ������
			isExit.setTitle("ϵͳ��ʾ");
			// ���öԻ�����Ϣ
			isExit.setMessage("ȷ��Ҫ�˳���");
			// ���ѡ��ť��ע�����
			isExit.setButton("ȷ��", listener);
			isExit.setButton2("ȡ��", listener);
			// ��ʾ�Ի���
			isExit.show();

		}

		return false;

	}

	/** �����Ի��������button����¼� */
	OnClickListener listener = new OnClickListener()
	{
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
			case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����
			{
				Ahandler.removeCallbacks(myRunnable);// ֹͣ��ʱ
				stopmusic();
				finish();
				break;
			}
			case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
				break;
			default:
				break;
			}
		}
	};

	public void settingB(View source)
	{
		final LinearLayout set = (LinearLayout) getLayoutInflater().inflate(R.layout.settings, null);
		EditText etw = (EditText) set.findViewById(R.id.ev_wrongnum);
		EditText ett = (EditText) set.findViewById(R.id.ev_time);
		EditText etm = (EditText) set.findViewById(R.id.ev_mem);
		etw.setText("" + setWrongnum);
		ett.setText("" + settime);
		etm.setText("" + memtime);
		new AlertDialog.Builder(this)
				// ���öԻ����ͼ��
				.setIcon(R.drawable.set_icon)
				// ���öԻ���ı���
				.setTitle("修改设置")
				// ���öԻ�����ʾ��View����
				.setView(set)
				// Ϊ�Ի�������һ����ȷ������ť
				.setPositiveButton("确定", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{

						EditText etw = (EditText) set.findViewById(R.id.ev_wrongnum);
						EditText ett = (EditText) set.findViewById(R.id.ev_time);
						EditText etm = (EditText) set.findViewById(R.id.ev_mem);

						setWrongnum = Integer.parseInt(etw.getText().toString());
						settime = Integer.parseInt(ett.getText().toString());
						memtime = Integer.parseInt(etm.getText().toString());
						// ������д�� ����

						sp_edit.putInt("Wrongnum", setWrongnum);
						sp_edit.putInt("gametime", settime);
						sp_edit.putInt("memtime", memtime);
						sp_edit.commit();
						Toast.makeText(getApplicationContext(), "���óɹ�~", Toast.LENGTH_LONG).show();

					}
				})
				// Ϊ�Ի�������һ����ȡ������ť
				.setNegativeButton("退出", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// ȡ����¼�������κ����顣
					}
				})
				// ����������ʾ�Ի���
				.create().show();

	}

	// ֹͣ����
	void stopmusic()
	{
		Intent intent = new Intent(MainActivity.this, EffectService.class);
		intent.putExtra("what", "quit");
		startService(intent);

		Intent nintent = new Intent(MainActivity.this, SoundService.class);
		nintent.putExtra("quit", true);
		startService(nintent);
	}

	// ���¿�ʼ��������ImageView����Ϊ������ʾ
	private void reset(View root)
	{
		mExplosionField.clear();

		if (root instanceof ViewGroup)
		{
			ViewGroup parent = (ViewGroup) root;
			for (int i = 0; i < parent.getChildCount(); i++)
			{
				reset(parent.getChildAt(i));
			}
		} else
		{
			root.setScaleX(1);
			root.setScaleY(1);
			root.setAlpha(1);
		}
	}

	// ��ʱ��
	private Handler Ahandler = new Handler();

	private Runnable myRunnable = new Runnable()
	{
		public void run()
		{
			Ahandler.postDelayed(this, 1000);
			tvShow.setText("" + Integer.toString(alltime++));
			if (alltime == memtime) // 3��� �����Ʒ���
			{
				CardTurn();
				canTouch = true;
			} else if (alltime == settime)
			{
				// ��Ϸʱ�䵽
				canTouch=false;
				Intent nintent = new Intent(MainActivity.this, SoundService.class);
				nintent.putExtra("quit", true);
				startService(nintent);

				Intent intent = new Intent(MainActivity.this, EffectService.class);
				intent.putExtra("what", "lose");
				startService(intent);

				Toast.makeText(getApplicationContext(), "��Ϸʱ�䵽~~������", Toast.LENGTH_LONG).show();
				tvShow.setText("" + alltime);
				alltime = 0;
				Ahandler.removeCallbacks(myRunnable);// ֹͣ��ʱ
			}

		}
	};

	/**
	 * �Ʒ�ת
	 */
	public static void CardTurn()
	{
		for (int i = 0; i < 12; i++)
		{
			PictureBoxs[i].setImageResource(R.drawable.card);
		}
	}

	// ������ƺ�Ĵ���
	public void CardTouch(View v)
	{
		if (canTouch)
		{
			Intent intent = new Intent(MainActivity.this, EffectService.class);
			intent.putExtra("what", "selected");
			startService(intent);
			if (alstart == 0 || (wrongnum == (setWrongnum + 1) && alstart != 0))
			{
				// ��Ϸ�տ�ʼ��û�������Ϸ�������������ܵ��
				Toast.makeText(this, "�ȵ��������Ϸ��Ŷ~", Toast.LENGTH_SHORT).show();

			} else
			{
				Count++;// �������+1

				if (Count > 2) // �������Ѹ�ٵ����3�Σ�������β�����
				{
					Count = 2;
				} else
				{
					// ���ݵ���Ƶ�ID�����ж� ���������ͼƬ�Ƿ���ͬ����ClickId���档�����Ʒ�����
					switch (v.getId())
					{
					case R.id.ImageView00:
						ClickID[Count - 1] = 0;
						PictureBoxs[0].setImageResource(ImageSource[Index[0]]);

						break;
					case R.id.ImageView01:
						ClickID[Count - 1] = 1;
						PictureBoxs[1].setImageResource(ImageSource[Index[1]]);

						break;
					case R.id.ImageView02:
						ClickID[Count - 1] = 2;
						PictureBoxs[2].setImageResource(ImageSource[Index[2]]);

						break;
					case R.id.ImageView03:
						ClickID[Count - 1] = 3;
						PictureBoxs[3].setImageResource(ImageSource[Index[3]]);

						break;
					case R.id.ImageView04:
						ClickID[Count - 1] = 4;
						PictureBoxs[4].setImageResource(ImageSource[Index[4]]);

						break;
					case R.id.ImageView05:
						ClickID[Count - 1] = 5;
						PictureBoxs[5].setImageResource(ImageSource[Index[5]]);

						break;
					case R.id.ImageView06:
						ClickID[Count - 1] = 6;
						PictureBoxs[6].setImageResource(ImageSource[Index[6]]);

						break;
					case R.id.ImageView07:
						ClickID[Count - 1] = 7;
						PictureBoxs[7].setImageResource(ImageSource[Index[7]]);

						break;
					case R.id.ImageView08:
						ClickID[Count - 1] = 8;
						PictureBoxs[8].setImageResource(ImageSource[Index[8]]);

						break;
					case R.id.ImageView09:
						ClickID[Count - 1] = 9;
						PictureBoxs[9].setImageResource(ImageSource[Index[9]]);

						break;
					case R.id.ImageView10:
						ClickID[Count - 1] = 10;
						PictureBoxs[10].setImageResource(ImageSource[Index[10]]);

						break;
					case R.id.ImageView11:
						ClickID[Count - 1] = 11;
						PictureBoxs[11].setImageResource(ImageSource[Index[11]]);
						break;
					default:
						// ֻ����12��ImageView���˵���¼���һ����˵�˴������ܴ���
						break;
					}
				}

				if (Count == 2)// ��������ν����ж�
				{

					// ����Handler����Ϊ��Ҫ�ӳ�0.5�룬��ʾ���ڶ����Ʒ����Ч�������򽫲���ʾ�ڶ����Ʒ�������ֱ�ӽ����ж�
					Handler handler = new Handler();
					handler.postDelayed(new Runnable()
					{

						public void run()
						{

							if (ClickID[0] != ClickID[1] && Index[ClickID[0]] == Index[ClickID[1]])// �ɹ�����
							{
								// ������Ч
								mExplosionField.explode(PictureBoxs[ClickID[0]]);
								mExplosionField.explode(PictureBoxs[ClickID[1]]);
								// v.setOnClickListener(null);

								// ������1
								socore++;

								// ������Ч
								Intent eintent = new Intent(MainActivity.this, EffectService.class);
								eintent.putExtra("what", "effect");
								startService(eintent);

								// �ܷ�����6��Ҳ����������6�飬��Ϸʤ��
								if (socore == 6)
								{

									// �ر���Ϸ��������
									Intent nintent = new Intent(MainActivity.this, SoundService.class);
									nintent.putExtra("quit", true);
									startService(nintent);

									// ����ʤ����Ч
									Intent intent = new Intent(MainActivity.this, EffectService.class);
									intent.putExtra("what", "win");
									startService(intent);
									// ��ʾ��Ϸʤ��
									Toast.makeText(getApplicationContext(), "恭喜你，胜利", Toast.LENGTH_LONG).show();

									// �̶���Ϸʱ��
									tvShow.setText("" + alltime);

									Ahandler.removeCallbacks(myRunnable);// ֹͣ��ʱ
								}
							} else
							{
								// �����������ٷ���ȥ
								PictureBoxs[ClickID[0]].setImageResource(R.drawable.card);
								PictureBoxs[ClickID[1]].setImageResource(R.drawable.card);
								// �������+1
								wrongnum++;
								if (wrongnum == (setWrongnum + 1))
									TV_num.setText("" + (wrongnum - 1));
								else
									TV_num.setText("" + wrongnum);
								if (wrongnum == (setWrongnum + 1))
								{
									// ��������ﵽ���ޣ�ʧ��

									Intent nintent = new Intent(MainActivity.this, SoundService.class);
									nintent.putExtra("quit", true);
									startService(nintent);

									Intent intent = new Intent(MainActivity.this, EffectService.class);
									intent.putExtra("what", "lose");
									startService(intent);

									Toast.makeText(getApplicationContext(), "你已经错误3次，请重新开始", Toast.LENGTH_LONG)
											.show();
									tvShow.setText("" + alltime);
									alltime = 0;
									Ahandler.removeCallbacks(myRunnable);// ֹͣ��ʱ
									canTouch=false;
								}
							}
							Count = 0;
						}

					}, 500);
				}
			}
		}
	}

}
