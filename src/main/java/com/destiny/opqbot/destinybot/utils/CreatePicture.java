package com.destiny.opqbot.destinybot.utils;

import com.sun.org.apache.bcel.internal.generic.BREAKPOINT;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class CreatePicture {
	public Object[] generateImage(File file, String[][] content) {
		BufferedImage bufferedImage =null;
		try {
			Image image = ImageIO.read(file);
			int height = image.getHeight(null);
			int width = image.getWidth(null);
			int text_x = 0;
			int text_y = 0;
			int img_x = 0;
			int img_y = height;
			int line_y = width;
			Color color = new Color(89, 89, 89);
			 bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			// 文字去锯齿
			graphics.drawImage(image, 0, 0, width, height, null);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			if (image != null) {
				text_x = 0;
				text_y = 0;

				for (int i = 0; i < content.length; i++) {
					String[] tempData = content[i];
					color = new Color(89, 89, 89);
					

					for (int j = 0; j < tempData.length; j++) {
					
						switch(j) {
							//服务器名称
						case 0:
							text_x = 34;
							text_y = 48;
							color = new Color(255, 255, 255);
							// new cyFont().getDefinedFont(1, (float) 10.0)
							graphics.setColor(color);
							graphics.setFont(new Font("微软雅黑", Font.BOLD, 20));
							graphics.drawString(tempData[j], text_x, text_y);
							break;
							//服务器人数
							case 1:
								text_x = 407;
								text_y = 155;
								graphics.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 28));
								graphics.drawString(tempData[j], text_x, text_y);
							break;
							//PING
							case 2:
								text_x = 574;
								text_y = 155;
								graphics.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 28));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							//10-1
							case 3:
								text_x = 65;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 4:
								text_x = 65*2;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 5:
								text_x = 65*3;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 6:
								text_x = 65*4-5;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 7:
								text_x = 65*5-20;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 8:
								text_x = 65*6-20;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 9:
								text_x = 65*7-20;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 10:
								text_x = 65*8-20;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 11:
								text_x = 65*9-20;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 12:
								text_x = 65*10-25;
								text_y = 275;
								graphics.setFont(new Font("微软雅黑", Font.PLAIN, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
								//服务器任务
							case 13:
								text_x = 65;
								text_y = 329;
								color = new Color(145, 147, 168);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 14:
								text_x = 65;
								text_y = 380;
								color = new Color(145, 147, 168);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 15:
								text_x = 65;
								text_y = 429;
								color = new Color(145, 147, 168);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 12));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 16:
								text_x = 306;
								text_y = 345;
								color = new Color(28, 191, 255);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 20));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 17:
								text_x = 306;
								text_y = 425;
								color = new Color(28, 191, 255);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 20));
								graphics.drawString(tempData[j], text_x, text_y);
								break;
							case 18:
								text_x = 64;
								text_y = 512;
								color = new Color(255, 255, 255);
								graphics.setColor(color);
								graphics.setFont(new Font("微软雅黑", Font.BOLD, 14));
								String[] arr = tempData[j].split("\\|");
								if (arr.length<=0){
									graphics.drawString("无在线玩家", text_x, text_y);
									break;
								}
								int a = 0;
								String s = "";
								for (String str:arr) {
									s = s + str+"    ";
									graphics.drawString(s+"     ", text_x, text_y);
									text_y+=15;
									s="";
									/*
									并排列
									if (arr.length>=3){
										a+=1;
										if (a==3){//并列条数
											graphics.drawString(s+"     ", text_x, text_y);
											text_y+=15;
											a = 0;
											s="";
											continue;
										}
										if (a<=2) {
											graphics.drawString(s+"     ", text_x, text_y);
										}
									}else{
										graphics.drawString(s+"     ", text_x, text_y);
									}*/

								}






								break;
							default:

						}

					}
				}

			} else {
				System.out.println(GetDate.GetMDate()+"构建数据的图片对象为空...");
				//释放资源
				graphics.dispose();
			}

			System.out.println(GetDate.GetMDate()+" 构建图片数据 完成! 宽:" + width + " 高:" + height);
				//释放资源
			graphics.dispose();
		} catch (IOException e) {
			// log.info("io exception :" + e);
			e.printStackTrace();

		}
		return new Object[]{bufferedImage};
	}
}



