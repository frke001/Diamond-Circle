package org.unibl.etf.figures;
import org.unibl.etf.Color;

public class OrdinaryFigure extends Figure{

	public OrdinaryFigure(Color figureColor) {
		super(figureColor);
	}
	@Override
	public String toString() {
		return name + " (obicna, " + this.figureColor + ")" + super.toString();
	}
}
