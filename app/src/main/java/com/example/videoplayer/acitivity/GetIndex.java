package com.example.videoplayer.acitivity;
/**
 * All rights Reserved, Designed By Vongvia
 * @Title:  �õ�������ĺ�������6�����ظ�	 
 * @author:	Vongvia  ��ӭ��λͯЬ������ ��441256563
 * @date:	2015.11.17
 * @version	V1.0   
 */
import java.util.Random;

public class GetIndex {
	 //���������
	  static int[] Getnum()
     {
          //ͼƬ���������
         int[] temp = new int[12];
         int[] temp_ = new int[6];
          //ͼƬ�������������
         int[] index = new int[6];
         int temp1;
         Random r = new Random();
         for (int i = 0; i < 6; i++)
         {
             temp1 = r.nextInt(36);//0-35
             temp[i] = GetNumber(temp, i, 1, 36, temp1, r);
         }
          //����ǰ���6�������
         for (int i = 0; i < 6; i++)
         {
             temp_[i] = temp[i];
         }
          //����0-5�Ĳ��ظ������
         for (int i = 0; i < 6; i++)
         {
             //�����ֵ
             index[i] = r.nextInt(6);
             //��������ظ�Ԫ��
             for (int j = i; j < 6; j++)
             {
                 if (index[j] == index[i])
                 {
                     index[j] = r.nextInt(6);
                 }
             }
         }
         //��ʣ�µ�6��Ԫ�ظ�ֵ
       
         int[] num = new int[6];
         for (int i = 0; i < num.length; i++)
         {
             num[i] = i + 1;
         }
         Random w= new Random();
         //������ս��
         int[] result = new int[6];
         int max = 6;//������������ֵ
         for (int j = 0; j < result.length; j++)
         {
             
             int nindex = w.nextInt(max);
             //ȡ��������λ���������
             result[j] = num[nindex]-1;
             //�����һ�����滻���ѱ�����result�е���
             
             //����num�����д�0��max-1�ֶ���δ�������result���Ҳ��ظ�������
             num[nindex] = num[max - 1];
             //��������������������ֵ��һ
             max--;
         }
       
         for (int i = 6; i < 12; i++)
         {
             //�����ֵ
             temp[i] = temp_[result[i-6]];
         }
         return temp;
     }
	 static int GetNumber(int[] a, int index, int minValue, int maxValue, int temp, Random r)
     {
         for (int i = 0; i < index; i++)
         {
             if (a[i] == temp)
             {
                 int newTemp = r.nextInt(maxValue)+minValue;
                 a[index] = newTemp;
                 return GetNumber(a, index, minValue, maxValue, newTemp, r);
             }
         }
         return temp;
     }

	 
}
