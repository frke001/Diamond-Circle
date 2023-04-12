package org.unibl.etf.figures;

import org.unibl.etf.Color;

public class FlyingFigure extends Figure {

	public FlyingFigure(Color figureColor) {
		super(figureColor);
	}
	@Override
	public String toString() {
		return name + " (leteca, " + this.figureColor + ")" + super.toString();
	}
}
