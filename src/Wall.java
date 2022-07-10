import java.awt.Color;


class Wall extends Terrain {

	public Wall() {
		super('壁', "かべ", '═', Color.BLACK, Color.GRAY, IMPASSABLE);
		//═╬
	}
}