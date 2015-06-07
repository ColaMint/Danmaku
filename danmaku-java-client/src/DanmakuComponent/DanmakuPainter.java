package DanmakuComponent;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DanmakuModel.DanmakuModel;

public class DanmakuPainter {

	private List<DanmakuModel> danmakuList;
	private Random random;;

	private int boardWidth, boardHeight;

	public DanmakuPainter(int boardWidth,int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;

		danmakuList = new ArrayList<DanmakuModel>();
		random = new Random();

	}

	private void paintDanmaku(Graphics g, DanmakuModel danmaku,ArrayList<DanmakuModel> danmakuToBeRemoved) {
		String danmakuStr = danmaku.username + ":" + danmaku.content;
		
		Graphics2D g2 = (Graphics2D) g;		
		g.setColor(new Color(danmaku.colorRed,danmaku.colorGreen,danmaku.colorBlue));
		g2.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, danmaku.fontSize));
		
		FontRenderContext frc= g2.getFontRenderContext();
		Rectangle2D rect=g2.getFont().getStringBounds(danmakuStr, frc);
		int lengthInPix = (int) rect.getWidth();
		  
		g.drawString(danmaku.username + ":" + danmaku.content, danmaku.x,
				danmaku.y);
		
		danmaku.x -= danmaku.speed;
		if(danmaku.x < -lengthInPix){
			danmakuToBeRemoved.add(danmaku);
		}
	}

	public synchronized void paintAllDanmaku(Graphics g) {
		ArrayList<DanmakuModel> danmakuToBeRemoved = new ArrayList<DanmakuModel>();
		for (DanmakuModel danmaku : danmakuList) {
			paintDanmaku(g, danmaku,danmakuToBeRemoved);
		}
		for (DanmakuModel danmaku : danmakuToBeRemoved) {
			danmakuList.remove(danmaku);
		}

	}

	public synchronized void addDanmaku(ArrayList<DanmakuModel> newDanmakuList) {
		for (DanmakuModel danmaku : newDanmakuList) {
			addDanmakuHelp(danmaku);
		}
	}

	public synchronized void addDanmaku(DanmakuModel danmaku) {
		addDanmakuHelp(danmaku);
	}

	public void addDanmakuHelp(DanmakuModel danmaku) {
		danmaku.x = boardWidth;
		danmaku.y = random.nextInt(boardHeight);
		if (boardHeight - danmaku.y < danmaku.fontSize) {
			danmaku.y = boardHeight - danmaku.fontSize;
		}
		if(danmaku.y<15){
			danmaku.y = 15;
		}
		danmakuList.add(danmaku);
	}
}
