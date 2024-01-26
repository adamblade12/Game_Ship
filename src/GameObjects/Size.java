package GameObjects;

import java.awt.image.BufferedImage;

import graphics.Assets;

public enum Size {

	
	BIG(2,Assets.meds),MED(2,Assets.small),SMALL(2,Assets.tinies),TINY(0,null);
	
	public int quantity;
	public BufferedImage[] tecturas;
	
	private Size(int quantity, BufferedImage[] texturas) {
		// TODO Auto-generated constructor stub
		this.quantity = quantity;
		this.tecturas = texturas;
	}
}
