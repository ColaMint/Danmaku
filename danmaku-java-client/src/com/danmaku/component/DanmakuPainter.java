package com.danmaku.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import com.danmaku.engine.DanmakuSet;
import com.danmaku.model.DanmakuModel;

public class DanmakuPainter {

	public void paintDanmaku(Graphics g, DanmakuSet danmakuSet) {

		Graphics2D g2 = (Graphics2D) g;

		try {
			danmakuSet.lock();
			Iterator<DanmakuModel> it = danmakuSet.getDanmakus().iterator();
			while (it.hasNext()) {
				DanmakuModel danmaku = it.next();

				/* The final string to be displayed in screen. */
				String danmakuStr = danmaku.username + ":" + danmaku.content;

				/* Prepare painting style and draw danmaku. */
				g2.setColor(getColor(danmaku.colorRed, danmaku.colorGreen,
						danmaku.colorBlue));
				g2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, danmaku.fontSize));
				g2.drawString(danmakuStr, danmaku.x, danmaku.y);

				/* Move danmaku by its speed. */
				danmaku.x -= danmaku.speed;

				/* Cal the length in pix of this danmaku. */
				FontRenderContext frc = g2.getFontRenderContext();
				Rectangle2D rect = g2.getFont()
						.getStringBounds(danmakuStr, frc);
				int lengthInPix = (int) rect.getWidth();

				/* If this danmaku is out of screen, remove it from list. */
				if (danmaku.x < -lengthInPix) {
					it.remove();
				}
			}
			danmakuSet.unLock();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private Color getColor(int R, int G, int B) {
		if (R >= 0 && R <= 255 && G >= 0 && G <= 255 && B >= 0 && B <= 255) {
			return new Color(R, G, B);
		} else {
			return new Color(0, 0, 0);
		}
	}
}
